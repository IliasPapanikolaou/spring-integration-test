package com.unipi.ipap.springintegrationtest.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Mockito, invoke catch blocks
 */

@ExtendWith(MockitoExtension.class)
public class CalculatorTest {

    @InjectMocks
    Calculator calculator;

    @Mock
    CalculatorService calculatorService;

    @BeforeEach
    void init() {
        calculator = Mockito.spy(new Calculator(calculatorService));
    }

    @Test
    public void addNumberNullPointerExceptionTest() throws Exception {
        Mockito.lenient().doThrow(new NullPointerException())
                .when(calculatorService)
                .sum(Mockito.anyDouble(), Mockito.anyDouble());

        assertThrows(NullPointerException.class, () -> calculator.addNumbers(10, 5));
    }

    @Test
    public void addNumberFormatExceptionTest() throws Exception {
        Mockito.lenient().doThrow(new NumberFormatException())
                .when(calculatorService)
                .sum(Mockito.anyDouble(), Mockito.anyDouble());

        assertThrows(NumberFormatException.class, () -> calculator.addNumbers(10, 5));
    }

    @Test
    public void addNumberTest() throws Exception {

        when(calculatorService.sum(Mockito.anyDouble(),Mockito.anyDouble())).thenReturn(15D);

        assertEquals(15, calculator.addNumbers(0, 0)); // numbers in .addNumber don't matter
    }
}
