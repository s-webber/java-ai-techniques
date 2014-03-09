package com.how2examples.ai.ga;

import java.util.LinkedList;
import java.util.Queue;

import com.how2examples.ai.util.Randomiser;

/**
 * Selects candidates, from an existing generation, using fitness proportionate selection.
 * <p>
 * Using fitness proportionate selection all candidates (even weak candidates) have a chance of being selected - but the
 * better a candidate (relative to the rest of the generation) the higher the probability of it being chosen. (Also
 * known as roulette wheel selection.)
 * </p>
 * See: <a href="http://en.wikipedia.org/wiki/Fitness_proportionate_selection">Wikipedia</a>
 */
public class FitnessProportionateSelectionStrategy<T extends Object> implements SelectionStrategy<T> {
   private final Randomiser randomiser;

   public FitnessProportionateSelectionStrategy(Randomiser randomiser) {
      this.randomiser = randomiser;
   }

   @Override
   public Queue<T> selectCandidates(Generation<T> generation, int number) {
      final Queue<T> result = new LinkedList<T>();
      for (int i = 0; i < number; i++) {
         result.add(selectCandidate(generation, randomiser.nextDouble()));
      }
      return result;
   }

   /**
    * @param probability a positive sign, greater than or equal to {@code 0.0} and less than {@code 1.0}.
    */
   private T selectCandidate(Generation<T> generation, double probability) {
      if (probability < 0 || probability >= 1) {
         throw new IllegalArgumentException("expected value greater than or equal to 0.0 and less than 1.0 - but got: " + probability);
      }
      return getChromosone(generation, probability).getValue();
   }

   private Chromosone<T> getChromosone(Generation<T> generation, double input) {
      final double sumFitness = generation.getSumFitness();
      double cumulativeProbability = 0;
      for (Chromosone<T> chromosone : generation) {
         final double chromosoneProbability = chromosone.getFitness() / sumFitness;
         cumulativeProbability += chromosoneProbability;
         if (input < cumulativeProbability) {
            return chromosone;
         }
      }
      // if get here it will be due to a rounding issue -
      // return entry with highest probability (i.e. the first entry)
      return generation.getFirst();
   }
}
