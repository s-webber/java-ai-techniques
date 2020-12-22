package com.example.ai.search;

/**
 * Searches a tree data structure using the specified {@link TreeSearchStrategy}.
 * <p>
 * <img src="doc-files/TreeSearch.png">
 */
public class TreeSearch {
   public static <T extends BlindSearchNode> T search(final T root, final TreeSearchStrategy<T> strategy) {
      T current;

      strategy.add(root);
      while (!strategy.isEmpty()) {
         current = strategy.removeNext();

         if (current.isGoal()) {
            return current;
         }

         addChildren(current, strategy);
      }

      // cannot find goal
      return null;
   }

   private static <T extends BlindSearchNode> void addChildren(T parent, TreeSearchStrategy<T> strategy) {
      @SuppressWarnings("unchecked")
      final T[] children = (T[]) parent.getChildren();
      for (final T child : children) {
         strategy.add(child);
      }
   }
}
