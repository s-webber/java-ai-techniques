package com.how2examples.ai.cluster.hierarchy;

import java.util.ArrayList;
import java.util.List;

import com.how2examples.ai.util.data.DataSet;
import com.how2examples.ai.util.data.DataSetRow;
import com.how2examples.ai.util.math.DistanceFunction;

/**
 * Builds a hierarchy of clusters from a {@link DataSet}.
 * <p>
 * <img src="doc-files/HierarchicalClusterer.png">
 */
public class HierarchicalClusterer {
   private final DistanceFunction distanceFunction;

   public HierarchicalClusterer(final DistanceFunction distanceFunction) {
      this.distanceFunction = distanceFunction;
   }

   public HierarchicalCluster cluster(final DataSet dataSet) throws Exception {
      final List<HierarchicalCluster> clusters = createLeafClusters(dataSet);

      final ClusterPairDistanceCache distanceCache = new ClusterPairDistanceCache(distanceFunction);
      while (clusters.size() > 1) {
         final ClusterPair closestPair = findClosestPair(clusters, distanceCache);
         combineClosestPair(clusters, closestPair);
      }

      return getRootCluster(clusters);
   }

   private List<HierarchicalCluster> createLeafClusters(final DataSet dataSet) {
      final List<HierarchicalCluster> clusters = new ArrayList<>();
      for (final DataSetRow row : dataSet.getValues()) {
         final HierarchicalCluster cluster = new LeafCluster(row);
         clusters.add(cluster);
      }
      return clusters;
   }

   private ClusterPair findClosestPair(final List<HierarchicalCluster> clusters, final ClusterPairDistanceCache distanceCache) {
      double closestDistance = Double.MAX_VALUE;
      ClusterPair closestPair = null;
      for (int x = 0; x < clusters.size(); x++) {
         final HierarchicalCluster c1 = clusters.get(x);
         for (int y = x + 1; y < clusters.size(); y++) {
            final HierarchicalCluster c2 = clusters.get(y);
            final ClusterPair clusterPair = new ClusterPair(c1, c2);
            final double distance = distanceCache.getOrCreate(clusterPair);
            if (distance < closestDistance) {
               closestDistance = distance;
               closestPair = clusterPair;
            }
         }
      }
      return closestPair;
   }

   private void combineClosestPair(final List<HierarchicalCluster> clusters, ClusterPair closestPair) {
      final HierarchicalCluster c1 = closestPair.getFirstCluster();
      final HierarchicalCluster c2 = closestPair.getSecondCluster();
      final HierarchicalCluster combinedClusters = new ParentCluster(c1, c2);
      clusters.remove(c1);
      clusters.remove(c2);
      clusters.add(combinedClusters);
   }

   private HierarchicalCluster getRootCluster(final List<HierarchicalCluster> clusters) {
      assert clusters != null && clusters.size() == 1;
      return clusters.get(0);
   }
}