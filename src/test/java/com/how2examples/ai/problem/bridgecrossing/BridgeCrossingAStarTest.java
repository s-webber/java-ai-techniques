package com.how2examples.ai.problem.bridgecrossing;

import static com.how2examples.ai.problem.bridgecrossing.Move.A;
import static com.how2examples.ai.problem.bridgecrossing.Move.A_B;
import static com.how2examples.ai.problem.bridgecrossing.Move.B;
import static com.how2examples.ai.problem.bridgecrossing.Move.C_D;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import com.how2examples.ai.search.AStarSearchStrategy;
import com.how2examples.ai.search.TreeSearch;

/**
 * Uses {@link AStarSearchStrategy} to solve the bridge crossing problem represented by {@link BridgeCrossingNode}.
 */
public class BridgeCrossingAStarTest extends TestCase {
   private static final int TARGET_TIME = 17;

   public void test() {
      final BridgeCrossingNode goalState = findGoalState();
      assertNotNull(goalState);
      assertTrue(goalState.isGoal());

      final List<Move> moves = getSequence(goalState);

      // assert first move is null (as is the start state) and remove it (to make further checks clearer)
      assertNull(moves.remove(0));

      assertMoves(moves, A_B, A, C_D, B, A_B);

      final int totalTimeTaken = calculateTotalTimeTaken(moves);
      assertEquals(TARGET_TIME, totalTimeTaken);
   }

   private BridgeCrossingNode findGoalState() {
      final BridgeCrossingNode startState = new BridgeCrossingNode();
      final AStarSearchStrategy<BridgeCrossingNode> strategy = new AStarSearchStrategy<BridgeCrossingNode>();
      return TreeSearch.search(startState, strategy);
   }

   private void assertMoves(List<Move> actual, Move... expected) {
      assertEquals(expected.length, actual.size());
      for (int i = 0; i < expected.length; i++) {
         assertSame(expected[i], actual.get(i));
      }
   }

   private List<Move> getSequence(BridgeCrossingNode p) {
      List<Move> moves = new LinkedList<Move>();
      do {
         moves.add(0, p.getMove());
      } while ((p = p.getParent()) != null);
      return moves;
   }

   private int calculateTotalTimeTaken(List<Move> moves) {
      int totalTimeTaken = 0;
      for (Move m : moves) {
         totalTimeTaken += m.getTimeTaken();
      }
      return totalTimeTaken;
   }
}
