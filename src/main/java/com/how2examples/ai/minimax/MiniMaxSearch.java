package com.how2examples.ai.minimax;

/**
 * Determines the best next move for a specified state of a two-player zero-sum game.
 * <p>
 * The minimax strategy attempts to minimise the negative cost for a worst case scenario. The basis for the minimax
 * strategy is the assumption that the opposition will always choose the move that gives them the best chance of a
 * positive outcome (i.e. maximises their chances of winning).
 * <p>
 * <img src="doc-files/MiniMax.png">
 */
public class MiniMaxSearch {
   private static int WORST_POSSIBLE_OUTCOME = Integer.MIN_VALUE;

   /**
    * Convenient alternative to: {@link MiniMaxSearch#minimax(MiniMaxNode, MiniMaxPlayer, int)}
    */
   public static MiniMaxNode minimax(final MiniMaxNode currentGameState, final MiniMaxPlayer playerToMoveNext) {
      return minimax(currentGameState, playerToMoveNext, Integer.MAX_VALUE);
   }

   /**
    * Returns the best possible next move from the specified game state.
    * 
    * @param currentGameState the current state of the game
    * @param depth the maximum depth of the game tree to search. A negative value indicates that there should be no
    * depth limit.
    * @return the best possible next move
    */
   public static MiniMaxNode minimax(final MiniMaxNode currentGameState, final MiniMaxPlayer playerToMoveNext, final int depth) {
      int bestOutcomeValue = WORST_POSSIBLE_OUTCOME;
      MiniMaxNode bestMove = null;

      for (final MiniMaxNode possibleMove : currentGameState.getChildren()) {
         final MiniMaxNode moveOutcome = getExpectedOutcome(possibleMove, playerToMoveNext.getOpponent(), depth);
         final int moveOutcomeValue = moveOutcome.getGoodness(playerToMoveNext);
         if (moveOutcomeValue >= bestOutcomeValue) {
            bestOutcomeValue = moveOutcomeValue;
            bestMove = possibleMove;
         }
      }

      return bestMove;
   }

   /**
    * Returns the expected final outcome that would result from the specified game state ({@code n}).
    */
   private static MiniMaxNode getExpectedOutcome(final MiniMaxNode n, final MiniMaxPlayer playerToMoveNext, final int remainingDepth) {
      if (remainingDepth == 0) {
         // if reached depth limit than stop searching
         return n;
      }

      final MiniMaxNode[] children = n.getChildren();
      if (children.length == 0) {
         return n;
      } else {
         // return best child move (from the point of view of the player who gets to choose it)
         int bestOutcomeValue = WORST_POSSIBLE_OUTCOME;
         MiniMaxNode bestMove = null;

         for (final MiniMaxNode child : children) {
            final MiniMaxNode moveOutcome = getExpectedOutcome(child, playerToMoveNext.getOpponent(), remainingDepth - 1);
            final int moveOutcomeValue = moveOutcome.getGoodness(playerToMoveNext);
            if (moveOutcomeValue >= bestOutcomeValue) {
               bestOutcomeValue = moveOutcomeValue;
               bestMove = moveOutcome;
            }
         }

         return bestMove;
      }
   }
}
