package com.unipi.ipap.springintegrationtest.repository;

import com.unipi.ipap.springintegrationtest.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByName(String string);
}
