package com.altavik.cart.core.impl;

import com.altavik.cart.core.ICartService;
import com.altavik.cart.exceptions.NotFoundException;
import com.altavik.cart.models.Cart;
import com.altavik.cart.models.Product;
import com.altavik.cart.models.Status;
import com.altavik.cart.repositories.ICartRepository;
import com.altavik.cart.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    ICartRepository cartRepository;

    @Autowired
    IProductRepository productRepository;

    @Override
    public List<Cart> getCarts() {
        List<Cart> carts = cartRepository.findAll();
        return carts;
    }

    @Override
    public Status addCart(Cart cart) {
        System.out.println(cart.toString());
        cart.setCreate_ts(new Date());
        List<Product> products = new ArrayList<>();
        for(Product product: cart.getProducts()){
            product.setId(UUID.randomUUID());
            product.setCreated(new Date());
            product.setCart(cart);
            products.add(product);
        }
        cart.setProducts(products);
        Cart c = cartRepository.save(cart);
        Status status = new Status();
        status.setId(String.valueOf(c.getCartId()));
        status.setMessage("Cart Added");
        return status;
    }

    @Override
    public Cart getCart(Integer id) {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if(cartOptional.isPresent())
            return cartOptional.get();
        throw new NotFoundException("Cart ID not found");
    }

    @Override
    public List<Product> getProducts(Integer id) {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if(cartOptional.isPresent()){
            if(cartOptional.get().getProducts().size()>0)
                return cartOptional.get().getProducts();
            else
                throw new NotFoundException("Products Not found");
        }
        throw new NotFoundException("Cart not found");
    }

    @Override
    public Status updateProducts(Integer id, List<Product> products) {
        Status status = new Status();
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if(cartOptional.isPresent()){
            for(Product product : products){
                product.setUpdated(new Date());
                product.setCart(cartOptional.get());
            }
            productRepository.saveAll(products);
            status.setMessage("Products updated");
            return status;
        }
        throw new NotFoundException("Cart not found");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Status deleteProduct(Integer id, UUID productId) {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if(cartOptional.isPresent()){
            Status status = new Status();
            Cart cart = cartOptional.get();
            Product product = cart.getProducts().stream()
                    .filter(x -> productId.equals(x.getId()))
                    .findAny().orElse(null);
           if(product!=null)
           {
               productRepository.deleteByIdWithJPQL(productId);
               status.setMessage("Product Deleted.");
               return status;
           }
            throw new NotFoundException("Products Not found");
        }
        throw new NotFoundException("Cart not found");
    }
}
