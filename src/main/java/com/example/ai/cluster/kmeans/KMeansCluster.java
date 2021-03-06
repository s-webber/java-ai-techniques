package com.example.ai.cluster.kmeans;

import java.util.Arrays;

import com.google.common.collect.ImmutableList;
import com.example.ai.util.data.DataSetRow;

/**
 * Represents a point within a data domain and the items that it is associated with.
 */
public class KMeansCluster {
   /** The coordinates of the central point of the cluster. */
   private final double[] centroid;
   /** The items that are associated with (i.e. have been classified as being part of) this cluster. */
   private final ImmutableList<DataSetRow> members;

   KMeansCluster(double[] centroid, ImmutableList<DataSetRow> members) {
      this.centroid = centroid;
      this.members = members;
   }

   public double[] getCentroid() {
      return Arrays.copyOf(centroid, centroid.length);
   }

   public ImmutableList<DataSetRow> getMembers() {
      return members;
   }
}
