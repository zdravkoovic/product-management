package com.e_commerce.Application.Product.Services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.e_commerce.API.Http.Requests.ProductPurchaseRequest;
import com.e_commerce.API.Http.Requests.ProductRequest;
import com.e_commerce.Application.Errors.ProductPurchaseException;
import com.e_commerce.Application.Interfaces.IProductReadRepository;
import com.e_commerce.Application.Product.Dtos.ProductDto;
import com.e_commerce.Application.Product.Dtos.ProductPricesAndQuantitiesDto;
import com.e_commerce.Application.Product.Dtos.ProductPurchaseDto;
import com.e_commerce.Core.ProductAggregate.ProductAggregate;
import com.e_commerce.Core.ProductAggregate.Interfaces.IProductRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    public final IProductRepository repository;
    public final IProductReadRepository productReadRepository;

    @Transactional
    public ProductDto createProduct(ProductRequest request) {
        
        validateProductRequest(request);

        ProductAggregate product = ProductAggregate.create(
                request.name(),
                request.description(),
                request.availableQuantity(),
                request.price(),
                request.categoryId()
        );

        this.repository.save(product);

        return new ProductDto(product.getName().value(), product.getDescription().value(), product.getQuantity().value(), product.getPrice().value());
    }

    @Transactional(rollbackOn = ProductPurchaseException.class)
    public List<ProductPurchaseDto> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds = request
                    .stream()
                    .map(ProductPurchaseRequest::productId)
                    .toList();

        var storedProducts = this.repository.findAllByIdInOrderById(productIds);
        if(storedProducts.size() != productIds.size()) {
            throw new ProductPurchaseException("One or more products not found");
        }

        var storedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var purchasedProducts = new ArrayList<ProductPurchaseDto>();

        for(int i = 0; i < storedProducts.size(); i++){
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);

            if(product.getQuantity().value() < productRequest.quantity()){
                throw new ProductPurchaseException("Insufficient stock quantity for product " + product.getName().value());
            }

            var newAvailableQuantity = product.getQuantity().subtract(productRequest.quantity());
            product.setQuantity(newAvailableQuantity);
            this.repository.update(product);
            purchasedProducts.add(ProductMapper.toProductPurchaseDto(product, productRequest.quantity()));
        }

        return purchasedProducts;
    }

    public ProductDto getProductById(Integer id) {
        var product =  this.productReadRepository.findById(id);
        
        var result = ProductMapper.toProductDto(product);
        if(result == null) throw new EntityNotFoundException("Cannot find product with id: " + id);

        return result;
        
    }

    public List<ProductDto> getAllProducts() {
        var products = this.productReadRepository.findAll();
        return products
                .stream()
                .map(ProductMapper::toProductDto)
                .toList();
    }

    public ProductPricesAndQuantitiesDto getProductPricesAndQuantities(List<Integer> ids)
    {
        var products = repository.findAllByIdInOrderById(ids);

        var result = products.stream()
            .map(ProductMapper::toProductDto)
            .toList();
        
        return new ProductPricesAndQuantitiesDto(result);
    }

    private void validateProductRequest(ProductRequest request)
    {
        if (request.availableQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (request.description() == null || request.description().isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank");
        }
        if (request.name() == null || request.name().isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
    }
}
