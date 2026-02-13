package com.e_commerce.Core.ValueObjects;

import com.e_commerce.Core.Errors.DomainException;

public class Quantity {
    private Integer value;

    private Quantity(Integer value)
    {
        this.value = value;
    }

    public static Quantity create(Integer value)
    {
        return new Quantity(value);
    }

    public Integer value() 
    {
        return this.value;
    }

    public Quantity add(Integer amount)
    {
        return new Quantity(this.value + amount);
    }

    public Quantity subtract(Integer amount)
    {
        var result = this.value - amount;
        if(result < 0) throw new DomainException("Negative quantity.");
        return new Quantity(result);
    }
}
