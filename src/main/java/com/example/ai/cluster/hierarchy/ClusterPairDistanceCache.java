package com.example.ai.cluster.hierarchy;

import java.util.HashMap;
import java.util.Map;

import com.example.ai.util.math.DistanceFunction;

/**
 * Returns the distance between the two clusters of a {@link ClusterPair}.
 * <p>
 * Caches the results so they do not have to be recalculated when the same clusters are generated during different
 * iterations of the clustering process.
 * <p>
 * <img src="doc-files/HierarchicalClusterer.png">
 */
class ClusterPairDistanceCache {
   private final DistanceFunction distanceFunction;
   private final Map<ClusterPair, Double> distances = new HashMap<>();

   public ClusterPairDistanceCache(final DistanceFunction distanceFunction) {
      this.distanceFunction = distanceFunction;
   }

   /**
    * Returns the distance between the two clusters of the {@link ClusterPair}.
    * <p>
    * If the distance has been determined on a previous iteration, a cached result will be returned - else the distance
    * will be calculated using the function provided in the constructor, and cached, before being returned.
    */
   double getOrCreate(final ClusterPair clusterPair) {
      Double distance = distances.get(clusterPair);
      if (distance == null) {
         distance = distanceFunction.calculate(clusterPair.getFirstCluster().getVector(), clusterPair.getSecondCluster().getVector());
         distances.put(clusterPair, distance);
      }
      return distance;
   }
}