package com.altavik.cart.core;


import com.altavik.cart.models.Cart;
import com.altavik.cart.models.Product;
import com.altavik.cart.models.Status;

import java.util.List;
import java.util.UUID;

public interface ICartService {
    List<Cart> getCarts();

    Status addCart(Cart cart);

    Cart getCart(Integer id);

    List<Product> getProducts(Integer id);

    Status updateProducts(Integer id, List<Product> products);

    Status deleteProduct(Integer id, UUID productId);
}
