package com.e_commerce.Core.ProductAggregate.Events;

import java.time.Instant;

import com.e_commerce.Core.Events.DomainEvent;
import com.e_commerce.Core.ValueObjects.ProductId;

public final class StockIncreased implements DomainEvent{

    private ProductId id;
    private Integer quantity;
    private Instant occuredOn = Instant.now();

    public StockIncreased(ProductId id, Integer quantity)
    {
        this.id = id;
        this.quantity = quantity;
    }

    @Override
    public Instant occuredOn() { return occuredOn; }

    public ProductId getId(){ return id; }
    public Integer getQuantity() { return quantity; }
    
}
