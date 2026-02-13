package com.e_commerce.Core.ValueObjects;

public class CategoryId {
    private Integer value;

    private CategoryId(Integer value)
    {
        this.value = value;
    }

    public static CategoryId create(Integer value)
    {
        return new CategoryId(value);
    }

    public Integer value() 
    {
        return this.value;
    }
}
