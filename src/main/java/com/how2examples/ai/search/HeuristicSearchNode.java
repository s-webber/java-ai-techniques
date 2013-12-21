package com.how2examples.ai.search;

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

   int estimateCostToGoal();

   int nodeCost();
}
