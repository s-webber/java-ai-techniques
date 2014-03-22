package com.how2examples.ai.util;

import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * Generates random values.
 * <p>
 * This interface provides a way for stochastic optimisation algorithms to access random values. Implementations may use
 * a pseudo-random number generator (e.g. {@code java.util.Random}) or a hardware random number generator.
 * </p>
 */
public interface Randomiser {
   /**
    * Returns a number between the specified range (inclusive).
    * 
    * @param minInclusive the minimum possible value of the number returned
    * @param maxInclusive the maximum possible value of the number returned
    * @return a number between the specified range (inclusive).
    */
   double getDouble(double minInclusive, double maxInclusive);

   /**
    * Returns a value between {@code 0.0d} (inclusive) to {@code 1.0d} (exclusive).
    */
   double nextDouble();

   /**
    * Returns a new randomly permutated version of the specified input.
    */
   <T> List<T> shuffle(ImmutableList<T> input);
}
