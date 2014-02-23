package com.how2examples.ai.problem.noughtsandcrosses;

import static com.how2examples.ai.problem.noughtsandcrosses.Square.BOTTOM_CENTRE;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.BOTTOM_LEFT;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.BOTTOM_RIGHT;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.CENTRE;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.MIDDLE_LEFT;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.MIDDLE_RIGHT;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.TOP_CENTRE;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.TOP_LEFT;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.TOP_RIGHT;

import java.util.EnumSet;

import junit.framework.TestCase;

import com.how2examples.ai.minimax.MiniMaxPlayer;
import com.how2examples.ai.minimax.MiniMaxSearch;

public class NoughtsAndCrossesMiniMaxTest extends TestCase {

   private static final String BLANK = "---";

   public void testTakeWinningLine() {
      String t = "xxo";
      String m = "x--";
      String b = "o--";
      assertMoves(t, m, b, CENTRE);
   }

   public void testBlockImmediateWinningLine() {
      String t = "xo-";
      String m = BLANK;
      String b = "x--";
      assertMoves(t, m, b, MIDDLE_LEFT, BOTTOM_RIGHT, BOTTOM_CENTRE, CENTRE);
   }

   public void testBlockAndWinInSingleMove() {
      String t = "x-o";
      String m = "xo-";
      String b = "--x";
      assertMoves(t, m, b, BOTTOM_LEFT);
   }

   public void testWinRatherThanBlock() {
      String t = "x-x";
      String m = BLANK;
      String b = "oo-";
      assertMoves(t, m, b, TOP_CENTRE);
   }

   public void testCreateFork_1() {
      String t = "x-o";
      String m = "o--";
      String b = "x--";
      assertMoves(t, m, b, BOTTOM_RIGHT, BOTTOM_CENTRE, CENTRE);
   }

   public void testCreateFork_2() {
      String t = "--o";
      String m = "--x";
      String b = "x-o";
      assertMoves(t, m, b, MIDDLE_LEFT, CENTRE, TOP_LEFT);
   }

   public void testCreateFork_3() {
      String t = BLANK;
      String m = "o--";
      String b = "xox";
      assertMoves(t, m, b, CENTRE, TOP_RIGHT, TOP_LEFT);
   }

   public void testAvoidOpponentFork_1() {
      String t = "--x";
      String m = "-o-";
      String b = "x--";
      assertMoves(t, m, b, BOTTOM_CENTRE, TOP_CENTRE, TOP_LEFT, BOTTOM_RIGHT, MIDDLE_RIGHT, MIDDLE_LEFT);
   }

   public void testAvoidOpponentFork_2() {
      String t = BLANK;
      String m = BLANK;
      String b = "xox";
      assertMoves(t, m, b, CENTRE, TOP_CENTRE, MIDDLE_RIGHT, MIDDLE_LEFT, TOP_LEFT, TOP_RIGHT);
   }

   public void testFullGame() {
      assertMoves(BLANK, BLANK, BLANK, BOTTOM_RIGHT, CENTRE, BOTTOM_CENTRE, BOTTOM_LEFT, TOP_RIGHT, MIDDLE_RIGHT, MIDDLE_LEFT, TOP_CENTRE, TOP_LEFT);
   }

   public void testAfterFirstMoveCentre() {
      String t = BLANK;
      String m = "-x-";
      String b = BLANK;
      assertMoves(t, m, b, BOTTOM_RIGHT, BOTTOM_CENTRE, TOP_CENTRE, MIDDLE_RIGHT, MIDDLE_LEFT, BOTTOM_LEFT, TOP_RIGHT, TOP_LEFT);
   }

   public void testAfterFirstMoveTopCentre() {
      String t = "-x-";
      String m = BLANK;
      String b = BLANK;
      assertMoves(t, m, b, BOTTOM_CENTRE, BOTTOM_RIGHT, TOP_RIGHT, BOTTOM_LEFT, CENTRE, MIDDLE_RIGHT, MIDDLE_LEFT, TOP_LEFT);
   }

   private void assertMoves(String topLine, String middleLine, String bottomLine, Square... expectedMoves) {
      String board = topLine + middleLine + bottomLine;
      NoughtsAndCrossesNode state = NoughtsAndCrossesNodeFactory.createNoughtsAndCrossesNode(board);
      for (Square expectedMove : expectedMoves) {
         state = assertNextMove(state, expectedMove);
      }
      assertNull(minimax(state));
   }

   private NoughtsAndCrossesNode assertNextMove(NoughtsAndCrossesNode originalState, Square expectedNextMove) {
      NoughtsAndCrossesNode newState = minimax(originalState);
      Square actualNextMove = getNextMove(originalState, newState);
      assertSame(expectedNextMove, actualNextMove);
      return newState;
   }

   private NoughtsAndCrossesNode minimax(NoughtsAndCrossesNode originalState) {
      return (NoughtsAndCrossesNode) MiniMaxSearch.minimax(originalState, originalState.getPlayerToMoveNext());
   }

   private Square getNextMove(NoughtsAndCrossesNode originalState, NoughtsAndCrossesNode newState) {
      MiniMaxPlayer playerToMoveNext = originalState.getPlayerToMoveNext();
      EnumSet<Square> originalSymbolSquares = originalState.createCopyOfState().get(playerToMoveNext);
      EnumSet<Square> newSymbolSquares = newState.createCopyOfState().get(playerToMoveNext);
      return getNextMove(newSymbolSquares, originalSymbolSquares);
   }

   private static Square getNextMove(EnumSet<Square> newSymbolSquares, EnumSet<Square> originalSymbolSquares) {
      Square result = null;
      if (newSymbolSquares.size() != originalSymbolSquares.size() + 1) {
         throw new IllegalStateException("was: " + originalSymbolSquares + " now: " + newSymbolSquares);
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
