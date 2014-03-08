package com.how2examples.ai.ga;

import junit.framework.TestCase;

public class ChromosoneTest extends TestCase {
   public void testGetters() {
      Object o = new Object();
      Chromosone<Object> c = new Chromosone<>(o, 123.45);
      assertSame(o, c.getValue());
      assertEquals(123.45, c.getFitness());
   }
}
