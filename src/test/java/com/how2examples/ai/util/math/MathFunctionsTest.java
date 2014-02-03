package com.how2examples.ai.util.math;

import junit.framework.TestCase;

public class MathFunctionsTest extends TestCase {
   public void testAverageVectors() {
      final double[][] input = new double[][] { {5.3, 18, 333}, {6, -27, 800}, {10.6, 6, 1600}};
      final double[] output = VectorFunctions.averageVectors(input);
      assertEquals(7.3, output[0]);
      assertEquals(-1.0, output[1]);
      assertEquals(911.0, output[2]);
   }

   public void testAssertArrayLengthsMatchTrue() {
      for (int i = 0; i < 4; i++) {
         VectorFunctions.assertArrayLengthsMatch(new double[i], new double[i]);
      }
   }

   public void testAssertArrayLengthsMatchTrueFalse() {
      assertDifferentArrayLengthsCausesException(0, 1);
      assertDifferentArrayLengthsCausesException(1, 0);
      assertDifferentArrayLengthsCausesException(2, 1);
      assertDifferentArrayLengthsCausesException(1, 2);
      assertDifferentArrayLengthsCausesException(1, 9);
      assertDifferentArrayLengthsCausesException(100, 0);
   }

   private void assertDifferentArrayLengthsCausesException(final int l1, final int l2) {
      try {
         VectorFunctions.assertArrayLengthsMatch(new double[l1], new double[l2]);
         fail();
      } catch (final RuntimeException e) {
         // expected
      }
   }
}
