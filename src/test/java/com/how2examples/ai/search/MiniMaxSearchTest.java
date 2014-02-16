package com.how2examples.ai.search;

import static com.how2examples.ai.search.MiniMaxSearch.minimax;
import static java.lang.Integer.MAX_VALUE;
import junit.framework.TestCase;

public class MiniMaxSearchTest extends TestCase {
   public void testNoPossibleMoves() {
      assertNull(minimax(new DummyMiniMaxNode("A")));
   }

   public void testOnePossibleMove() {
      DummyMiniMaxNode childNode = new DummyMiniMaxNode("B", 1);
      DummyMiniMaxNode rootNode = new DummyMiniMaxNode("A", childNode);
      assertSame(childNode, MiniMaxSearch.minimax(rootNode));
   }

   public void testMultiplePossibleMove() {
      DummyMiniMaxNode b = new DummyMiniMaxNode("B", -9);
      DummyMiniMaxNode c = new DummyMiniMaxNode("C", 5);
      DummyMiniMaxNode d = new DummyMiniMaxNode("D", 4);

      // assert that the best next move ("C") is chosen regardless of it's position
      assertSame(c, minimax(new DummyMiniMaxNode("A", b, c, d)));
      assertSame(c, minimax(new DummyMiniMaxNode("A", c, b, d)));
      assertSame(c, minimax(new DummyMiniMaxNode("A", b, d, c)));
   }

   public void testOneMoveAhead() {
      assertEquals("C", ((DummyMiniMaxNode) minimax(createMiniMaxTree(), 0)).id);
   }

   public void testTwoMovesAhead() {
      assertEquals("B", ((DummyMiniMaxNode) minimax(createMiniMaxTree(), 1)).id);
   }

   public void testMaxDepth() {
      assertEquals("B", ((DummyMiniMaxNode) minimax(createMiniMaxTree(), MAX_VALUE)).id);
   }

   private DummyMiniMaxNode createMiniMaxTree() {
      //         A
      // +   B         C
      // - F  G       D E
      // +   X Y
      final DummyMiniMaxNode x = new DummyMiniMaxNode("X", -23);
      final DummyMiniMaxNode y = new DummyMiniMaxNode("Y", 44);

      final DummyMiniMaxNode g = new DummyMiniMaxNode("G", 3, x, y); // max xy
      final DummyMiniMaxNode f = new DummyMiniMaxNode("F", 7);

      final DummyMiniMaxNode e = new DummyMiniMaxNode("E", 5);
      final DummyMiniMaxNode d = new DummyMiniMaxNode("D", 4);

      final DummyMiniMaxNode c = new DummyMiniMaxNode("C", d, e); // min ed
      final DummyMiniMaxNode b = new DummyMiniMaxNode("B", f, g); // min fg

      return new DummyMiniMaxNode("A", b, c); // max bc   
   }
}
