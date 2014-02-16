package com.how2examples.ai.search;

/** A node (state) in a minimax search tree. */
public interface MiniMaxNode {
   MiniMaxNode[] getChildren();

   public int getGoodness();
}
