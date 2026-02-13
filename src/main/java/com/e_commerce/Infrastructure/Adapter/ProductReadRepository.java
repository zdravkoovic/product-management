package com.e_commerce.Infrastructure.Adapter;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.e_commerce.Application.Interfaces.IProductReadRepository;
import com.e_commerce.Core.ProductAggregate.ProductAggregate;
import com.e_commerce.Infrastructure.Persistance.Repositories.JpaProductReadRepository;
import com.e_commerce.Infrastructure.Services.ProductMapper;

@Repository
public class ProductReadRepository implements IProductReadRepository{

    private final JpaProductReadRepository jpaRepository;
    
    public ProductReadRepository(
        JpaProductReadRepository jpaRepository
    ) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<ProductAggregate> findAllByIdInOrderById(List<Integer> productIds) {
        return jpaRepository
                .findAllByIdInOrderById(productIds)
                .stream()
                .map(ProductMapper::toDomain)
                .toList();
    }

    @Override
    public ProductAggregate findById(Integer id) {
        var product = jpaRepository.findById(id).get();
        return ProductMapper.toDomain(product);
    }

    @Override
    public List<ProductAggregate> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(ProductMapper::toDomain)
                .toList();
    }
    
}
