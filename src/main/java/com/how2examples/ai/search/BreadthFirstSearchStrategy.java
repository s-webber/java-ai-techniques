package com.how2examples.ai.search;

import java.util.ArrayList;

/**
 * Performs breadth-first traversal of a tree structure.
 * <p>
 * A blind-search strategy that examines all nodes at the same level before examining nodes at the next level.
 * </p>
 * <img src="doc-files/TreeSearch.png">
 */
public class BreadthFirstSearchStrategy<T extends BlindSearchNode> implements TreeSearchStrategy<T> {
   ArrayList<T> l = new ArrayList<T>();

   @Override
   public T removeNext() {
      return l.remove(0);
   }

   @Override
   public void add(T n) {
      l.add(n);
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
