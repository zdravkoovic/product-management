package com.e_commerce.Core.ValueObjects;

public class ProductName {
    private String value;

    private ProductName(String value)
    {
        this.value = value;
    }

    public static ProductName create(String value)
    {
        return new ProductName(value);
    }

    public String value() 
    {
        return this.value;
    }
}
