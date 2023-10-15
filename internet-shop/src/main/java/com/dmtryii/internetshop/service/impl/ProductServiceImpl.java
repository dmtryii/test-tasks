package com.dmtryii.internetshop.service.impl;

import com.dmtryii.internetshop.exception.NegativeQuantityException;
import com.dmtryii.internetshop.exception.ResourceNotFoundException;
import com.dmtryii.internetshop.model.Product;
import com.dmtryii.internetshop.repository.ProductRepository;
import com.dmtryii.internetshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The product not fount by id: " + id)
        );
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product changeQuantity(Long id, Integer quantity) {
        Product product = getById(id);
        Integer currentQuantity = product.getQuantity();

        int newQuantity = currentQuantity + quantity;

        if (newQuantity >= 0) {
            product.setQuantity(newQuantity);
            return productRepository.save(product);
        }

        throw new NegativeQuantityException(
                (newQuantity * -1) + " products are not enough to change the quantity"
        );
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
