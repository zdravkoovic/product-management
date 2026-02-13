package com.e_commerce.Core.ProductAggregate.Events;

import java.math.BigDecimal;
import java.time.Instant;

import com.e_commerce.Core.Events.DomainEvent;
import com.e_commerce.Core.ValueObjects.ProductId;

public final class ProductCreated implements DomainEvent{

    private final Instant occurredOn = Instant.now();
    private final ProductId productId; // may be null until persisted
    private final String name;
    private final String description;
    private final int quantity;
    private final BigDecimal price;

    public ProductCreated(ProductId productId, String name, String description, int quantity, BigDecimal price) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }
    
    @Override
    public Instant occuredOn() { return this.occurredOn; }

    public ProductId getProductId() { return productId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }

}
