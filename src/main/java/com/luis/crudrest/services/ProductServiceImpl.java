package com.luis.crudrest.services;

import com.luis.crudrest.entities.Product;
import com.luis.crudrest.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public Optional<Product> update(Long id, Product product) {

        Optional<Product> productOptional = productRepository.findById(id);
       if(productOptional.isPresent()) {
           Product productDb = productOptional.orElseThrow();
           productDb.setSku(product.getSku());
           productDb.setName(product.getName());
           productDb.setPrice(product.getPrice());
           productDb.setDescription(product.getDescription());
           return Optional.of(productRepository.save(productDb));
       }
        return productOptional;

    }


    @Transactional
    @Override
    public  Optional<Product> delete(Long id) {
        Optional<Product> productDb = productRepository.findById(id);
        productDb.ifPresent(prodDb -> {
            productRepository.delete(prodDb);
        });

        return productDb;
    }


    @Override
    @Transactional(readOnly = true)
    public boolean existsBySku(String sku) {
        return productRepository.existsBySku(sku);
    }


}
