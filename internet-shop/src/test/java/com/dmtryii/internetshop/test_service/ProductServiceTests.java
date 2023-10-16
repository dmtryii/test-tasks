package com.dmtryii.internetshop.test_service;

import com.dmtryii.internetshop.exception.NegativeQuantityException;
import com.dmtryii.internetshop.exception.ResourceNotFoundException;
import com.dmtryii.internetshop.model.Product;
import com.dmtryii.internetshop.repository.ProductRepository;
import com.dmtryii.internetshop.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceTests {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void GetById_ReturnProduct() {
        Long productId = 1L;
        Product mockProduct = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        Product result = productService.getById(productId);

        assertEquals(mockProduct, result);
    }

    @Test
    public void GetById_NotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getById(productId));
    }

    @Test
    public void Save_ReturnProduct() {
        Product productToSave = new Product();
        when(productRepository.save(productToSave)).thenReturn(productToSave);

        Product result = productService.save(productToSave);

        assertEquals(productToSave, result);
    }

    @Test
    public void ChangeQuantity_EnoughQuantity() {
        Long productId = 1L;
        Integer quantityChange = 5;
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setQuantity(10);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any())).thenReturn(existingProduct);

        Product result = productService.changeQuantity(productId, quantityChange);

        assertEquals(15, result.getQuantity());
    }

    @Test
    public void ChangeQuantity_InsufficientQuantity() {
        Long productId = 1L;
        Integer quantityChange = -15;
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setQuantity(10);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        assertThrows(NegativeQuantityException.class, () -> productService.changeQuantity(productId, quantityChange));
    }

    @Test
    public void GetAll_ReturnList() {
        List<Product> mockProducts = Arrays.asList(new Product(), new Product());
        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> result = productService.getAll();

        assertEquals(mockProducts, result);
    }
}
