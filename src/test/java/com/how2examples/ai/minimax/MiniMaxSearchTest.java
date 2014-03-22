package com.how2examples.ai.minimax;

import static com.how2examples.ai.minimax.MiniMaxPlayer.PLAYER_1;
import static com.how2examples.ai.minimax.MiniMaxPlayer.PLAYER_2;
import static com.how2examples.ai.minimax.MiniMaxSearch.minimax;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

public class MiniMaxSearchTest {
   private final Mockery context = new Mockery();

   private final MiniMaxNode root = context.mock(MiniMaxNode.class, "root");
   private final MiniMaxNode a = context.mock(MiniMaxNode.class, "a");
   private final MiniMaxNode b = context.mock(MiniMaxNode.class, "b");
   private final MiniMaxNode c = context.mock(MiniMaxNode.class, "c");
   private final MiniMaxNode a_a = context.mock(MiniMaxNode.class, "a_a");
   private final MiniMaxNode a_b = context.mock(MiniMaxNode.class, "a_b");
   private final MiniMaxNode b_a = context.mock(MiniMaxNode.class, "b_a");
   private final MiniMaxNode b_b = context.mock(MiniMaxNode.class, "b_b");
   private final MiniMaxNode b_a_a = context.mock(MiniMaxNode.class, "b_a_a");
   private final MiniMaxNode b_a_b = context.mock(MiniMaxNode.class, "b_a_b");

   @Test
   public void testNoPossibleMoves() {
      expectGetChildren(root);
      assertNull(minimax(root, PLAYER_1));
   }

   @Test
   public void testOnePossibleMovePlayer1() {
      expectGetChildren(root, a);
      expectGetChildren(a);
      expectGetGoodness(a, PLAYER_1, 0);
      assertSame(a, MiniMaxSearch.minimax(root, PLAYER_1));
   }

   @Test
   public void testOnePossibleMovePlayer2() {
      expectGetChildren(root, a);
      expectGetChildren(a);
      expectGetGoodness(a, PLAYER_2, 0);
      assertSame(a, MiniMaxSearch.minimax(root, PLAYER_2));
   }

   @Test
   public void testMultiplePossibleMoves() {
      expectGetChildren(root, a, b, c);
      expectGetChildren(a);
      expectGetChildren(b);
      expectGetChildren(c);
      expectGetGoodness(a, PLAYER_1, -9);
      expectGetGoodness(b, PLAYER_1, 5);
      expectGetGoodness(c, PLAYER_1, 4);

      // assert that the best next move (b) is chosen regardless of it's position
      assertSame(b, minimax(root, PLAYER_1));
   }

   @Test
   public void testOneMoveAhead_1() {
      createOneMoveAheadChildrenExpectations();

      expectGetGoodness(a_a, PLAYER_2, 6);
      expectGetGoodness(a_b, PLAYER_2, 9);
      expectGetGoodness(a_b, PLAYER_1, -9);

      expectGetGoodness(b_a, PLAYER_2, 8);
      expectGetGoodness(b_b, PLAYER_2, 7);
      expectGetGoodness(b_a, PLAYER_1, -8);

      assertEquals(b, minimax(root, PLAYER_1));
   }

   @Test
   public void testOneMoveAhead_2() {
      createOneMoveAheadChildrenExpectations();

      expectGetGoodness(a_a, PLAYER_2, -7);
      expectGetGoodness(a_b, PLAYER_2, -8);
      expectGetGoodness(a_a, PLAYER_1, 8);

      expectGetGoodness(b_a, PLAYER_2, -9);
      expectGetGoodness(b_b, PLAYER_2, 100);
      expectGetGoodness(b_b, PLAYER_1, -100);

      assertEquals(a, minimax(root, PLAYER_1));
   }

   @Test
   public void testTwoMovesAhead_1() {
      createTwoMovesAheadChildrenExpectations();

      expectGetGoodness(a_a, PLAYER_2, -50);
      expectGetGoodness(a_b, PLAYER_2, -100);
      expectGetGoodness(a_a, PLAYER_1, 50);

      expectGetGoodness(b_a_a, PLAYER_1, -9);
      expectGetGoodness(b_a_b, PLAYER_1, 5);
      expectGetGoodness(b_a_b, PLAYER_1, 5);
      expectGetGoodness(b_a_b, PLAYER_2, -5);

      expectGetGoodness(c, PLAYER_1, 9);

      assertEquals(a, minimax(root, PLAYER_1));
   }

