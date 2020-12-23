package com.example.ai.search;

/**
 * A node (state) in a tree structure that is applicable to heuristic-search strategies.
 * <p>
 * <img src="doc-files/TreeSearch.png">
 */
public interface HeuristicSearchNode extends BlindSearchNode {
   @Override
   HeuristicSearchNode getParent();

   @Override
   HeuristicSearchNode[] getChildren();

   /**
    * Returns the estimated cost from the current state to the goal state.
    * <p>
    * To enable {@link AStarSearchStrategy} to always find the optimal solution, this method should never overestimate
    * the cost to the goal state. Underestimating is OK - but the less accurate the estimation the less efficient the
    * search will be (i.e. more nodes may be searched before the goal state is found).
    */
   int getEstimatedCostToGoal();

   /** Returns the cost of getting to this node from it's parent node. */
   int getNodeCost();
}
