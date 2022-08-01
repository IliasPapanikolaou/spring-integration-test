package com.unipi.ipap.springintegrationtest;

import com.unipi.ipap.springintegrationtest.entity.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)  // loads application context
class SpringIntegrationTestApplicationTests {

    @LocalServerPort // Get the test port
    private int port;
    private  String baseUrl = "http://localhost";
    private static RestTemplate restTemplate;
    @Autowired
    private TestH2Repository h2Repository;

    @BeforeAll // Before run all tests
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach // Before run each test
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(String.valueOf(port)).concat("/products");
        System.out.println(baseUrl);
    }

    @Test
    @Sql(statements = "DELETE FROM PRODUCT_TBL WHERE name = 'Samsung Galaxy S22'",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testAddProduct() {
        Product product = new Product("Samsung Galaxy S22", 9, 950);
        Product response = restTemplate.postForObject(baseUrl, product, Product.class);
        assertEquals(response.getName(), product.getName());
        assertEquals(1, h2Repository.findAll().size());
    }

    @Test
    @Sql(statements = "INSERT INTO PRODUCT_TBL(id, name, quantity, price) VALUES (4, 'Nintendo Switch', 2, 400)",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM PRODUCT_TBL WHERE name = 'Nintendo Switch'",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetProducts() {
        List<Product> products = restTemplate.getForObject(baseUrl, List.class);
        assertEquals(1, products.size());
    }

    @Test
    @Sql(statements = "INSERT INTO PRODUCT_TBL(id, name, quantity, price) VALUES (5, 'AirCondition', 4, 500)",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM PRODUCT_TBL WHERE name = 'AirCondition'",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindProductById() {
        Product product = restTemplate.getForObject(baseUrl + "/{id}", Product.class, 5);
        assertAll(
                () -> assertNotNull(product),
                () -> assertEquals(5, product.getId()),
                () -> assertEquals("AirCondition", product.getName())
        );
    }

    @Test
    @Sql(statements = "INSERT INTO PRODUCT_TBL(id, name, quantity, price) VALUES (6, 'Hair Drier', 4, 500)",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM PRODUCT_TBL WHERE name = 'Hair Drier'",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateProduct() {
        Product product = new Product("Hair Drier", 2, 850);
        restTemplate.put(baseUrl + "/update/{id}", product, 6);
        Product response = h2Repository.findById(6).get();
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals("Hair Drier", product.getName()),
                () -> assertEquals(2, product.getQuantity()),
                () -> assertEquals(850, product.getPrice())
        );
    }

    @Test
    @Sql(statements = "INSERT INTO PRODUCT_TBL(id, name, quantity, price) VALUES (7, 'Adidas Shoes', 10, 80)",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testDeleteProduct() {
        assertEquals(1, h2Repository.findAll().size());
        restTemplate.delete(baseUrl + "/delete/{id}", 7);
        assertEquals(0, h2Repository.findAll().size());
    }

}
