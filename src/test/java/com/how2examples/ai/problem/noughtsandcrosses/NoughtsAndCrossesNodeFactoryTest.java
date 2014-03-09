package com.how2examples.ai.problem.noughtsandcrosses;

import static com.how2examples.ai.minimax.MiniMaxPlayer.PLAYER_1;
import static com.how2examples.ai.minimax.MiniMaxPlayer.PLAYER_2;
import static com.how2examples.ai.problem.noughtsandcrosses.NoughtsAndCrossesNodeFactory.createNoughtsAndCrossesNode;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.BOTTOM_CENTRE;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.BOTTOM_LEFT;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.BOTTOM_RIGHT;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.CENTRE;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.MIDDLE_LEFT;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.MIDDLE_RIGHT;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.TOP_CENTRE;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.TOP_LEFT;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.TOP_RIGHT;
import static java.util.EnumSet.complementOf;
import static java.util.EnumSet.noneOf;
import static java.util.EnumSet.of;

import java.util.EnumSet;
import java.util.Map;

import junit.framework.TestCase;

import com.how2examples.ai.minimax.MiniMaxPlayer;

public class NoughtsAndCrossesNodeFactoryTest extends TestCase {
   public void testEmpty1() {
      NoughtsAndCrossesNode n = createNoughtsAndCrossesNode();
      assertNodeState(n, noneOf(Square.class), noneOf(Square.class));
   }

   public void testEmpty2() {
      assertNodeState("---" + "---" + "---", noneOf(Square.class), noneOf(Square.class));
   }

   public void testTopLeft() {
      assertNodeState("x--" + "---" + "---", TOP_LEFT);
   }

   public void testTopCentre() {
      assertNodeState("-x-" + "---" + "---", TOP_CENTRE);
   }

   public void testTopRight() {
      assertNodeState("--x" + "---" + "---", TOP_RIGHT);
   }

   public void testMiddleLeft() {
      assertNodeState("---" + "x--" + "---", MIDDLE_LEFT);
   }

   public void testCentre() {
      assertNodeState("---" + "-x-" + "---", CENTRE);
   }

   public void testMiddleRight() {
      assertNodeState("---" + "--x" + "---", MIDDLE_RIGHT);
   }

   public void testBottomLeft() {
      assertNodeState("---" + "---" + "x--", BOTTOM_LEFT);
   }

   public void testBottomCentre() {
      assertNodeState("---" + "---" + "-x-", BOTTOM_CENTRE);
   }

   public void testBottomRight() {
      assertNodeState("---" + "---" + "--x", BOTTOM_RIGHT);
   }

   public void testMix1() {
      EnumSet<Square> noughts = of(TOP_RIGHT, MIDDLE_LEFT, MIDDLE_RIGHT, BOTTOM_LEFT, BOTTOM_CENTRE);
      assertNodeState("oox" + "xox" + "xxo", noughts, complementOf(noughts));
   }

   public void testMix2() {
      EnumSet<Square> noughts = of(TOP_LEFT, TOP_RIGHT, CENTRE, BOTTOM_LEFT, BOTTOM_RIGHT);
      assertNodeState("xox" + "oxo" + "xox", noughts, complementOf(noughts));
   }

   public void testMix3() {
      assertNodeState("--o" + "x-x" + "o--", of(MIDDLE_LEFT, MIDDLE_RIGHT), of(TOP_RIGHT, BOTTOM_LEFT));
   }

   public void testInputTooBig() {
      String board = "---" + "---" + "---" + "-";
      try {
         createNoughtsAndCrossesNode(board);
         fail();
      } catch (IllegalArgumentException e) {
         assertEquals(board, e.getMessage());
      }
   }

   public void testInputTooShort() {
      String board = "---" + "---" + "--";
      try {
         createNoughtsAndCrossesNode(board);
         fail();
      } catch (IllegalArgumentException e) {
         assertEquals(board, e.getMessage());
      }
   }

   public void testInvalidChar() {
      // only 'x', 'o' and '-' are valid
      char[] invalid = {' ', 'X', 'O', '0', 'a', 'Z'};
      for (char c : invalid) {
         try {
            createNoughtsAndCrossesNode("---" + "-" + c + "-" + "---");
            fail();
         } catch (IllegalArgumentException e) {
            assertEquals("expected: 'x', 'o' or '-' not: " + c, e.getMessage());
         }
      }
   }

   private void assertNodeState(String board, Square s) {
      assertNodeState(board, of(s), noneOf(Square.class));
   }

   private void assertNodeState(String board, EnumSet<Square> noughts, EnumSet<Square> crosses) {
      NoughtsAndCrossesNode n = createNoughtsAndCrossesNode(board);
      assertNodeState(n, noughts, crosses);

   }

   private void assertNodeState(NoughtsAndCrossesNode n, EnumSet<Square> noughts, EnumSet<Square> crosses) {
      Map<MiniMaxPlayer, EnumSet<Square>> m = n.getCopyOfState();
      assertEquals(noughts, m.get(PLAYER_1));
      assertEquals(crosses, m.get(PLAYER_2));
   }
}
