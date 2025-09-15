package com.Gosima.Sprout.Products;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);
    Product createProduct(Product product);
//    Product updateProduct();

    void deleteProduct(Long id);
}