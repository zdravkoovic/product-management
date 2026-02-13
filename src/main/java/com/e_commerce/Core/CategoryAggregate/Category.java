package com.e_commerce.Core.CategoryAggregate;

import com.e_commerce.Core.ValueObjects.CategoryDescription;
import com.e_commerce.Core.ValueObjects.CategoryId;
import com.e_commerce.Core.ValueObjects.CategoryName;

import lombok.Getter;

@Getter
public class Category {

    private CategoryId id;
    private CategoryName name;
    private CategoryDescription description;

    private Category(CategoryId id, CategoryName name, CategoryDescription description)
    {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static Category Create(String name, String description)
    {
        var categoryName = CategoryName.Create(name);
        var categoryDescription = CategoryDescription.Create(description);

        return new Category(null, categoryName, categoryDescription);
    }

}
