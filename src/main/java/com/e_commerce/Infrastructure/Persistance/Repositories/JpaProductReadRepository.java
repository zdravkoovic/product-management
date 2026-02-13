package com.e_commerce.Infrastructure.Persistance.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.Infrastructure.Persistance.Model.Product;

public interface JpaProductReadRepository extends JpaRepository<Product, Integer>
{
    public List<Product> findAllByIdInOrderById(List<Integer> productIds);
    
}
