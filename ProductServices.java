package com.example.demo.services;

import com.example.demo.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductServices {
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long productId, ProductDTO productDTO);
    ProductDTO getProductById(Long productId);
    Page<ProductDTO> getAllProduct(Pageable pageable);
    Page<ProductDTO> getProductSortedByPrice(Pageable pageable);
    List<ProductDTO> getProductByPriceRange(Double minPrice, Double maxPrice);
    void deleteProduct(Long productId);
}
