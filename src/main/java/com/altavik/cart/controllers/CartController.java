package com.altavik.cart.controllers;

import com.altavik.cart.core.ICartService;
import com.altavik.cart.models.Cart;
import com.altavik.cart.models.Product;
import com.altavik.cart.models.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

@RestController
@Api(tags = { "Carts controller" })
public class CartController {

    @Autowired
    private ICartService cartService;

    @GetMapping("/api/carts")
    @ApiOperation(value = "Returns carts", nickname = "getCarts")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Carts found"),
            @ApiResponse(code = 404, message = "Carts not found"),
            @ApiResponse(code = 500, message = "Technical Error") })
    public List<Cart> getCarts(){
            return cartService.getCarts();
    }

    @PostMapping("/api/carts")
    @ApiOperation(value = "Add cart", nickname = "addCart")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Cart created"),
            @ApiResponse(code = 400, message = "Cart cannot be added"),
            @ApiResponse(code = 500, message = "Technical Error") })
    public Status addCart(@Valid @RequestBody Cart cart){
        return cartService.addCart(cart);
    }

    @GetMapping("/api/carts/{id}")
    @ApiOperation(value = "Returns cart", nickname = "getCart")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Cart found"),
            @ApiResponse(code = 404, message = "Cart not found"),
            @ApiResponse(code = 500, message = "Technical Error") })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", defaultValue = "1", paramType = "path", required = true, dataType = "Integer") })
    public Cart getCart(@PathVariable("id")Integer id){
        return cartService.getCart(id);
    }

    @GetMapping("/api/carts/{id}/products")
    @ApiOperation(value = "Returns products", nickname = "getProducts")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Products found"),
            @ApiResponse(code = 404, message = "Product not found"),
            @ApiResponse(code = 500, message = "Technical Error") })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", defaultValue = "1", paramType = "path", required = true, dataType = "Long") })
    public List<Product> getProducts(@PathVariable("id")Integer id){
        return cartService.getProducts(id);
    }

    @PutMapping("/api/carts/{id}/products")
    @ApiOperation(value = "updates products", nickname = "updateProducts")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Products updated"),
            @ApiResponse(code = 404, message = "Cart / Product not found"),
            @ApiResponse(code = 500, message = "Technical Error") })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", defaultValue = "1", paramType = "path", required = true, dataType = "Long") })
    public Status updateProducts(@PathVariable("id")Integer id, @RequestBody List<Product> products){
        return cartService.updateProducts(id, products);
    }

    @DeleteMapping("/api/carts/{id}/products/{productId}")
    @ApiOperation(value = "delete product", nickname = "deleteProduct")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Products deleted"),
            @ApiResponse(code = 404, message = "Cart / Product not found"),
            @ApiResponse(code = 500, message = "Technical Error") })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", defaultValue = "1", paramType = "path", required = true, dataType = "Long") ,
            @ApiImplicitParam(name = "productId", defaultValue = "1", paramType = "path", required = true, dataType = "String")})
    public Status deleteProduct(@PathVariable("id") Integer id, @PathVariable("productId")UUID productId) {
        return cartService.deleteProduct(id, productId);
    }
}
