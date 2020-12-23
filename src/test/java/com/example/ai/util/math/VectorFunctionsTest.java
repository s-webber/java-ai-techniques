package com.example.ai.util.math;

import static com.example.ai.TestUtils.assertExactlyEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class VectorFunctionsTest {
   @Test
   public void testAverageVectors() {
      final double[][] input = new double[][] { {5.3, 18, 333}, {6, -27, 800}, {10.6, 6, 1600}};
      final double[] output = VectorFunctions.averageVectors(input);
      assertExactlyEquals(7.3, output[0]);
      assertExactlyEquals(-1.0, output[1]);
      assertExactlyEquals(911.0, output[2]);
   }

   @Test
   public void testAssertArrayLengthsMatchTrue() {
      for (int i = 0; i < 4; i++) {
         VectorFunctions.assertArrayLengthsMatch(new double[i], new double[i]);
      }
   }

   @Test
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
