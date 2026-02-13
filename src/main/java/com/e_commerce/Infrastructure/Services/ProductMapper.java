package com.e_commerce.Infrastructure.Services;

import com.e_commerce.Core.ProductAggregate.ProductAggregate;
import com.e_commerce.Infrastructure.Persistance.Model.Product;

public final class ProductMapper {

    public ProductMapper() {}
    
    public static ProductAggregate toDomain(Product product)
    {
        return ProductAggregate.reconstruct(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getAvailableQuantity(),
            product.getPrice(),
            product.getCategory_id()
        );
    }

    public static Product toModel(ProductAggregate product)
    {
        return new Product(
            null,
            product.getName().value(),
            product.getDescription().value(),
            product.getQuantity().value(),
            product.getPrice().value(),
            product.getCategoryId().value()
        );
    }

}
