package com.e_commerce.Application.Product.Dtos;

import java.math.BigDecimal;


public record ProductDto(
    String name,
    String description,
    Integer availableQuantity,
    BigDecimal price
) {

}
