package com.how2examples.ai.problem.noughtsandcrosses;

import static com.how2examples.ai.problem.noughtsandcrosses.NoughtsAndCrossesNodeFactory.createNoughtsAndCrossesNode;
import static com.how2examples.ai.search.MiniMaxPlayer.PLAYER_1;

import java.util.EnumSet;

import junit.framework.TestCase;

public class LinesTest extends TestCase {
   public void testLine1() {
      assertHasLine("xxx" + "---" + "---");
   }

   public void testLine2() {
      assertHasLine("---" + "xxx" + "---");
   }

   public void testLine3() {
      assertHasLine("---" + "---" + "xxx");
   }

   public void testLine4() {
      assertHasLine("x--" + "x--" + "x--");
   }

   public void testLine5() {
      assertHasLine("-x-" + "-x-" + "-x-");
   }

   public void testLine6() {
      assertHasLine("--x" + "--x" + "--x");
   }

   public void testLine7() {
      assertHasLine("x--" + "-x-" + "--x");
   }

   public void testLine8() {
      assertHasLine("--x" + "-x-" + "x--");
   }

   public void testNoLine1() {
      assertNoLine("xx-" + "xx-" + "---");
   }

   public void testNoLine2() {
      assertNoLine("xx-" + "-xx" + "x--");
   }

   public void testNoLine3() {
      assertNoLine("x-x" + "---" + "x-x");
   }

   public void testNoLine4() {
      assertNoLine("-x-" + "x-x" + "-x-");
   }

   private void assertHasLine(String board) {
      assertLine(board, true);
   }

   private void assertNoLine(String board) {
      assertLine(board, false);
   }

   private void assertLine(String board, boolean expectation) {
      NoughtsAndCrossesNode n = createNoughtsAndCrossesNode(board);
      EnumSet<Square> squaresPopulatedBySymbol = n.createCopyOfState().get(PLAYER_1);
      assertEquals(expectation, Lines.hasLine(squaresPopulatedBySymbol));
   }
}
