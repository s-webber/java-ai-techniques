package com.how2examples.ai.search;

import java.util.HashSet;

/**
 * Searches a tree data structure using the specified {@link TreeSearchStrategy}.
 * <p>
 * <img src="doc-files/TreeSearch.png">
 */
public class TreeSearch {
   public static <T extends BlindSearchNode> T search(final T root, final TreeSearchStrategy<T> strategy) {
      final HashSet<T> alreadyVisited = new HashSet<T>(10000);
      T current;

      strategy.add(root);
      while (!strategy.isEmpty()) {
         current = strategy.removeNext();

         if (current.isGoal()) {
            // return goal state
            return current;
         }

         @SuppressWarnings("unchecked")
         final T[] children = (T[]) current.getChildren();
         for (final T child : children) {
            if (!strategy.contains(child) && !alreadyVisited.contains(child)) {
               strategy.add(child);
            }
         }

         alreadyVisited.add(current);
      }
      // cannot find goal
      return null;
   }
}
