package com.example.ai.problem.noughtsandcrosses;

import static com.example.ai.problem.noughtsandcrosses.NoughtsAndCrossesNodeFactory.createNoughtsAndCrossesNode;
import static com.example.ai.problem.noughtsandcrosses.Square.BOTTOM_CENTRE;
import static com.example.ai.problem.noughtsandcrosses.Square.BOTTOM_LEFT;
import static com.example.ai.problem.noughtsandcrosses.Square.BOTTOM_RIGHT;
import static com.example.ai.problem.noughtsandcrosses.Square.CENTRE;
import static com.example.ai.problem.noughtsandcrosses.Square.MIDDLE_LEFT;
import static com.example.ai.problem.noughtsandcrosses.Square.TOP_CENTRE;
import static com.example.ai.problem.noughtsandcrosses.Square.TOP_RIGHT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.EnumSet;
import java.util.Map;

import org.junit.Test;

import com.example.ai.minimax.MiniMaxPlayer;

public class NoughtsAndCrossesNodeTest {
   /** On first move, all 9 squares should be suggested as potential next moves */
   @Test
   public void testEmpty() {
      assertChildren("---" + "---" + "---", Square.values());
   }

   /** If a player can complete a line on their next move, that move should be the only suggestion made */
   @Test
   public void testPlayer1WinningMove() {
      assertChildren("x-o" + "-o-" + "x--", MIDDLE_LEFT);
   }

   /** If a player can complete a line on their next move, that move should be the only suggestion made */
   @Test
   public void testPlayer2WinningMove() {
      assertChildren("x-o" + "-ox" + "--x", BOTTOM_LEFT);
   }

   /**
    * If a player can block a winning next move of their opponent, that move should be the only suggestion made
    * <p>
    * (If no winning option for self.)
    */
   @Test
   public void testPlayer1BlockWinningMove() {
      assertChildren("o-o" + "x--" + "--x", TOP_CENTRE);
   }

   /**
    * If a player can block a winning next move of their opponent, that move should be the only suggestion made
    * <p>
    * (If no winning option for self.)
    */
   @Test
   public void testPlayer2BlockWinningMove() {
      assertChildren("x-x" + "---" + "--o", TOP_CENTRE);
   }

   /** Taking a winning line takes priority over blocking possible winning line of opponent */
   @Test
   public void testPlayer1WinningMoveTakesPriorityOverBlock() {
      assertChildren("x-o" + "--o" + "x--", MIDDLE_LEFT);
   }

   /** Taking a winning line takes priority over blocking possible winning line of opponent */
   @Test
   public void testPlayer2WinningMoveTakesPriorityOverBlock() {
      assertChildren("xo-" + "-o-" + "x-x", BOTTOM_CENTRE);
   }

   /** If no option to take or block a winning line, all remaining squares should be suggested */
   @Test
   public void testAllFreeSquaresSuggested() {
      assertChildren("xo-" + "--x" + "---", TOP_RIGHT, MIDDLE_LEFT, CENTRE, BOTTOM_LEFT, BOTTOM_CENTRE, BOTTOM_RIGHT);
   }

   @Test
   public void testCopyOfState() {
      // test getCopyOfState() returns a copy (not the same version)
      NoughtsAndCrossesNode n = createNoughtsAndCrossesNode("x-o" + "-o-" + "--x");
      assertNotSame(n.getCopyOfState(), n.getCopyOfState());
      assertEquals(n.getCopyOfState(), n.getCopyOfState());
   }

   private void assertChildren(String board, Square... expectedMoves) {
      NoughtsAndCrossesNode originalState = createNoughtsAndCrossesNode(board);
      NoughtsAndCrossesNode[] expectedChildren = new NoughtsAndCrossesNode[expectedMoves.length];
      for (int i = 0; i < expectedMoves.length; i++) {
         expectedChildren[i] = createNode(originalState, expectedMoves[i]);
      }
      assertChildren(originalState, expectedChildren);
   }

   private void assertChildren(NoughtsAndCrossesNode originalState, NoughtsAndCrossesNode[] expectedChildren) {
      NoughtsAndCrossesNode[] actualChildren = originalState.getChildren();
      assertEquals(expectedChildren.length, actualChildren.length);
      for (int i = 0; i < expectedChildren.length; i++) {
         assertNodesEqual(expectedChildren[i], actualChildren[i]);
      }
   }

   private void assertNodesEqual(NoughtsAndCrossesNode n1, NoughtsAndCrossesNode n2) {
      Map<MiniMaxPlayer, EnumSet<Square>> map1 = n1.getCopyOfState();
      Map<MiniMaxPlayer, EnumSet<Square>> map2 = n2.getCopyOfState();
      for (MiniMaxPlayer p : MiniMaxPlayer.values()) {
         EnumSet<Square> set1 = map1.get(p);
         EnumSet<Square> set2 = map2.get(p);
         assertNotSame(set1, set2);
         assertEquals(set1, set2);
      }
   }

   private NoughtsAndCrossesNode createNode(NoughtsAndCrossesNode n, Square s) {
      final Map<MiniMaxPlayer, EnumSet<Square>> state = n.getCopyOfState();
      state.get(n.getPlayerToMoveNext()).add(s);
      return new NoughtsAndCrossesNode(state);
   }
}
