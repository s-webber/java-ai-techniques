package com.example.ai.ann;

import static com.example.ai.TestUtils.assertExactlyEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LogisticFunctionTest {
   private static final double DELTA = 0.001;

   private final LogisticFunction f = new LogisticFunction();

   @Test
   public void testZero() {
      assertExactlyEquals(0.5, f.activate(0));
   }

   /** Assert that possible output values range from 0 to 1. */
   @Test
   public void testRange() {
      assertFunctionOutput(-Double.MAX_VALUE, 0);
      assertFunctionOutput(-6, 0.002);
      assertFunctionOutput(-0.1, 0.475);
      assertFunctionOutput(Double.MIN_VALUE, 0.5);
      assertFunctionOutput(0.1, 0.525);
      assertFunctionOutput(6, 0.998);
      assertFunctionOutput(Double.MAX_VALUE, 1);
   }

   private void assertFunctionOutput(double input, double expectedOutput) {
      assertEquals(expectedOutput, f.activate(input), DELTA);
   }
}
