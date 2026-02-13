package com.e_commerce.product.Unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.e_commerce.API.Http.Requests.ProductPurchaseRequest;
import com.e_commerce.API.Http.Requests.ProductRequest;
import com.e_commerce.Application.Errors.ProductPurchaseException;
import com.e_commerce.Application.Interfaces.IProductReadRepository;
import com.e_commerce.Application.Product.Dtos.ProductDto;
import com.e_commerce.Application.Product.Services.ProductService;
import com.e_commerce.Core.ProductAggregate.ProductAggregate;
import com.e_commerce.Core.ProductAggregate.Interfaces.IProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    
    @Mock
    private IProductRepository repository;

    @Mock
    private IProductReadRepository productReadRepository;

    @InjectMocks
    private ProductService productService;

    // ---------- CREATE PRODUCT ----------

    @Test
    void createProduct_successfully_creates_product() {
        // given
        ProductRequest request = new ProductRequest(
                "Laptop",
                "Gaming laptop",
                10,
                new BigDecimal("1200.00"),
                1
        );

        // when
        ProductDto result = productService.createProduct(request);

        // then
        assertEquals("Laptop", result.name());
        assertEquals("Gaming laptop", result.description());
        assertEquals(10, result.availableQuantity());
        assertEquals(new BigDecimal("1200.00"), result.price());

        // Checking that the service called the mock repository with a ProductAggregate
        verify(repository).save(any(ProductAggregate.class));
    }

    // ---------- PURCHASE PRODUCTS ----------

    @Test
    void purchaseProducts_decreases_stock_successfully() {
        // given
        ProductAggregate product = ProductAggregate.reconstruct(
                1,
                "Phone",
                "Smart phone",
                10,
                new BigDecimal("500.00"),
                1
        );

        when(repository.findAllByIdInOrderById(List.of(1)))
                .thenReturn(List.of(product));

        List<ProductPurchaseRequest> request = List.of(
                new ProductPurchaseRequest(1, 3)
        );

        // when
        var result = productService.purchaseProducts(request);

        // then
        assertEquals(result.size(), 1);
        assertEquals(product.getQuantity().value(), 7);

        verify(repository).update(product);
    }

    @Test
    void purchaseProducts_fails_when_stock_is_insufficient() {
        // given
        ProductAggregate product = ProductAggregate.reconstruct(
                1,
                "Phone",
                "Smart phone",
                2,
                new BigDecimal("500.00"),
                1
        );

        when(repository.findAllByIdInOrderById(List.of(1)))
                .thenReturn(List.of(product));

        List<ProductPurchaseRequest> request = List.of(
                new ProductPurchaseRequest(1, 5)
        );

        // when / then
        assertThrows(ProductPurchaseException.class, () -> productService.purchaseProducts(request));
        verify(repository, never()).update(any());
    }

    // ---------- BLANK NAME ----------

    @Test
    void createProduct_fails_when_name_is_blank() {
        ProductRequest request = new ProductRequest(
                " ", // blank name
                "Valid description",
                10,
                new BigDecimal("100.00"),
                1
        );

        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(request));

        verify(repository, never()).save(any(ProductAggregate.class));
    }

    // ---------- NEGATIVE QUANTITY ----------

    @Test
    void createProduct_fails_when_quantity_is_negative() {
        ProductRequest request = new ProductRequest(
                "Valid Name",
                "Valid description",
                -5, // negative quantity
                new BigDecimal("100.00"),
                1
        );

        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(request));

        verify(repository, never()).save(any(ProductAggregate.class));
    }

    // ---------- NULL DESCRIPTION ----------

        @Test
    void createProduct_fails_when_description_is_null() {
        ProductRequest request = new ProductRequest(
                "Valid Name",
                null, // null description
                5,
                new BigDecimal("100.00"),
                1
        );

        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(request));

        verify(repository, never()).save(any(ProductAggregate.class));
    }
}
