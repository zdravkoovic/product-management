package com.e_commerce.Application.Interfaces;

import java.util.List;

import com.e_commerce.Core.ProductAggregate.ProductAggregate;

public interface IProductReadRepository {
    
    List<ProductAggregate> findAllByIdInOrderById(List<Integer> productIds);
    ProductAggregate findById(Integer id);
    List<ProductAggregate> findAll();
}
