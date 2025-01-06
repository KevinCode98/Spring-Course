package com.pvin.springboot.id.app.springbootid;

import com.pvin.springboot.id.app.springbootid.repositories.IProductRepository;
import com.pvin.springboot.id.app.springbootid.repositories.ProductRepositoryJson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

@Configuration
@PropertySource("classpath:config.properties")
public class AppConfig {
    @Value("classpath:json/product.json")
    private Resource resource;

    @Bean("productJson")
    IProductRepository productRepositoryJson() {
        return new ProductRepositoryJson(resource);
    }
}
