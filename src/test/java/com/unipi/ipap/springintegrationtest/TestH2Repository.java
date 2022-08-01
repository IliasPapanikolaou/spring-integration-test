package com.unipi.ipap.springintegrationtest;

import com.unipi.ipap.springintegrationtest.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2Repository extends JpaRepository<Product, Integer> {
}
