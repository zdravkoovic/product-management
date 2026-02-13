package com.e_commerce.API.Http.Requests;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(
    @NotNull(message = "Product ID cannot be null")
    Integer productId,
    @NotNull(message = "Quantity cannot be null")
    Integer quantity
) {

}
