package com.how2examples.ai.problem.noughtsandcrosses;

import static com.how2examples.ai.problem.noughtsandcrosses.Square.BOTTOM_LEFT;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.BOTTOM_RIGHT;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.CENTRE;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.MIDDLE_LEFT;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.TOP_LEFT;
import static com.how2examples.ai.problem.noughtsandcrosses.Symbol.CROSS;
import static com.how2examples.ai.problem.noughtsandcrosses.Symbol.NOUGHT;

import java.util.EnumSet;

import junit.framework.TestCase;

import com.how2examples.ai.search.MiniMaxNode;
import com.how2examples.ai.search.MiniMaxSearch;

public class NoughtsAndCrossesTest extends TestCase {
   public void testMultipleGames() {
      NoughtsAndCrossesNode n = NoughtsAndCrossesNodeFactory.createNoughtsAndCrossesNode();
      MiniMaxNode[] children = n.getChildren();
      for (MiniMaxNode child : children) {
         NoughtsAndCrossesNode c2 = (NoughtsAndCrossesNode) child;
         System.out.println(c2);
      }

      while (children.length != 0) {
         System.out.println(children[0]);
         children = children[0].getChildren();
      }

      while (!n.isComplete()) {
         n = (NoughtsAndCrossesNode) MiniMaxSearch.minimax(n);
         System.out.println(n + " " + n.getGoodness());
      }
   }

   public void testBlockImmediateWinningLine() {
      String t = "xo-";
      String m = "---";
      String b = "x--";
      assertNextMove(t, m, b, NOUGHT, MIDDLE_LEFT);
   }

   public void testFutureWinningState() {
      String t = "x-o";
      String m = "o--";
      String b = "xo-";
      assertNextMove(t, m, b, CROSS, BOTTOM_RIGHT);
   }

   public void testBlockFutureWinningState() {
      String t = "--x";
      String m = "--o";
      String b = "o-x";
      assertNextMove(t, m, b, CROSS, TOP_LEFT);
   }

   public void testBlockAndWinInSingleMove() {
      String t = "x-o";
      String m = "xo-";
      String b = "--x";
      assertNextMove(t, m, b, NOUGHT, BOTTOM_LEFT);
   }

   public void testTakeWinningLine() {
      String t = "xxo";
      String m = "x--";
      String b = "o--";
      assertNextMove(t, m, b, NOUGHT, CENTRE);
   }

   private void assertNextMove(String topLine, String middleLine, String bottomLine, Symbol symbolToMoveNext, Square expectedNextMove) {
      String board = topLine + middleLine + bottomLine;
      NoughtsAndCrossesNode originalState = NoughtsAndCrossesNodeFactory.createNoughtsAndCrossesNode(board);
      NoughtsAndCrossesNode newState = (NoughtsAndCrossesNode) MiniMaxSearch.minimax(originalState);
      Square actualNextMove = getNextMove(symbolToMoveNext, originalState, newState);
      assertSame(expectedNextMove, actualNextMove);
   }

   private Square getNextMove(Symbol symbolToMoveNext, NoughtsAndCrossesNode originalState, NoughtsAndCrossesNode newState) {
      EnumSet<Square> originalSymbolSquares = originalState.createCopyOfState().get(symbolToMoveNext);
      EnumSet<Square> newSymbolSquares = newState.createCopyOfState().get(symbolToMoveNext);
      return getNextMove(newSymbolSquares, originalSymbolSquares);
   }

   private static Square getNextMove(EnumSet<Square> newSymbolSquares, EnumSet<Square> originalSymbolSquares) {
      Square result = null;
      if (newSymbolSquares.size() != originalSymbolSquares.size() + 1) {
         throw new IllegalStateException();
      }
      for (Square s : newSymbolSquares) {
         if (!originalSymbolSquares.contains(s)) {
            if (result != null) {
               throw new IllegalStateException();
            }
            result = s;
         }
      }
      if (result == null) {
         throw new IllegalStateException();
      }
      return result;
   }
}
