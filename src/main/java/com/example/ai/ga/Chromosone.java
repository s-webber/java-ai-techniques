package com.example.ai.ga;

/**
 * Represents a possible solution to a problem.
 * <p>
 * A member of a {@link Generation}.
 */
public class Chromosone<T extends Object> {
   private final T o;
   private final double fitness;

   public Chromosone(T o, double fitness) {
      this.o = o;
      this.fitness = fitness;
   }

   public T getValue() {
      return o;
   }

   /**
    * The fitness (quality) of this solution.
    * 
    * @return the fitness of this solution (the higher the value the better the solution)
    */
   public double getFitness() {
      return fitness;
   }
}