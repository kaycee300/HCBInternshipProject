package com.Gosima.Sprout.Products;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PreAuthorize("hasAuthority('READ_PRODUCT') or hasAuthority('MANAGE_PRODUCT')")
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_PRODUCT') or hasAuthority('MANAGE_PRODUCT')")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id).orElseThrow(()->new RuntimeException("Product Not Found"));

    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
//        Product product = productService.getProductById(id);
//        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
//    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}