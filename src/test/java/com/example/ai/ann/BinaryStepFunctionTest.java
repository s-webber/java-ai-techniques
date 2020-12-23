package com.example.ai.ann;

import static com.example.ai.TestUtils.assertExactlyEquals;

import org.junit.Test;

public class BinaryStepFunctionTest {
   private final BinaryStepFunction f = new BinaryStepFunction();

   @Test
   public void test() {
      assertExactlyEquals(1.0, f.activate(Double.MAX_VALUE));
      assertExactlyEquals(1.0, f.activate(1));
      assertExactlyEquals(1.0, f.activate(0.001));
      assertExactlyEquals(0.0, f.activate(0));
      assertExactlyEquals(0.0, f.activate(-0.001));
      assertExactlyEquals(0.0, f.activate(-1));
      assertExactlyEquals(0.0, f.activate(-Double.MAX_VALUE));
   }
}
