package com.how2examples.ai.search;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

/**
 * Determines the best next move a specified state of a two-player zero-sum game.
 */
public class MiniMaxSearch {
   /**
    * Convenient alternative to: {@link MiniMaxSearch#minimax(currentGameState, Integer.MAX_VALUE)}
    */
   public static MiniMaxNode minimax(final MiniMaxNode currentGameState) {
      return minimax(currentGameState, MAX_VALUE);
   }

   /**
    * Returns the best possible next move from the specified game state.
    * 
    * @param currentGameState the current state of the game
    * @param depth the maximum depth of the game tree to search
    * @return the best possible next move
    */
   public static MiniMaxNode minimax(final MiniMaxNode currentGameState, final int depth) {
      int bestOutcomeValue = MIN_VALUE;
      MiniMaxNode bestMove = null;

      for (final MiniMaxNode possibleMove : currentGameState.getChildren()) {
         final int moveOutcomeValue = search(possibleMove, depth, Mode.MIN);
         if (moveOutcomeValue >= bestOutcomeValue) {
            bestOutcomeValue = moveOutcomeValue;
            bestMove = possibleMove;
         }
      }

      return bestMove;
   }

   private static int search(final MiniMaxNode n, final int remainingDepth, final Mode mode) {
      final MiniMaxNode[] children = n.getChildren();
      if (children.length == 0) {
         // if no children then return own "goodness" rating
         return n.getGoodness();
      } else {
         // return best outcome of children
         final boolean reachedLimit = remainingDepth == 0;
         int bestOutcome = mode.worstPossibleOutcome;
         for (final MiniMaxNode child : children) {
            final int childGoodness;
            if (reachedLimit) {
               childGoodness = child.getGoodness();
            } else {
               childGoodness = search(child, remainingDepth - 1, mode.alternativeMode());
            }
            bestOutcome = mode.getBestOutcome(childGoodness, bestOutcome);
         }
         return bestOutcome;
      }
   }

   private static enum Mode {
      MIN(MAX_VALUE),
      MAX(MIN_VALUE);

      final int worstPossibleOutcome;

      Mode(final int value) {
         this.worstPossibleOutcome = value;
      }

      Mode alternativeMode() {
         return this == MIN ? MAX : MIN;
      }

      int getBestOutcome(final int o1, final int o2) {
         if (this == MAX) {
            return Math.max(o1, o2);
         } else {
            return Math.min(o1, o2);
         }
      }
   }
}
