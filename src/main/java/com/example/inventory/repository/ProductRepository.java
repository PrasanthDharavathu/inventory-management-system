package com.example.inventory.repository;
import com.example.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
List<Product> findByCategory(String category);
List<Product> findByQuantityLessThan(int quantity);
List<Product> findByNameContainingIgnoreCase(String keyword);

Page<Product> findByCategoryIgnoreCase(String category, Pageable pageable);
Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
Page<Product> findByNameContainingIgnoreCaseAndCategoryIgnoreCase(String keyword,String category,Pageable pageable);
}

