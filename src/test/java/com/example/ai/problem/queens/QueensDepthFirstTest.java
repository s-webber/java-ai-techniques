package com.example.ai.problem.queens;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.example.ai.search.DepthFirstSearchStrategy;
import com.example.ai.search.TreeSearch;

public class QueensDepthFirstTest {
   private static final boolean Q = true;
   private static final boolean _ = false;

   @Test
   public void testOneQueen() {
      boolean[][] expectedSolution = {{Q}};
      assertFindSolution(expectedSolution);
   }

   @Test
   public void testTwoQueens() {
      // impossible - so expect null
      assertNull(findSolution(2));
   }

   @Test
   public void testFourQueens() {
      boolean[][] expectedSolution = { {_, Q, _, _}, {_, _, _, Q}, {Q, _, _, _}, {_, _, Q, _}};
      assertFindSolution(expectedSolution);
   }

   @Test
   public void testEightQueens() {
      boolean[][] expectedSolution = { {Q, _, _, _, _, _, _, _}, {_, _, _, _, Q, _, _, _}, {_, _, _, _, _, _, _, Q}, {_, _, _, _, _, Q, _, _}, {_, _, Q, _, _, _, _, _}, {_, _, _, _, _, _, Q, _}, {_, Q, _, _, _, _, _, _}, {_, _, _, Q, _, _, _, _}};
      assertFindSolution(expectedSolution);
   }

   private void assertFindSolution(boolean[][] expectedSolution) {
      assertEquals("board is not square", expectedSolution.length, expectedSolution[0].length);

      final int boardWidth = expectedSolution.length;
      final QueensNode actualSolution = findSolution(boardWidth);

      assertNotNull(actualSolution);
      assertTrue(actualSolution.isGoal());

      int actualNumberOfQueens = 0;
      for (int f = 0; f < boardWidth; f++) {
         for (int r = 0; r < boardWidth; r++) {
            final boolean isSet = actualSolution.isSet(f, r);
            assertEquals(expectedSolution[f][r], isSet);
            if (isSet) {
               actualNumberOfQueens++;
            }
         }
      }
      assertEquals(boardWidth, actualNumberOfQueens);
   }

   private QueensNode findSolution(int boardWidth) {
      final QueensNode rootNode = new QueensNode(new QueensConfiguration(boardWidth));
      final DepthFirstSearchStrategy<QueensNode> strategy = new DepthFirstSearchStrategy<QueensNode>();
      return TreeSearch.search(rootNode, strategy);
   }
}
