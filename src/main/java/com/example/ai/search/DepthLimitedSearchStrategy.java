package com.example.ai.search;

/**
 * Performs depth-limited search of a tree structure.
 * <p>
 * Based on depth-first search, but modified to impose a maximum limit on the depth of a search.
 * </p>
 * <img src="doc-files/TreeSearch.png">
 */
public class DepthLimitedSearchStrategy<T extends BlindSearchNode> extends DepthFirstSearchStrategy<T> {
   private final int limit;

   /** @param limit the maximum limit on the depth of a search */
   public DepthLimitedSearchStrategy(final int limit) {
      this.limit = limit;
   }

   @Override
   public void add(final T n) {
      if (getDepth(n) <= limit) {
         super.add(n);
      }
   }

   /** Returns the depth of the specified node. */
   private int getDepth(final T n) {
      @SuppressWarnings("unchecked")
      final T p = (T) n.getParent();
      if (p == null) {
         return 0;
      } else {
         return 1 + getDepth(p);
      }
   }
}
