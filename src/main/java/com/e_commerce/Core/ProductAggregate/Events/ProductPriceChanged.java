package com.e_commerce.Core.ProductAggregate.Events;

import java.math.BigDecimal;
import java.time.Instant;

import com.e_commerce.Core.Events.DomainEvent;
import com.e_commerce.Core.ValueObjects.ProductId;

public final class ProductPriceChanged implements DomainEvent {

    private final Instant occuredOn = Instant.now();
    private final ProductId id;
    private final BigDecimal oldPrice;
    private final BigDecimal newPrice;

    public ProductPriceChanged(ProductId id, BigDecimal oldPrice, BigDecimal newPrice)
    {
        this.id = id;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
    }

    public ProductId getId() { return this.id; }
    public BigDecimal getOldPrice() { return this.oldPrice; } 
    public BigDecimal getNewPrice() { return this.newPrice; }

    @Override
    public Instant occuredOn() { return this.occuredOn; }
    
}
