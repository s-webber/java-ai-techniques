package com.example.ai.problem.noughtsandcrosses;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SquareTest {
   @Test
   public void testSize() {
      assertEquals(9, Square.values().length);
   }
}
