package com.how2examples.ai.util.math;

import static com.how2examples.ai.util.math.VectorFunctions.assertArrayLengthsMatch;

public class EuclideanFunction implements DistanceFunction {
   @Override
   public double calculate(final double[] vector1, final double[] vector2) {
      assertArrayLengthsMatch(vector1, vector2);
      double d = 0;
      for (int i = 0; i < vector1.length; i++) {
         d += Math.pow(vector1[i] - vector2[i], 2);
      }
      return Math.sqrt(d);
   }
}
