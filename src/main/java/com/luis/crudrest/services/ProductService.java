package com.luis.crudrest.services;

import com.luis.crudrest.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();
    Optional<Product> findById(Long id);

    Product save(Product product);
    Optional<Product> update(Long id, Product product);

    Optional<Product> delete(Long id);
    boolean existsBySku(String sku);

}
