package com.unipi.ipap.springintegrationtest.util;

import org.springframework.stereotype.Service;

/**
 * Mockito, invoke catch blocks
 */

@Service
public class CalculatorService {

    public double sum(double n1, double n2) {
        return n1 + n2;
    }
}
