package com.e_commerce.Core.ValueObjects;

public class ProductDescription {
    private String value;

    private ProductDescription(String value)
    {
        this.value = value;
    }

    public static ProductDescription create(String value)
    {
        return new ProductDescription(value);
    }

    public String value() 
    {
        return this.value;
    }
}
