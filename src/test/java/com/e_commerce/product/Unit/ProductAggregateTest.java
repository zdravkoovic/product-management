package com.e_commerce.product.Unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.e_commerce.Core.Errors.DomainException;
import com.e_commerce.Core.Events.DomainEvent;
import com.e_commerce.Core.ProductAggregate.ProductAggregate;
import com.e_commerce.Core.ProductAggregate.Events.ProductCreated;
import com.e_commerce.Core.ProductAggregate.Events.ProductNameChanged;
import com.e_commerce.Core.ProductAggregate.Events.ProductPriceChanged;
import com.e_commerce.Core.ProductAggregate.Events.StockDecreased;
import com.e_commerce.Core.ProductAggregate.Events.StockIncreased;

public class ProductAggregateTest {
    
    /* ---------- Creation ---------- */

    @Test
    @DisplayName("create() with valid data creates product and emits ProductCreated")
    void create_validProduct_emitsProductCreated() {
        var product = ProductAggregate.create(
            "Phone",
            "Smart phone",
            10,
            new BigDecimal("100"),
            1
        );

        List<DomainEvent> events = product.pullDomainEvents();

        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ProductCreated);
    }

    @Test
    @DisplayName("create() with negative quantity throws DomainException")
    void create_negativeQuantity_throwsException() {
        assertThrows(DomainException.class, () ->
            ProductAggregate.create(
                "Phone",
                "Desc",
                -1,
                BigDecimal.TEN,
                1
            )
        );
    }

    @Test
    @DisplayName("create() with negative price throws DomainException")
    void create_negativePrice_throwsException() {
        assertThrows(DomainException.class, () ->
            ProductAggregate.create(
                "Phone",
                "Desc",
                5,
                new BigDecimal("-1"),
                1
            )
        );
    }

    /* ---------- Stock management ---------- */

    @Test
    @DisplayName("increaseStock() increases quantity and emits StockIncreased")
    void increaseStock_validAmount_increasesQuantity() {
        var product = ProductAggregate.reconstruct(
            1,
            "Phone",
            "Desc",
            5,
            BigDecimal.TEN,
            1
        );

        product.increaseStock(3);

        assertEquals(8, product.getQuantity().value());

        var events = product.pullDomainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof StockIncreased);
    }

    @Test
    @DisplayName("increaseStock() with zero or negative amount throws DomainException")
    void increaseStock_invalidAmount_throwsException() {
        var product = ProductAggregate.reconstruct(
            1,
            "Phone",
            "Desc",
            5,
            BigDecimal.TEN,
            1
        );

        assertThrows(DomainException.class, () -> product.increaseStock(0));
        assertThrows(DomainException.class, () -> product.increaseStock(-1));
    }

    @Test
    @DisplayName("decreaseStock() decreases quantity and emits StockDecreased")
    void decreaseStock_validAmount_decreasesQuantity() {
        var product = ProductAggregate.reconstruct(
            1,
            "Phone",
            "Desc",
            5,
            BigDecimal.TEN,
            1
        );

        product.decreaseStock(2);

        assertEquals(3, product.getQuantity().value());

        var events = product.pullDomainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof StockDecreased);
    }

    @Test
    @DisplayName("decreaseStock() below zero throws DomainException")
    void decreaseStock_insufficientStock_throwsException() {
        var product = ProductAggregate.reconstruct(
            1,
            "Phone",
            "Desc",
            2,
            BigDecimal.TEN,
            1
        );

        assertThrows(DomainException.class, () -> product.decreaseStock(3));
    }

    /* ---------- Attribute updates ---------- */

    @Test
    @DisplayName("updatePrice() changes price and emits ProductPriceChanged")
    void updatePrice_validValue_changesPrice() {
        var product = ProductAggregate.reconstruct(
            1,
            "Phone",
            "Desc",
            5,
            BigDecimal.TEN,
            1
        );

        product.updatePrice(new BigDecimal("15"));

        assertEquals(new BigDecimal("15"), product.getPrice().value());

        var events = product.pullDomainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ProductPriceChanged);
    }

    @Test
    @DisplayName("updatePrice() with negative value throws DomainException")
    void updatePrice_negativeValue_throwsException() {
        var product = ProductAggregate.reconstruct(
            1,
            "Phone",
            "Desc",
            5,
            BigDecimal.TEN,
            1
        );

        assertThrows(DomainException.class, () ->
            product.updatePrice(new BigDecimal("-10"))
        );
    }

    @Test
    @DisplayName("updateName() changes name and emits ProductNameChanged")
    void updateName_validValue_changesName() {
        var product = ProductAggregate.reconstruct(
            1,
            "Phone",
            "Desc",
            5,
            BigDecimal.TEN,
            1
        );

        product.updateName("New Phone");

        assertEquals("New Phone", product.getName().value());

        var events = product.pullDomainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ProductNameChanged);
    }

    @Test
    @DisplayName("updateName() with blank value throws DomainException")
    void updateName_blankValue_throwsException() {
        var product = ProductAggregate.reconstruct(
            1,
            "Phone",
            "Desc",
            5,
            BigDecimal.TEN,
            1
        );

        assertThrows(DomainException.class, () ->
            product.updateName(" ")
        );
    }

        /* ---------- Domain event mechanics ---------- */

    @Test
    @DisplayName("pullDomainEvents() clears internal event list")
    void pullDomainEvents_clearsEvents() {
        var product = ProductAggregate.create(
            "Phone",
            "Desc",
            5,
            BigDecimal.TEN,
            1
        );

        var firstPull = product.pullDomainEvents();
        var secondPull = product.pullDomainEvents();

        assertEquals(1, firstPull.size());
        assertEquals(0, secondPull.size());
    }
}
