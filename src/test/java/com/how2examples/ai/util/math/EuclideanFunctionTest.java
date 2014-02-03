package com.how2examples.ai.util.math;

import junit.framework.TestCase;

public class EuclideanFunctionTest extends TestCase {
   private static final EuclideanFunction TEST_OBJECT = new EuclideanFunction();

   public void test() {
      assertEquals(0.0, TEST_OBJECT.calculate(new double[] {1, 1}, new double[] {1, 1}));
      assertEquals(5.0, TEST_OBJECT.calculate(new double[] {2, -1}, new double[] {-2, 2}));
      assertEquals(5.0, TEST_OBJECT.calculate(new double[] {-2, 2}, new double[] {2, -1}));
   }

   public void testDifferentLengthArrays() {
      try {
         TEST_OBJECT.calculate(new double[] {1}, new double[] {1, 1});
         fail();
      } catch (final IllegalArgumentException e) {
         // expected
      }
   }
}
