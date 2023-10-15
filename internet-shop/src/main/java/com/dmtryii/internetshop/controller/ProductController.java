package com.dmtryii.internetshop.controller;

import com.dmtryii.internetshop.dto.ProductDTO;
import com.dmtryii.internetshop.model.Product;
import com.dmtryii.internetshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@RestController
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {

        List<ProductDTO> response = productService.getAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    private ProductDTO map(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }
}
