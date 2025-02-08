package com.example.demo.controllers;

import com.example.demo.dto.ProductDTO;
import com.example.demo.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productServices.createProduct(productDTO);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productServices.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productServices.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(Pageable pageable) {
        Page<ProductDTO> products = productServices.getAllProduct(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/sorted")
    public ResponseEntity<Page<ProductDTO>> getProductSortedByPrice(Pageable pageable) {
        Page<ProductDTO> sortedProducts = productServices.getProductSortedByPrice(pageable);
        return ResponseEntity.ok(sortedProducts);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductDTO>> getProductByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        List<ProductDTO> filteredProducts = productServices.getProductByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(filteredProducts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productServices.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully.");
    }
}

