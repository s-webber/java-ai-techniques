package com.how2examples.ai.search;

/** A node (state) in a minimax search tree. */
public interface MiniMaxNode {
   MiniMaxNode[] getChildren();

   /**
    * Returns the "goodness" of the game-state represented by the node, from the point of view of the specified player.
    * <p>
    * i.e.: if this node represents a winning game-state for {@link MiniMaxPlayer#PLAYER_1} this method should return a
    * high value when called with {@link MiniMaxPlayer#PLAYER_1} as the method argument, but a low value when called
    * with {@link MiniMaxPlayer#PLAYER_2} as the method argument.
    */
   int getGoodness(MiniMaxPlayer player);
}
