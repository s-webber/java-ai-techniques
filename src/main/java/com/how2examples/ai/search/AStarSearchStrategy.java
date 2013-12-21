package com.how2examples.ai.search;

import java.util.ArrayList;

/**
 * Performs A* (a-star) traversal of a tree structure.
 * <p>
 * A heuristic-search strategy that selects the path with the lowest expected <i>total</i> cost or distance.
 * </p>
 * <img src="doc-files/TreeSearch.png">
 */
public class AStarSearchStrategy<T extends HeuristicSearchNode> implements TreeSearchStrategy<T> {
   private final ArrayList<T> l = new ArrayList<T>();

   @Override
   public T removeNext() {
      return l.remove(0);
   }

   @Override
   public void add(T newNode) {
      final int newNodeEstimatedCostOfTotalSolution = getCostSoFar(newNode) + newNode.estimateCostToGoal();
      for (int i = 0; i < l.size(); i++) {
         final T existingNode = l.get(i);
         final int existingNodeEstimatedCostOfTotalSolution = getCostSoFar(existingNode) + existingNode.estimateCostToGoal();
         if (newNodeEstimatedCostOfTotalSolution < existingNodeEstimatedCostOfTotalSolution) {
            l.add(i, newNode);
            return;
         }
      }
      l.add(newNode);
   }

   public static int getCostSoFar(HeuristicSearchNode n) {
      HeuristicSearchNode p = n.getParent();
      if (p == null) {
         return n.nodeCost();
      } else {
         return n.nodeCost() + getCostSoFar(p);
      }
   }

   @Override
   public boolean contains(T n) {
      return l.contains(n);
   }

   @Override
   public boolean isEmpty() {
      return l.isEmpty();
   }
}
