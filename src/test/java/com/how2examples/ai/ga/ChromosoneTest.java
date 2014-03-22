package com.how2examples.ai.ga;

import static com.how2examples.ai.TestUtils.assertExactlyEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class ChromosoneTest {
   @Test
   public void testGetters() {
      Object o = new Object();
      Chromosone<Object> c = new Chromosone<>(o, 123.45);
      assertSame(o, c.getValue());
      assertExactlyEquals(123.45, c.getFitness());
   }
}
