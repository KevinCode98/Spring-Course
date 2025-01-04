package com.pvin.springboot.id.app.springbootid.repositories;

import com.pvin.springboot.id.app.springbootid.models.Product;

import java.util.List;

public interface IProductRepository {
    List<Product> findAll();

    Product findById(Long id);
}
