package com.e_commerce.API.Http.Requests;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRequest(
    @NotNull(message = "Name cannot be null")
    String name,
    @NotNull(message = "Description cannot be null")
    String description,
    @Positive(message = "Available quantity must be positive")
    @NotNull(message = "Available quantity cannot be null")
    Integer availableQuantity,
    @Positive(message = "Price must be positive")
    @NotNull(message = "Price cannot be null")
    BigDecimal price,
    @NotNull(message = "Category ID cannot be null")
    Integer categoryId
) {

}
