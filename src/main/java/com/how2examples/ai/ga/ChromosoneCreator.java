package com.how2examples.ai.ga;

/**
 * Provides genetic operators for creating {@link Chromosone} instances.
 * <p>
 * See: <a href="http://en.wikipedia.org/wiki/Genetic_algorithm#Genetic_operators">Wikipedia</a>
 */
public interface ChromosoneCreator<T extends Object> {
   /**
    * Returns a newly created {@link Chromosone}.
    */
   Chromosone<T> create();

   /**
    * Returns a newly created {@link Chromosone} by combining the characteristics of the specified candidates.
    * <p>
    * See: <a href="http://en.wikipedia.org/wiki/Crossover_(genetic_algorithm)">Wikipedia</a>
    */
   Chromosone<T> crossover(T input1, T input2);

   /**
    * Returns a newly created {@link Chromosone} by mutating the characteristics of the specified candidate.
    * <p>
    * See: <a href="http://en.wikipedia.org/wiki/Mutation_(genetic_algorithm)">Wikipedia</a>
    */
   Chromosone<T> mutate(T input);
}