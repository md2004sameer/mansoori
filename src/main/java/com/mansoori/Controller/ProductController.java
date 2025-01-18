package com.mansoori.Controller;

import com.mansoori.Helper.ResourceNotFoundException;
import com.mansoori.Model.Product;
import com.mansoori.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) throws IOException {
        // Save the product to the database
        Product savedProduct = productService.saveProduct(product);
    
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }
    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/getProduct/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
        return ResponseEntity.ok(product);
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) {

        Product existingProduct = productService.getProductById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        // Update fields only if they are not null or differ from the existing values
        if (updatedProduct.getName() != null) {
            existingProduct.setName(updatedProduct.getName());
        }
        if (updatedProduct.getDescription() != null) {
            existingProduct.setDescription(updatedProduct.getDescription());
        }
        if (updatedProduct.getImage() != null) {
            existingProduct.setImage(updatedProduct.getImage());  // Store as String (base64)
        }
        if (updatedProduct.getVideo() != null) {
            existingProduct.setVideo(updatedProduct.getVideo());
        }
        if (updatedProduct.getPrice() != 0.0) {
            existingProduct.setPrice(updatedProduct.getPrice());
        }
        if (updatedProduct.getCategory() != null) {
            existingProduct.setCategory(updatedProduct.getCategory());
        }
        if (updatedProduct.isAvailabilityStatus() != existingProduct.isAvailabilityStatus()) {
            existingProduct.setAvailabilityStatus(updatedProduct.isAvailabilityStatus());
        }

        Product savedProduct = productService.saveProduct(existingProduct);
        return ResponseEntity.ok(savedProduct);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        logger.info("Delete endpoint invoked for product ID: {}", id);
        Optional<Product> productOptional = productService.getProductById(id);

        if (productOptional.isEmpty()) {
            logger.warn("Product with ID: {} not found for deletion", id);
            return ResponseEntity.notFound().build();
        }

        productService.deleteProduct(productOptional.get());

        logger.info("Product with ID: {} deleted successfully", id);

        return ResponseEntity.noContent().build();
    }

    // Endpoint to check if a product exists by ID
    @GetMapping("/exists/{productId}")
    public ResponseEntity<Boolean> isProductExist(@PathVariable Long productId) {
        boolean exists = productService.isProductExistById(productId);
        return ResponseEntity.ok(exists);
    }
}