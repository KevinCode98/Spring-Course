package com.pvin.springboot.id.app.springbootid.services;

import com.pvin.springboot.id.app.springbootid.models.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll();

    Product findById(Long id);
}
