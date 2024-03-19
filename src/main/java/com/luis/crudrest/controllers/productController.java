package com.luis.crudrest.controllers;


//import com.luis.crudrest.ProductValidation;
import com.luis.crudrest.entities.Product;
import com.luis.crudrest.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class productController {

    @Autowired
    private ProductService productService;

    //@Autowired
    //private ProductValidation productValidation;
    @GetMapping
    public List<Product> list(){
        return  productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id){
            Optional<Product> productOptional = productService.findById(id);
            if(productOptional.isPresent()){
                return ResponseEntity.ok(productOptional.orElseThrow());
            }
            return ResponseEntity.notFound().build();
        }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result) {
        // valdation.validate(product, result);
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Product product, BindingResult result, @PathVariable Long id){
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa enfermera");
        //productValidation.validate(product, result);
        if(result.hasFieldErrors()){
            return validation(result);
        }
      Optional<Product> productOptional = productService.update(id, product);
      if(productOptional.isPresent()){
          return ResponseEntity.status(HttpStatus.CREATED).body(productOptional.orElseThrow());
      }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id){
        Optional<Product> productOptional = productService.delete(id);
        if(productOptional.isPresent()){
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();

    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
