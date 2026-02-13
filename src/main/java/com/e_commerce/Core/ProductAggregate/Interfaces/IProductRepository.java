package com.e_commerce.Core.ProductAggregate.Interfaces;

import java.util.List;
import java.util.Optional;

import com.e_commerce.Core.ProductAggregate.ProductAggregate;
import com.e_commerce.Core.ValueObjects.ProductId;

public interface IProductRepository {
    void save(ProductAggregate product);
    ProductAggregate update(ProductAggregate product);
    Optional<ProductAggregate> findById(ProductId id);
    List<ProductAggregate> findAllByIdInOrderById(List<Integer> productIds);
}
