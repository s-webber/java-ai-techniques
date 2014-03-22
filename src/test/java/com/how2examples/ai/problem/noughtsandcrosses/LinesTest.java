package com.how2examples.ai.problem.noughtsandcrosses;

import static com.how2examples.ai.minimax.MiniMaxPlayer.PLAYER_1;
import static com.how2examples.ai.problem.noughtsandcrosses.NoughtsAndCrossesNodeFactory.createNoughtsAndCrossesNode;
import static org.junit.Assert.assertEquals;

import java.util.EnumSet;

import org.junit.Test;

public class LinesTest {
   @Test
   public void testLine1() {
      assertHasLine("xxx" + "---" + "---");
   }

   @Test
   public void testLine2() {
      assertHasLine("---" + "xxx" + "---");
   }

   @Test
   public void testLine3() {
      assertHasLine("---" + "---" + "xxx");
   }

   @Test
   public void testLine4() {
      assertHasLine("x--" + "x--" + "x--");
   }

   @Test
   public void testLine5() {
      assertHasLine("-x-" + "-x-" + "-x-");
   }

   @Test
   public void testLine6() {
      assertHasLine("--x" + "--x" + "--x");
   }

   @Test
   public void testLine7() {
      assertHasLine("x--" + "-x-" + "--x");
   }

   @Test
   public void testLine8() {
      assertHasLine("--x" + "-x-" + "x--");
   }

   @Test
   public void testNoLine1() {
      assertNoLine("xx-" + "xx-" + "---");
   }

   @Test
   public void testNoLine2() {
      assertNoLine("xx-" + "-xx" + "x--");
   }

   @Test
   public void testNoLine3() {
      assertNoLine("x-x" + "---" + "x-x");
   }

   @Test
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
      EnumSet<Square> squaresPopulatedBySymbol = n.getCopyOfState().get(PLAYER_1);
      assertEquals(expectation, Lines.hasLine(squaresPopulatedBySymbol));
   }
}
