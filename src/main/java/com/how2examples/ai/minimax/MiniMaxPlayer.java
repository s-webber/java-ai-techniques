package com.how2examples.ai.minimax;

/** Represents a player in a two-player game. */
public enum MiniMaxPlayer {
   PLAYER_1,
   PLAYER_2;

   public MiniMaxPlayer getOpponent() {
      return this == PLAYER_1 ? PLAYER_2 : PLAYER_1;
   }
}
