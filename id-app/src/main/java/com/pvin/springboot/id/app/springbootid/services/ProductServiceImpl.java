package com.pvin.springboot.id.app.springbootid.services;

import com.pvin.springboot.id.app.springbootid.models.Product;
import com.pvin.springboot.id.app.springbootid.repositories.ProductRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductRepositoryImpl repository;

    @Override
    public List<Product> findAll() {
        return repository.findAll().stream().map(p -> {
            Double priceImp = p.getPrice() * 1.16d;
//            return new Product(p.getId(), p.getName(), priceImp.longValue());
            Product newProduct = (Product) p.clone();
            newProduct.setPrice(priceImp.longValue());
            return newProduct;
        }).collect(Collectors.toList());
    }

    @Override
    public Product findById(Long id) {
        return repository.findById(id);
    }
}
