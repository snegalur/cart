package com.altavik.cart.repositories;

import com.altavik.cart.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProductRepository  extends JpaRepository<Product, UUID> {

    @Modifying
    @Query("DELETE Product p WHERE p.id =:productId")
    void deleteByIdWithJPQL(UUID productId);
}
