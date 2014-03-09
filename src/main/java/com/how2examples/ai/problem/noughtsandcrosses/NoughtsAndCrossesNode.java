package com.how2examples.ai.problem.noughtsandcrosses;

import static com.how2examples.ai.minimax.MiniMaxPlayer.PLAYER_1;
import static com.how2examples.ai.minimax.MiniMaxPlayer.PLAYER_2;
import static com.how2examples.ai.problem.noughtsandcrosses.Lines.hasLine;
import static java.util.EnumSet.complementOf;
import static java.util.EnumSet.copyOf;
import static java.util.EnumSet.noneOf;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.how2examples.ai.minimax.MiniMaxNode;
import com.how2examples.ai.minimax.MiniMaxPlayer;

/**
 * Represents a game-state of a noughts and crosses game.
 * <p>
 * Noughts and crosses is a game for two players. The players take turns marking the spaces in a 3x3 grid. One player
 * marks squares with a 'X' and the other marks squares with a 'O'. The player who succeeds in placing three respective
 * marks in a line (horizontal, vertical, or diagonal) wins the game.
 * </p>
 * <img src="../../search/doc-files/Minimax.png">
 * 
 * @see http://en.wikipedia.org/wiki/Tic_tac_toe
 */
class NoughtsAndCrossesNode implements MiniMaxNode {
   private static final NoughtsAndCrossesNode[] EMPTY_ARRAY = new NoughtsAndCrossesNode[0];

   private final Map<MiniMaxPlayer, EnumSet<Square>> state;

   NoughtsAndCrossesNode(Map<MiniMaxPlayer, EnumSet<Square>> state) {
      this.state = getCopyOfState(state);
   }

   MiniMaxPlayer getPlayerToMoveNext() {
      return getNumberOfSquares(PLAYER_1) > getNumberOfSquares(PLAYER_2) ? PLAYER_2 : PLAYER_1;
   }

   @Override
   public NoughtsAndCrossesNode[] getChildren() {
      if (isComplete()) {
         return EMPTY_ARRAY;
      }

      List<MiniMaxNode> children = new ArrayList<>();

      NoughtsAndCrossesNode gameSavingMove = null;
      for (Square freeSquare : getFreeSquares()) {
         NoughtsAndCrossesNode child = createChild(freeSquare);
         if (isWinningMove(child)) {
            // if we have a winning move, take it
            return singleton(child);
         } else if (isWinningSquareForOpponent(freeSquare)) {
            // if not taking this square would leave the opponent with a winning move on their next turn, keep a record of it
            gameSavingMove = child;
         }
         children.add(child);
      }

      if (gameSavingMove != null) {
         // if haven't found a winning move, next best option is to pick a move that denies the opponent a winning move
         return singleton(gameSavingMove);
      } else {
         return children.toArray(new NoughtsAndCrossesNode[children.size()]);
      }
   }

   private NoughtsAndCrossesNode[] singleton(NoughtsAndCrossesNode n) {
      return new NoughtsAndCrossesNode[] {n};
   }

   private boolean isWinningMove(NoughtsAndCrossesNode child) {
      return child.hasWon(getPlayerToMoveNext());
   }

   private boolean isWinningSquareForOpponent(Square freeSquare) {
      EnumSet<Square> copyOfOpponentState = copyOf(state.get(getPlayerToMoveNext().getOpponent()));
      copyOfOpponentState.add(freeSquare);
      return hasLine(copyOfOpponentState);
   }

   private NoughtsAndCrossesNode createChild(Square squareToPopulateNext) {
      Map<MiniMaxPlayer, EnumSet<Square>> newState = getCopyOfState();
      newState.get(getPlayerToMoveNext()).add(squareToPopulateNext);

      return new NoughtsAndCrossesNode(newState);
   }

   private EnumSet<Square> getFreeSquares() {
      return complementOf(getPopulatedSquares());
   }

   private EnumSet<Square> getPopulatedSquares() {
      final EnumSet<Square> populatedSquares = noneOf(Square.class);
      for (EnumSet<Square> s : state.values()) {
         populatedSquares.addAll(s);
      }
      return populatedSquares;
   }

   private int getNumberOfSquares(MiniMaxPlayer s) {
      return state.get(s).size();
   }

   @Override
   public int getGoodness(MiniMaxPlayer Player) {
      if (hasWon(Player)) {
         return Integer.MAX_VALUE;
      } else if (hasWon(Player.getOpponent())) {
         return Integer.MIN_VALUE;
      } else {
         return 0;
      }
   }

   private boolean isComplete() {
      return isWon() || isFull();
   }

   /** Are all the squares populated? */
   private boolean isFull() {
      return getFreeSquares().isEmpty();
   }

   /** Has either player won? */
   private boolean isWon() {
      for (MiniMaxPlayer p : MiniMaxPlayer.values()) {
         if (hasWon(p)) {
            return true;
         }
      }
      return false;
   }

   private boolean hasWon(MiniMaxPlayer Player) {
      return hasLine(state.get(Player));
   }

   /** Returns a copy of the state represented by this node */
   Map<MiniMaxPlayer, EnumSet<Square>> getCopyOfState() {
      return getCopyOfState(state);
   }

   private static Map<MiniMaxPlayer, EnumSet<Square>> getCopyOfState(Map<MiniMaxPlayer, EnumSet<Square>> currentState) {
      final Map<MiniMaxPlayer, EnumSet<Square>> newState = new EnumMap<>(MiniMaxPlayer.class);
      for (Entry<MiniMaxPlayer, EnumSet<Square>> e : currentState.entrySet()) {
         newState.put(e.getKey(), copyOf(e.getValue()));
      }
      return newState;
   }

   @Override
   public String toString() {
      return state.toString();
   }
}
