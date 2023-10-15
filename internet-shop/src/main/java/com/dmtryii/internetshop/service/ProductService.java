package com.dmtryii.internetshop.service;

import com.dmtryii.internetshop.model.Product;

import java.util.List;

public interface ProductService {
    Product getById(Long id);
    Product save(Product product);
    Product changeQuantity(Long id, Integer quantity);
    List<Product> getAll();
}
