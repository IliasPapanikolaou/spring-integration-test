package com.unipi.ipap.springintegrationtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringIntegrationTestApplication {

//    @Autowired
//    private ProductRepository productRepository;
//
//    @PostConstruct
//    public void init() {
//        productRepository.saveAll(
//                List.of(
//                        new Product("PlayStation", 3, 600),
//                        new Product("Xbox", 3, 550),
//                        new Product("iPhone", 10, 900)
//                )
//        );
//    }

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationTestApplication.class, args);
    }

}
