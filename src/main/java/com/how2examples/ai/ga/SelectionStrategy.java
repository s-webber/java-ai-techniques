package com.how2examples.ai.ga;

import java.util.Queue;

/**
 * Selects candidates, from an existing generation, to breed a new generation.
 * <p>
 * See: <a href="http://en.wikipedia.org/wiki/Selection_(genetic_algorithm)">Wikipedia</a>
 */
public interface SelectionStrategy<T extends Object> {
   /**
    * Selects the specified number of candidates from the specified {@link Generation}.
    * 
    * @param generation the existing generation from which to select candidates from
    * @param numberToSelect the number of candidates to select
    * @return the selected candidates
    */
   Queue<T> selectCandidates(Generation<T> generation, int numberToSelect);
}
