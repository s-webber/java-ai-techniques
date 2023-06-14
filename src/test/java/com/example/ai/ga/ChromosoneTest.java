package com.example.ai.ga;

import static com.example.ai.TestUtils.assertExactlyEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ChromosoneTest {
   @Test
   public void testGetters() {
      Object o = new Object();
      Chromosone<Object> c = new Chromosone<>(o, 123.45);
      assertSame(o, c.getValue());
      assertExactlyEquals(123.45, c.getFitness());
   }
}
