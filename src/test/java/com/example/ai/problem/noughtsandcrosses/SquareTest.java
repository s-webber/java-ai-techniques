package com.example.ai.problem.noughtsandcrosses;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SquareTest {
   @Test
   public void testSize() {
      assertEquals(9, Square.values().length);
   }
}
