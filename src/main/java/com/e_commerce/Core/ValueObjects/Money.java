package com.e_commerce.Core.ValueObjects;

import java.math.BigDecimal;

public class Money {
    private BigDecimal value;

    private Money(BigDecimal value)
    {
        this.value = value;
    }

    public static Money create(BigDecimal value)
    {
        return new Money(value);
    }

    public BigDecimal value() 
    {
        return this.value;
    }
}
