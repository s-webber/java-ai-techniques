package com.how2examples.ai.search;

/**
 * A node (state) in a tree structure that is applicable to blind-search strategies.
 * <p>
 * <img src="doc-files/TreeSearch.png">
 */
public interface BlindSearchNode {
   /**
    * Returns the node that directly led to the state represented by this node.
    * <p>
    * i.e. the node that returned this node from a call to {@link #getChildren()}.
    */
   BlindSearchNode getParent();

   /** Returns possible states that directly follow from this node. */
   BlindSearchNode[] getChildren();

   /** Returns true if this node represents the goal/target state. */
   boolean isGoal();
}