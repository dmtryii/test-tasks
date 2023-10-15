package com.dmtryii.internetshop.controller;

import com.dmtryii.internetshop.dto.ProductDTO;
import com.dmtryii.internetshop.dto.request.ChangeQuantityRequest;
import com.dmtryii.internetshop.dto.request.ProductRequest;
import com.dmtryii.internetshop.model.Product;
import com.dmtryii.internetshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/manager/products")
@RestController
public class ProductManagerController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ProductDTO> save(@RequestBody ProductRequest request) {
        Product product = productService.save(
                map(request)
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(map(product));
    }

    @PatchMapping
    public ResponseEntity<ProductDTO> changeQuantity(@RequestBody ChangeQuantityRequest request) {
        Product product = productService.changeQuantity(
                request.getProductId(),
                request.getQuantity()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(map(product));
    }

    private Product map(ProductRequest request) {
        return modelMapper.map(request, Product.class);
    }

    private ProductDTO map(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }
}
