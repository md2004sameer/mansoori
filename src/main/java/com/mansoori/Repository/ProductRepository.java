package com.mansoori.Repository;

import java.util.List;
import java.util.Locale.Category;


import com.mansoori.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}
