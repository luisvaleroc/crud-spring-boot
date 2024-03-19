package com.luis.crudrest.repositories;

import com.luis.crudrest.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository  extends CrudRepository<Product, Long> {

    boolean existsBySku(String sku);
}
