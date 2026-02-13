package com.e_commerce.Application.Product.Dtos;

import java.math.BigDecimal;

public record ProductPurchaseDto(
    Integer productId,
    String name,
    String description,
    BigDecimal price,
    Integer quantity
) {

}
