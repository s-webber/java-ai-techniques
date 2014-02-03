package com.how2examples.ai.util.math;

/**
 * A function that calculates the distance between elements of a set.
 * <p>
 * e.g. possible implementations could use dot product, pearson correlation or radial basis functions
 * </p>
 */
public interface DistanceFunction {
   double calculate(double[] vector1, double[] vector2);
}
