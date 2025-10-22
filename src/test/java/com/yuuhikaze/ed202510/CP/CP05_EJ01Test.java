package com.yuuhikaze.ed202510.CP;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Test class for CP05_EJ01 (Expression Tree evaluation)
 */
class CP05_EJ01Test {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testExpressionEvaluationWithX2() {
        // The expression is: ((x * x) - (2 * x)) + 1
        // With x = 2: (2*2) - (2*2) + 1 = 4 - 4 + 1 = 1
        ExpressionController ec = new ExpressionController();
        double result = ec.compute(2);
        assertEquals(1.0, result, 0.0001, "Expression should evaluate to 1.0 when x=2");
    }

    @Test
    void testExpressionEvaluationWithX0() {
        // With x = 0: (0*0) - (2*0) + 1 = 0 - 0 + 1 = 1
        ExpressionController ec = new ExpressionController();
        double result = ec.compute(0);
        assertEquals(1.0, result, 0.0001, "Expression should evaluate to 1.0 when x=0");
    }

    @Test
    void testExpressionEvaluationWithX3() {
        // With x = 3: (3*3) - (2*3) + 1 = 9 - 6 + 1 = 4
        ExpressionController ec = new ExpressionController();
        double result = ec.compute(3);
        assertEquals(4.0, result, 0.0001, "Expression should evaluate to 4.0 when x=3");
    }

    @Test
    void testExpressionEvaluationWithX5() {
        // With x = 5: (5*5) - (2*5) + 1 = 25 - 10 + 1 = 16
        ExpressionController ec = new ExpressionController();
        double result = ec.compute(5);
        assertEquals(16.0, result, 0.0001, "Expression should evaluate to 16.0 when x=5");
    }

    @Test
    void testExpressionEvaluationWithNegativeX() {
        // With x = -2: ((-2)*(-2)) - (2*(-2)) + 1 = 4 - (-4) + 1 = 9
        ExpressionController ec = new ExpressionController();
        double result = ec.compute(-2);
        assertEquals(9.0, result, 0.0001, "Expression should evaluate to 9.0 when x=-2");
    }

    @Test
    void testTracePathNotNull() {
        ExpressionController ec = new ExpressionController();
        // Just test that tracePath doesn't throw an exception
        assertDoesNotThrow(() -> ec.tracePath());
    }

    @Test
    void testTracePathPrettyNotNull() {
        ExpressionController ec = new ExpressionController();
        // Just test that tracePathPretty doesn't throw an exception
        assertDoesNotThrow(() -> ec.tracePathPretty());
    }

    @Test
    void testTracePathProducesOutput() {
        ExpressionController ec = new ExpressionController();
        ec.tracePath();
        String output = outContent.toString();
        assertFalse(output.isEmpty(), "tracePath should produce output");
        assertTrue(output.contains("x") || output.contains("·") || output.contains("+") || output.contains("-"),
                   "Output should contain expression elements");
    }

    @Test
    void testTracePathPrettyProducesOutput() {
        outContent.reset();
        ExpressionController ec = new ExpressionController();
        ec.tracePathPretty();
        String output = outContent.toString();
        assertFalse(output.isEmpty(), "tracePathPretty should produce output");
        assertTrue(output.contains("("), "Pretty output should contain parentheses");
    }

    @Test
    void testExpressionStructure() {
        // Test that the expression can be evaluated multiple times with different x values
        ExpressionController ec = new ExpressionController();
        double result1 = ec.compute(1);
        double result2 = ec.compute(2);
        double result3 = ec.compute(3);

        assertNotEquals(result1, result2, "Results should differ for different x values");
        assertNotEquals(result2, result3, "Results should differ for different x values");
    }

    void restoreStreams() {
        System.setOut(originalOut);
    }
}
