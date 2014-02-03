package com.how2examples.ai.search;

import java.util.ArrayList;

/**
 * Performs depth-first traversal of a tree structure.
 * <p>
 * A blind-search strategy that continues as far as possible along a particular path (branch) before backtracking.
 * </p>
 * <img src="doc-files/TreeSearch.png">
 */
public class DepthFirstSearchStrategy<T extends BlindSearchNode> implements TreeSearchStrategy<T> {
   private final ArrayList<T> l = new ArrayList<>();
   private int idx = 0;

   @Override
   public T removeNext() {
      idx = l.size() - 1;
      return l.remove(idx);
   }

   @Override
   public void add(T n) {
      l.add(idx, n);
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
