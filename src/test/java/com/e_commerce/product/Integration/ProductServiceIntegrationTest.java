package com.e_commerce.product.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.e_commerce.API.Http.Requests.ProductRequest;
import com.e_commerce.Application.Product.Dtos.ProductDto;
import com.e_commerce.Application.Product.Services.ProductService;

@Tag("integration")
@SpringBootTest(properties = {
    "eureka.client.enabled=false",
    "spring.cloud.discovery.enabled=false"
})
@Testcontainers
public class ProductServiceIntegrationTest {
        
    @SuppressWarnings("resource")
    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("product_test")
                    .withUsername("postgres")
                    .withPassword("postgres");
    
    @DynamicPropertySource
    static void datasourceProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    
    @Autowired
    private ProductService productService;

    // ---------------- CREATE ----------------

    @Test
    void createProduct_persists_and_returns_product() {
        var price = new BigDecimal("1200.0");

        ProductRequest request = new ProductRequest(
                "Laptop",
                "Gaming laptop",
                10,
                price,
                1
        );
        ProductDto result = productService.createProduct(request);

        assertEquals(result.name(), "Laptop");
        assertEquals(result.availableQuantity(), 10);
        assertEquals(result.price(), price);
    }
}
