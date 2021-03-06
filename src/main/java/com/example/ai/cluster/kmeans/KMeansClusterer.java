package com.example.ai.cluster.kmeans;

import static com.example.ai.util.ImmutableListFactory.createList;
import static com.example.ai.util.ImmutableListFactory.emptyList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.example.ai.util.Randomiser;
import com.example.ai.util.data.DataSet;
import com.example.ai.util.data.DataSetRow;
import com.example.ai.util.math.DistanceFunction;
import com.example.ai.util.math.VectorFunctions;

/**
 * Builds a collection of {@link KMeansCluster} objects based on a {@link DataSet}.
 * <p>
 * The data-set will be partitioned into a predefined numbered of discrete clusters. Each row of the data-set will be
 * associated with one, and only one, cluster.
 */
public class KMeansClusterer {
   /**
    * The maximum number of iterations to process to alter the cluster centroids.
    * <p>
    * The algorithm continues to adjust the centroids of the clusters until either a) the clustering has stabilised
    * (i.e. further iterations would not cause any clusters to be adjusted) or b) the number of attempted iterations
    * equals {@code MAX_ITERATIONS}.
    * </p>
    * TODO make configurable and test
    */
   private static final int MAX_ITERATIONS = 100;

   private final DistanceFunction distanceFunction;
   private final Randomiser randomiser;

   public KMeansClusterer(final DistanceFunction distanceFunction, final Randomiser randomiser) {
      this.distanceFunction = distanceFunction;
      this.randomiser = randomiser;
   }

   public ImmutableList<KMeansCluster> createClusters(final DataSet dataSet, final int numberOfClusters) {
      // get min and max ranges of all input data
      final double[][] minmax = getMinAndMaxRanges(dataSet);
      final double[] min = minmax[0];
      final double[] max = minmax[1];

      // create requested number of random clusters
      final KMeansCluster[] clusters = new KMeansCluster[numberOfClusters];
      for (int i = 0; i < numberOfClusters; i++) {
         clusters[i] = createCluster(min, max);
      }

      return createList(clusters);
   }

   private double[][] getMinAndMaxRanges(final DataSet dataSet) {
      final ImmutableList<DataSetRow> values = dataSet.getValues();
      final int numElementsPerRow = values.get(0).getInputsAsDoubleArray().length;
      final double[] min = new double[numElementsPerRow];
      final double[] max = new double[numElementsPerRow];
      for (int i = 0; i < numElementsPerRow; i++) {
         min[i] = Double.MAX_VALUE;
         max[i] = Double.MIN_VALUE;
      }
      for (final DataSetRow row : values) {
         final double[] rowValues = row.getInputsAsDoubleArray();
         for (int i = 0; i < numElementsPerRow; i++) {
            final double v = rowValues[i];
            if (v < min[i]) {
               min[i] = v;
            } else if (v > max[i]) {
               max[i] = v;
            }
         }
      }
      return new double[][] {min, max};
   }

   private KMeansCluster createCluster(final double[] min, final double[] max) {
      final double[] clusterValues = new double[min.length];
      for (int i = 0; i < min.length; i++) {
         clusterValues[i] = randomiser.getDouble(min[i], max[i]);
      }
      final ImmutableList<DataSetRow> emptyList = emptyList();
      return new KMeansCluster(clusterValues, emptyList);
   }

   public ImmutableList<KMeansCluster> cluster(final DataSet dataSet, final ImmutableList<KMeansCluster> inputClusters) {
      final KMeansCluster[] outputClusters = toArray(inputClusters);
      performIterations(dataSet, outputClusters);
      return createList(outputClusters);
   }

   private KMeansCluster[] toArray(final ImmutableList<KMeansCluster> inputClusters) {
      return inputClusters.toArray(new KMeansCluster[inputClusters.size()]);
   }

   private void performIterations(final DataSet dataSet, final KMeansCluster[] clusters) {
      for (int i = 0; i < MAX_ITERATIONS; i++) {
         final boolean updated = performIteration(clusters, dataSet);
         if (!updated) {
            return;
         }
      }
   }

   /** @return {@code true} if any cluster's centroid was updated by this iteration, else {@code false} */
   private boolean performIteration(final KMeansCluster[] clusters, final DataSet dataSet) {
      final Map<KMeansCluster, List<DataSetRow>> newAllocationOfVectorsToClusters = allocateVectorsToClusters(clusters, dataSet);
      return updateClusters(clusters, newAllocationOfVectorsToClusters);
   }

   private Map<KMeansCluster, List<DataSetRow>> allocateVectorsToClusters(final KMeansCluster[] clusters, final DataSet dataSet) {
      final Map<KMeansCluster, List<DataSetRow>> vectorsByClusters = createMapOfClusters(clusters);
      for (final DataSetRow row : dataSet.getValues()) {
         final double[] rowValues = row.getInputsAsDoubleArray();
         final KMeansCluster closestCluster = findClosestCluster(rowValues, clusters);
         final List<DataSetRow> vectorsByCluster = vectorsByClusters.get(closestCluster);
         vectorsByCluster.add(row);
      }
      return vectorsByClusters;
   }

   private Map<KMeansCluster, List<DataSetRow>> createMapOfClusters(final KMeansCluster[] clusters) {
      final Map<KMeansCluster, List<DataSetRow>> m = new HashMap<>();
      for (KMeansCluster c : clusters) {
         m.put(c, new ArrayList<DataSetRow>());
      }
      return m;
   }

   private KMeansCluster findClosestCluster(final double[] target, final KMeansCluster[] clusters) {
      double closestDistance = Double.MAX_VALUE;
      KMeansCluster closestCluster = null;
      for (final KMeansCluster cluster : clusters) {
         final double distance = distanceFunction.calculate(target, cluster.getCentroid());
         if (distance < closestDistance) {
            closestDistance = distance;
            closestCluster = cluster;
         }
      }
      return closestCluster;
   }

   private static boolean updateClusters(final KMeansCluster[] clusters, final Map<KMeansCluster, List<DataSetRow>> newAllocationOfVectorsToClusters) {
      boolean isUpdated = false;
      for (int i = 0; i < clusters.length; i++) {
         final KMeansCluster cluster = clusters[i];
         final ImmutableList<DataSetRow> newMembers = createList(newAllocationOfVectorsToClusters.get(cluster));
         if (newMembers.size() > 0) {
            final double[] newCentroid = averageVectors(newMembers);
            if (isUpdated(cluster.getCentroid(), newCentroid)) {
               clusters[i] = new KMeansCluster(newCentroid, newMembers);
               isUpdated = true;
            }
         }
      }
      return isUpdated;
   }

   private static double[] averageVectors(final ImmutableList<DataSetRow> rows) {
      final double[][] input = new double[rows.size()][];
      for (int i = 0; i < rows.size(); i++) {
         final DataSetRow row = rows.get(i);
         input[i] = row.getInputsAsDoubleArray();
      }
      return VectorFunctions.averageVectors(input);
   }

   private static boolean isUpdated(final double[] oldCentroid, final double[] newCentroid) {
      for (int i = 0; i < newCentroid.length; i++) {
         if (oldCentroid[i] != newCentroid[i]) {
            return true;
         }
      }
      return false;
   }
}
