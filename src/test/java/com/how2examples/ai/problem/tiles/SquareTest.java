package com.how2examples.ai.problem.tiles;

import junit.framework.TestCase;

public class SquareTest extends TestCase {
   public void test() {
      // confirm each Square refers to a unique element of a 3x3 grid
      final Square[][] grid = new Square[3][3];
      assertEquals(9, Square.values().length);
      for (Square s : Square.values()) {
         assertNull(grid[s.x][s.y]);
         grid[s.x][s.y] = s;
      }
   }
}
