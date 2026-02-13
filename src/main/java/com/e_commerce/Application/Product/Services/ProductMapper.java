package com.e_commerce.Application.Product.Services;

import com.e_commerce.Application.Product.Dtos.ProductDto;
import com.e_commerce.Application.Product.Dtos.ProductPurchaseDto;
import com.e_commerce.Core.ProductAggregate.ProductAggregate;

public class ProductMapper {
    
    public static ProductPurchaseDto toProductPurchaseDto(ProductAggregate product, Integer quantity)
    {
        return new ProductPurchaseDto(
            product.getId().value(),
            product.getName().value(),
            product.getDescription().value(),
            product.getPrice().value(),
            quantity
        );
    }

    public static ProductDto toProductDto(ProductAggregate product)
    {
        return new ProductDto(
            product.getName().value(),
            product.getDescription().value(),
            product.getQuantity().value(),
            product.getPrice().value()
        );
    }

    
}
