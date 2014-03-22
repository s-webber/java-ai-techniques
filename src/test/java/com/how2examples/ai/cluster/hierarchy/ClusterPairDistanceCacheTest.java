package com.how2examples.ai.cluster.hierarchy;

import static com.how2examples.ai.TestUtils.assertExactlyEquals;

import org.junit.Test;

import com.how2examples.ai.util.math.DistanceFunction;

public class ClusterPairDistanceCacheTest {
   private final double[] distances = {7896.25, -0.25, 9};

   @Test
   public void test() {
      final ClusterPairDistanceCache cache = new ClusterPairDistanceCache(new DummyDistanceFunction());

      final HierarchicalCluster c1 = new DummyHierarchicalCluster();
      final HierarchicalCluster c2 = new DummyHierarchicalCluster();
      final HierarchicalCluster c3 = new DummyHierarchicalCluster();

      assertExactlyEquals(distances[0], cache.getOrCreate(new ClusterPair(c1, c2)));
      assertExactlyEquals(distances[0], cache.getOrCreate(new ClusterPair(c2, c1)));
      assertExactlyEquals(distances[1], cache.getOrCreate(new ClusterPair(c1, c3)));
      assertExactlyEquals(distances[2], cache.getOrCreate(new ClusterPair(c2, c3)));
      assertExactlyEquals(distances[0], cache.getOrCreate(new ClusterPair(c1, c2)));
   }

   private class DummyDistanceFunction implements DistanceFunction {
      int ctr = 0;

      @Override
      public double calculate(final double[] vector1, final double[] vector2) {
         return distances[ctr++];
      }
   }
}
