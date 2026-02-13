package com.e_commerce.Core.ValueObjects;

public class CategoryDescription 
{
    private String value;

    private CategoryDescription(String value)
    {
        this.value = value;
    }

    public static CategoryDescription Create(String value)
    {
        return new CategoryDescription(value);
    }

    public String Value() 
    {
        return this.value;
    }
}
