package com.how2examples.ai.search;

/**
 * Represents a strategy for traversal of a tree structure.
 * <p>
 * <img src="doc-files/TreeSearch.png">
 * 
 * @param <T> the type of objects that may be traversed by this strategy
 */
public interface TreeSearchStrategy<T extends BlindSearchNode> {
   T removeNext();

   void add(T n);

   boolean isEmpty();
}
