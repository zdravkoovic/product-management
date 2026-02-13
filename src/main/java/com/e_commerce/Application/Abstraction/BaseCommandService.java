package com.e_commerce.Application.Abstraction;

public abstract class BaseCommandService
{
    public Integer Handle()
    {
        var result = this.Execute();
        return result;
    }
    public abstract Integer Execute();
}