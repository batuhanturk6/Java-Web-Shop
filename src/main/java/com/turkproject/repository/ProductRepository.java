package com.turkproject.repository;

import com.turkproject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategoryId(int id);

    List<Product> findByNameContaining(String name);
}
