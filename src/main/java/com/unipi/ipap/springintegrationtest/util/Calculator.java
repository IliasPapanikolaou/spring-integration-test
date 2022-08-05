package com.unipi.ipap.springintegrationtest.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mockito, invoke catch blocks
 */

@Component
@Slf4j
public class Calculator {

    private final CalculatorService calculatorService;

    public Calculator(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    public double addNumbers(double n1, double n2) throws Exception {
        try {
            double sum = calculatorService.sum(n1, n2);
            log.info("Sum of number A and number B is {}", sum);
            return sum;
        } catch (NullPointerException e) {
            log.warn("Null pointer exception: {}", e.getMessage());
            throw new NullPointerException();
        } catch (NumberFormatException e) {
            log.warn("Number format exception: {}", e.getMessage());
            throw new NumberFormatException();
        } catch (Exception e) {
            log.warn("Exception has occurred: {}", e.getMessage());
            throw new Exception();
        } finally {
            log.info("Finally block");
        }
    }
}
