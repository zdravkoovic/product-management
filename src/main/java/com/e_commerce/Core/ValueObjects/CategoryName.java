package com.e_commerce.Core.ValueObjects;

public class CategoryName {
    private String value;

    private CategoryName(String value)
    {
        this.value = value;
    }

    public static CategoryName Create(String value)
    {
        return new CategoryName(value);
    }

    public String Value() 
    {
        return this.value;
    }
}
