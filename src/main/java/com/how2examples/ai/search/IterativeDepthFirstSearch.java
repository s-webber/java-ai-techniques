package com.how2examples.ai.search;

/**
 * Performs breadth-first traversal of a tree structure.
 * <p>
 * A blind-search strategy that repeatedly applies a depth-limited search strategy with increasing levels of depth.
 * </p>
 * 
 * @see DepthLimitedSearchStrategy
 */
public class IterativeDepthFirstSearch {
   public static <T extends BlindSearchNode> T search(final T root, int maxDepth) {
      for (int i = 1; i < maxDepth; i++) {
         final T result = TreeSearch.search(root, new DepthLimitedSearchStrategy<T>(i));
         if (result != null) {
            return result;
         }
      }
      return null;
   }
}
