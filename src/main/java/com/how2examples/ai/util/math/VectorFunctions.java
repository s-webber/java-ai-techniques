package com.how2examples.ai.util.math;

public class VectorFunctions {
   public static double[] averageVectors(final double[][] vectors) {
      if (vectors.length == 0) {
         throw new IllegalArgumentException("array length is 0");
      }
      final double[] first = vectors[0];
      final double[] result = copy(first);
      for (int i = 1; i < vectors.length; i++) {
         final double[] next = vectors[i];
         assertArrayLengthsMatch(result, next);
         for (int y = 0; y < result.length; y++) {
            result[y] += next[y];
         }
      }
      for (int y = 0; y < result.length; y++) {
         result[y] /= vectors.length;
      }
      return result;
   }

   private static double[] copy(final double[] o) {
      final double[] copy = new double[o.length];
      for (int i = 0; i < o.length; i++) {
         copy[i] = o[i];
      }
      return copy;
   }

   static void assertArrayLengthsMatch(final double[] v1, final double[] v2) {
      if (v1.length != v2.length) {
         throw new IllegalArgumentException("Array lengths differ: " + v1.length + " != " + v2.length);
      }
   }
}
