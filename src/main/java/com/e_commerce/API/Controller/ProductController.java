package com.e_commerce.API.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.API.Http.Requests.ProductPricesAndQuantitiesRequest;
import com.e_commerce.API.Http.Requests.ProductPurchaseRequest;
import com.e_commerce.API.Http.Requests.ProductRequest;
import com.e_commerce.Application.Product.Dtos.ProductDto;
import com.e_commerce.Application.Product.Dtos.ProductPricesAndQuantitiesDto;
import com.e_commerce.Application.Product.Dtos.ProductPurchaseDto;
import com.e_commerce.Application.Product.Services.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    
    private final ProductService service;

    @PostMapping
    public ResponseEntity<ProductDto> create(
        @RequestBody @Valid ProductRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.createProduct(request));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseDto>> purchaseProducts(
        @RequestBody List<ProductPurchaseRequest> request
    ) { 
        return ResponseEntity.ok(this.service.purchaseProducts(request));
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductDto> getProductById(
        @PathVariable("product-id") Integer id
    ) {
        return ResponseEntity.ok(this.service.getProductById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(this.service.getAllProducts());
    }

    @PostMapping("/prices&quantities")
    public ResponseEntity<ProductPricesAndQuantitiesDto> getPricesAndQuantities(
        @Valid @RequestBody ProductPricesAndQuantitiesRequest request) {
        
        return ResponseEntity.ok(this.service.getProductPricesAndQuantities(request.productIds()));
    }
    
    
}
