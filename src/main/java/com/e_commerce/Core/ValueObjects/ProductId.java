package com.e_commerce.Core.ValueObjects;

import java.util.Random;
import java.util.random.RandomGenerator;

public class ProductId {
    private Integer value;

    private ProductId(Integer value)
    {
        this.value = value;
    }

    public static ProductId create(Integer value)
    {
        return new ProductId(value);
    }

    public static Integer fromInt(Integer value)
    {
        return new ProductId(value).value();
    }

    public Integer value() 
    {
        return this.value;
    }

    public ProductId Generate()
    {
        var random = Random.from(RandomGenerator.getDefault());
        return new ProductId(random.nextInt());
    }
}
