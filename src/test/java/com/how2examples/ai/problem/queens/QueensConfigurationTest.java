package com.how2examples.ai.problem.queens;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class QueensConfigurationTest {
   @Test
   public void test1x1() {
      final QueensConfiguration c = new QueensConfiguration(1);
      assertEquals(1, c.getBoardWidth());
      assertEquals(1, c.getSquareId(0, 0));
      assertEquals(1, c.getCoverage(0, 0));
   }

   @Test
   public void test3x3() {
      final int boardWidth = 3;
      final int a3 = 1;
      final int b3 = 2;
      final int c3 = 4;
      final int a2 = 8;
      final int b2 = 16;
      final int c2 = 32;
      final int a1 = 64;
      final int b1 = 128;
      final int c1 = 256;
      final int allSquares = 511;

      final QueensConfiguration c = new QueensConfiguration(boardWidth);
      assertEquals(boardWidth, c.getBoardWidth());

      assertEquals(a3, c.getSquareId(0, 0));
      assertEquals(b3, c.getSquareId(0, 1));
      assertEquals(c3, c.getSquareId(0, 2));
      assertEquals(a2, c.getSquareId(1, 0));
      assertEquals(b2, c.getSquareId(1, 1));
      assertEquals(c2, c.getSquareId(1, 2));
      assertEquals(a1, c.getSquareId(2, 0));
      assertEquals(b1, c.getSquareId(2, 1));
      assertEquals(c1, c.getSquareId(2, 2));

      assertEquals(allSquares - b1 - c2, c.getCoverage(0, 0));
      assertEquals(allSquares - a1 - c1, c.getCoverage(0, 1));
      assertEquals(allSquares - a2 - b1, c.getCoverage(0, 2));
      assertEquals(allSquares - c1 - c3, c.getCoverage(1, 0));
      assertEquals(allSquares, c.getCoverage(1, 1));
      assertEquals(allSquares - a1 - a3, c.getCoverage(1, 2));
      assertEquals(allSquares - b3 - c2, c.getCoverage(2, 0));
      assertEquals(allSquares - a3 - c3, c.getCoverage(2, 1));
      assertEquals(allSquares - b3 - a2, c.getCoverage(2, 2));
   }

   @Test
   public void test0x0() {
      try {
         new QueensConfiguration(0);
         fail();
      } catch (IllegalArgumentException e) {
         assertEquals("board width: 0", e.getMessage());
      }
   }

   @Test
   public void test9x9() {
      try {
         new QueensConfiguration(9);
         fail();
      } catch (IllegalArgumentException e) {
         assertEquals("board width: 9", e.getMessage());
      }
   }
}
