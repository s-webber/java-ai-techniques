package com.example.ai.util.math;

import static com.example.ai.TestUtils.assertExactlyEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class EuclideanFunctionTest {
   private static final EuclideanFunction TEST_OBJECT = new EuclideanFunction();

   @Test
   public void test() {
      assertExactlyEquals(0.0, TEST_OBJECT.calculate(new double[] {1, 1}, new double[] {1, 1}));
      assertExactlyEquals(5.0, TEST_OBJECT.calculate(new double[] {2, -1}, new double[] {-2, 2}));
      assertExactlyEquals(5.0, TEST_OBJECT.calculate(new double[] {-2, 2}, new double[] {2, -1}));
   }

   @Test
   public void testDifferentLengthArrays() {
      try {
         TEST_OBJECT.calculate(new double[] {1}, new double[] {1, 1});
         fail();
      } catch (final IllegalArgumentException e) {
         // expected
      }
   }
}
