package com.unipi.ipap.springintegrationtest.service;

import com.unipi.ipap.springintegrationtest.entity.Product;
import com.unipi.ipap.springintegrationtest.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceTest {
    @InjectMocks // or autowired
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;

    @Test
    public void testSaveProductToDB() {

        Product product = new Product("Test Product 1", 3, 45);

        when(productRepository.save(product)).thenReturn(product);

        Product response = productService.saveProduct(product);

        assertAll(() -> {
            assertEquals(response.getName(), product.getName());
            assertEquals(response.getQuantity(), product.getQuantity());
            assertEquals(response.getPrice(), product.getPrice());
        });

    }
}
