package com.how2examples.ai.search;

/**
 * A node (state) in a tree structure that is applicable to blind-search strategies.
 * <p>
 * <img src="doc-files/TreeSearch.png">
 */
public interface BlindSearchNode {
   String getId();

   BlindSearchNode getParent();

   BlindSearchNode[] getChildren();

   boolean isGoal();
}