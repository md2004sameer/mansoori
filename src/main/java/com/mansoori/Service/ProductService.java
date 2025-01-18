package com.mansoori.Service;

import java.util.List;
import java.util.Locale.Category;

import org.springframework.stereotype.Service;

import java.util.Optional;

import com.mansoori.Model.Product;

@Service
public interface ProductService {

    Product saveProduct(Product product);

    Optional<Product> getProductById(Long id);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(Category category);

    Optional<Product> updateProduct(Product product);

    void deleteProduct(Product product);

    boolean isProductExistById(Long id);
}
