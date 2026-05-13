package com.example.inventory.repository;
import com.example.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
List<Product> findByCategory(String category);
List<Product> findByQuantityLessThan(int quantity);
List<Product> findByNameContainingIgnoreCase(String keyword);
}