   @Test
   public void testTwoMovesAhead_2() {
      createTwoMovesAheadChildrenExpectations();

      expectGetGoodness(a_a, PLAYER_2, -7);
      expectGetGoodness(a_b, PLAYER_2, 8);
      expectGetGoodness(a_b, PLAYER_1, -8);

      expectGetGoodness(b_a_a, PLAYER_1, -9);
      expectGetGoodness(b_a_b, PLAYER_1, 500);
      expectGetGoodness(b_a_b, PLAYER_1, 500);
      expectGetGoodness(b_a_b, PLAYER_2, -500);

      expectGetGoodness(c, PLAYER_1, 9);

      assertEquals(b, minimax(root, PLAYER_1));
   }

   @Test
   public void testTwoMovesAhead_3() {
      createTwoMovesAheadChildrenExpectations();

      expectGetGoodness(a_a, PLAYER_2, -7);
      expectGetGoodness(a_b, PLAYER_2, -8);
      expectGetGoodness(a_a, PLAYER_1, 7);

      expectGetGoodness(b_a_a, PLAYER_1, -6);
      expectGetGoodness(b_a_b, PLAYER_1, -6);
      expectGetGoodness(b_a_b, PLAYER_1, -6);
      expectGetGoodness(b_a_b, PLAYER_2, 6);

      expectGetGoodness(c, PLAYER_1, 9);

      assertEquals(c, minimax(root, PLAYER_1));
   }

   @Test
   public void testDepth0() {
      expectGetChildren(root, a);
      expectGetChildren(a, b);
      expectGetGoodness(a, PLAYER_1, 0);

      assertEquals(a, minimax(root, PLAYER_1, 0));
   }

   @Test
   public void testDepth1() {
      expectGetChildren(root, a);
      expectGetChildren(a, b);
      expectGetGoodness(b, PLAYER_1, 0);
      expectGetGoodness(b, PLAYER_2, 0);
      expectGetGoodness(a, PLAYER_1, 0);

      assertEquals(a, minimax(root, PLAYER_1, 1));
   }

   @Test
   public void testDepth2() {
      expectGetChildren(root, a);
      expectGetChildren(a, b);
      expectGetChildren(b, c);
      expectGetGoodness(c, PLAYER_1, 0);
      expectGetGoodness(c, PLAYER_2, 0);
      expectGetGoodness(c, PLAYER_1, 0);

      assertEquals(a, minimax(root, PLAYER_1, 2));
   }

   @Test
   public void testDepthMinusValue() {
      expectGetChildren(root, a);
      expectGetChildren(a, b);
      expectGetChildren(b, c);
      expectGetChildren(c);
      expectGetGoodness(c, PLAYER_1, 0);
      expectGetGoodness(c, PLAYER_2, 0);
      expectGetGoodness(c, PLAYER_1, 0);

      assertEquals(a, minimax(root, PLAYER_1, -1));
   }

   private void createOneMoveAheadChildrenExpectations() {
      expectGetChildren(root, a, b);

      expectGetChildren(a, a_a, a_b);
      expectGetChildren(a_a);
      expectGetChildren(a_b);

      expectGetChildren(b, b_a, b_b);
      expectGetChildren(b_a);
      expectGetChildren(b_b);
   }

   private void createTwoMovesAheadChildrenExpectations() {
      expectGetChildren(root, a, b, c);

      expectGetChildren(a, a_a, a_b);
      expectGetChildren(a_a);
      expectGetChildren(a_b);

      expectGetChildren(b, b_a);
      expectGetChildren(b_a, b_a_a, b_a_b);
      expectGetChildren(b_a_a);
      expectGetChildren(b_a_b);

      expectGetChildren(c);
   }

   private void expectGetChildren(final MiniMaxNode n, final MiniMaxNode... children) {
      context.checking(new Expectations() {
         {
            oneOf(n).getChildren();
            will(returnValue(children));
         }
      });
   }

   private void expectGetGoodness(final MiniMaxNode n, final MiniMaxPlayer p, final int g) {
      context.checking(new Expectations() {
         {
            oneOf(n).getGoodness(p);
            will(returnValue(g));
         }
      });
   }
}
