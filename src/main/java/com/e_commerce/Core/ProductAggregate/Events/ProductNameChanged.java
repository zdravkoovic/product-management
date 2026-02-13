package com.e_commerce.Core.ProductAggregate.Events;

import java.time.Instant;

import com.e_commerce.Core.Events.DomainEvent;
import com.e_commerce.Core.ValueObjects.ProductId;

public final class ProductNameChanged implements DomainEvent{

    private final ProductId id;
    private final String oldName;
    private final String newName;

    public ProductNameChanged(ProductId id, String oldName, String newName)
    {
        this.id = id;
        this.oldName = oldName;
        this.newName = newName;
    }

    @Override
    public Instant occuredOn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'occuredOn'");
    }

    public ProductId getId() { return this.id; }
    public String getOldName() { return this.oldName; }
    public String getNewName() { return this.newName; }
    
}
