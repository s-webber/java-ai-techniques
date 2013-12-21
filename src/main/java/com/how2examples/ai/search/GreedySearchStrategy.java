package com.how2examples.ai.search;

import java.util.ArrayList;

/**
 * Performs greedy-search traversal of a tree structure.
 * <p>
 * A heuristic-search strategy that selects the locally optimal choice at each stage.
 * </p>
 * <img src="doc-files/TreeSearch.png">
 */
public class GreedySearchStrategy<T extends HeuristicSearchNode> implements TreeSearchStrategy<T> {
   private final ArrayList<T> l = new ArrayList<T>();

   @Override
   public T removeNext() {
      return l.remove(0);
   }

   @Override
   public void add(T newNode) {
      int newNodeCost = newNode.estimateCostToGoal();
      for (int i = 0; i < l.size(); i++) {
         HeuristicSearchNode existingNode = l.get(i);
         if (newNodeCost < existingNode.estimateCostToGoal()) {
            l.add(i, newNode);
            return;
         }
      }
      l.add(newNode);
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
