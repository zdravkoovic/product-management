package com.e_commerce.Core.ProductAggregate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.e_commerce.Core.Errors.DomainException;
import com.e_commerce.Core.Events.DomainEvent;
import com.e_commerce.Core.ProductAggregate.Events.ProductCreated;
import com.e_commerce.Core.ProductAggregate.Events.ProductNameChanged;
import com.e_commerce.Core.ProductAggregate.Events.ProductPriceChanged;
import com.e_commerce.Core.ProductAggregate.Events.StockDecreased;
import com.e_commerce.Core.ProductAggregate.Events.StockIncreased;
import com.e_commerce.Core.ValueObjects.CategoryId;
import com.e_commerce.Core.ValueObjects.Money;
import com.e_commerce.Core.ValueObjects.ProductDescription;
import com.e_commerce.Core.ValueObjects.ProductId;
import com.e_commerce.Core.ValueObjects.ProductName;
import com.e_commerce.Core.ValueObjects.Quantity;

public class ProductAggregate {

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    private ProductId id;
    private ProductName name;
    private ProductDescription description;
    private Quantity quantity;
    private Money price;
    private CategoryId categoryId;
    
    private ProductAggregate(ProductId id, ProductName name, ProductDescription description, Quantity quantity, Money price, CategoryId categoryId)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.categoryId = categoryId;
    }

    public static ProductAggregate create(String name, String description, Integer quantity, BigDecimal price, Integer categoryId)
    {
        validateCreateArgs(name, description, quantity, price);

        var productName = ProductName.create(name);
        var productDescription = ProductDescription.create(description);
        var productQuantity = Quantity.create(quantity);
        var productPrice = Money.create(price);
        var productCategoryId = CategoryId.create(categoryId);
        
        ProductAggregate productAggregate = new ProductAggregate(null, productName, productDescription, productQuantity, productPrice, productCategoryId);
        productAggregate.recordThat(new ProductCreated(null, name, description, quantity, price));
        return productAggregate;
    }

    public static ProductAggregate reconstruct(Integer id, String name, String description, Integer quantity, BigDecimal price, Integer categoryId)
    {
        var productId = ProductId.create(id);
        var productName = ProductName.create(name);
        var productDescription = ProductDescription.create(description);
        var productQuantity = Quantity.create(quantity);
        var productPrice = Money.create(price);
        var productCategoryId = CategoryId.create(categoryId);
        
        return new ProductAggregate(productId, productName, productDescription, productQuantity, productPrice, productCategoryId);
    }

    public void increaseStock(int amount) {
        if (amount <= 0) throw new DomainException("increase amount must be > 0");
        this.quantity = this.quantity.add(amount);
        this.recordThat(new StockIncreased(this.id, amount));
    }

    public void decreaseStock(int amount) {
        if (amount <= 0) throw new DomainException("decrease amount must be > 0");
        if (this.quantity.value() < amount) throw new DomainException("insufficient stock");
        this.quantity = this.quantity.subtract(amount);
        this.recordThat(new StockDecreased(this.id, amount));
    }

    public void updatePrice(BigDecimal newPrice) {
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) < 0) throw new DomainException("price must be >= 0");
        var old = this.price;
        this.price = Money.create(newPrice);
        this.recordThat(new ProductPriceChanged(this.id, old.value(), newPrice));
    }

    public void updateName(String newName) {
        if (newName == null || newName.isBlank()) throw new DomainException("name required");
        var old = this.name;
        this.name = ProductName.create(newName);
        this.recordThat(new ProductNameChanged(this.id, old.value(), newName));
    }

    protected void recordThat(DomainEvent event)
    {
        this.domainEvents.add(event);
    }

    public List<DomainEvent> pullDomainEvents()
    {
        List<DomainEvent> snapshot = List.copyOf(domainEvents);
        domainEvents.clear();
        return snapshot;
    }

    private static void validateCreateArgs(String name, String description, Integer quantity, BigDecimal price) {
        if (name == null || name.isBlank()) throw new DomainException("name is required");
        if (description == null) throw new DomainException("description is required");
        if (quantity == null || quantity < 0) throw new DomainException("quantity must be >= 0");
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) throw new DomainException("price must be >= 0");
    }

    /* --- Accessors --- */
    public ProductId getId() { return id; }
    public void setId(ProductId id) { this.id = id; }
    
    public ProductName getName() { return name; }
    public ProductDescription getDescription() { return description; }

    public Quantity getQuantity() { return quantity; }
    public void setQuantity(Quantity quantity) { this.quantity = quantity; }

    public Money getPrice() { return price; }
    public CategoryId getCategoryId() { return categoryId; }
}
