package com.e_commerce.Infrastructure.Persistance.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.Infrastructure.Persistance.Model.Product;

public interface JpaProductRepository extends JpaRepository<Product, Integer>{
    
}
