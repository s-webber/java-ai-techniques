package com.how2examples.ai.problem.noughtsandcrosses;

import static com.how2examples.ai.minimax.MiniMaxPlayer.PLAYER_1;
import static com.how2examples.ai.minimax.MiniMaxPlayer.PLAYER_2;
import static java.util.EnumSet.noneOf;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

import com.how2examples.ai.minimax.MiniMaxPlayer;

/** Provides methods for creating {@link NoughtsAndCrossesNode} instances. */
class NoughtsAndCrossesNodeFactory {
   private static final int NUMBER_OF_SQUARES = Square.values().length;

   /**
    * Returns a new {@link NoughtsAndCrossesNode} representing the start state of a new game (i.e. an empty grid).
    */
   static NoughtsAndCrossesNode createNoughtsAndCrossesNode() {
      return new NoughtsAndCrossesNode(createEmptyState());
   }

   /**
    * Returns a new {@link NoughtsAndCrossesNode} representing the state described by the specified argument.
    * 
    * @param board description of the game-state the new {@link NoughtsAndCrossesNode} should represent. Must be 9
    * characters in length with each character representing the contents of one of the squares that make up the grid.
    * Each character must be one of: a 'x' (to represent a cross), a 'o' (to represent a nought) or a '-' (to represent
    * an unoccupied (i.e. blank/free) square).
    */
   static NoughtsAndCrossesNode createNoughtsAndCrossesNode(String board) {
      if (board.length() != NUMBER_OF_SQUARES) {
         throw new IllegalArgumentException(board);
      }

      Map<MiniMaxPlayer, EnumSet<Square>> state = createEmptyState();
      for (int i = 0; i < NUMBER_OF_SQUARES; i++) {
         char c = board.charAt(i);
         MiniMaxPlayer p = getMiniMaxPlayer(c);
         if (p != null) {
            state.get(p).add(Square.values()[i]);
         }
      }

      return new NoughtsAndCrossesNode(state);
   }

   private static MiniMaxPlayer getMiniMaxPlayer(char c) {
      if (c == 'x') {
         return PLAYER_1;
      } else if (c == 'o') {
         return PLAYER_2;
      } else if (c == '-') {
         return null;
      } else {
         throw new IllegalArgumentException("expected: 'x', 'o' or '-' not: " + c);
      }
   }

   private static Map<MiniMaxPlayer, EnumSet<Square>> createEmptyState() {
      final Map<MiniMaxPlayer, EnumSet<Square>> state = new EnumMap<>(MiniMaxPlayer.class);
      for (MiniMaxPlayer symbol : MiniMaxPlayer.values()) {
         state.put(symbol, noneOf(Square.class));
      }
      return state;
   }
}
