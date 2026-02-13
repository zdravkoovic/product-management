package com.e_commerce.Infrastructure.Adapter;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.e_commerce.Core.ProductAggregate.ProductAggregate;
import com.e_commerce.Core.ProductAggregate.Interfaces.IProductRepository;
import com.e_commerce.Core.ValueObjects.ProductId;
import com.e_commerce.Infrastructure.Persistance.Model.Product;
import com.e_commerce.Infrastructure.Persistance.Repositories.JpaProductRepository;
import com.e_commerce.Infrastructure.Services.ProductMapper;

@Repository
public class ProductRepository implements IProductRepository
{

    private final JpaProductRepository jpaRepos;

    public ProductRepository(JpaProductRepository jpaRepos) {
        this.jpaRepos = jpaRepos;
    }

    @Override
    public void save(ProductAggregate product) {
        var productEntity = ProductMapper.toModel(product);
        Product saved = jpaRepos.save(productEntity);
        product.setId(ProductId.create(saved.getId()));
    }

    @Override
    public Optional<ProductAggregate> findById(ProductId id) {
        return jpaRepos.findById(id.value()).map(ProductMapper::toDomain);
    }

    @Override
    public List<ProductAggregate> findAllByIdInOrderById(List<Integer> productIds) {
        List<Integer> ids = productIds.stream().map(ProductId::fromInt).toList();
        return jpaRepos.findAllById(ids).stream().map(ProductMapper::toDomain).toList();
    }

    @Override
    public ProductAggregate update(ProductAggregate product) {
        var entity = this.jpaRepos.findById(product.getId().value()).get();
        
        if (entity == null) throw new InvalidParameterException("Product not found!");

        entity.setName(product.getName().value());
        entity.setCategory_id(product.getCategoryId().value());
        entity.setDescription(product.getDescription().value());
        entity.setPrice(product.getPrice().value());
        entity.setAvailableQuantity(product.getQuantity().value());

        return product;
    }
    
}
