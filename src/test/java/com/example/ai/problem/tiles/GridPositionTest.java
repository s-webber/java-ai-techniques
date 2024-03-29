package com.example.ai.problem.tiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class GridPositionTest {
   @Test
   public void test() {
      // confirm each GridPosition refers to a unique element of a 3x3 grid
      final GridPosition[][] grid = new GridPosition[3][3];
      assertEquals(9, GridPosition.values().length);
      for (GridPosition s : GridPosition.values()) {
         assertNull(grid[s.x][s.y]);
         grid[s.x][s.y] = s;
      }
   }
}
