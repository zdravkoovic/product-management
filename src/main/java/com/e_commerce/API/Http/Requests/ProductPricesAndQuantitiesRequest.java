package com.e_commerce.API.Http.Requests;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record ProductPricesAndQuantitiesRequest(
    @NotEmpty List<Integer> productIds
) {
}