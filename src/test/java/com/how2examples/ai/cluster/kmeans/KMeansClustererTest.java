package com.how2examples.ai.cluster.kmeans;

import static com.how2examples.ai.TestUtils.assertArraysEqual;
import static com.how2examples.ai.TestUtils.getDataSet;
import junit.framework.TestCase;

import com.how2examples.ai.util.ImmutableArray;
import com.how2examples.ai.util.Randomiser;
import com.how2examples.ai.util.data.DataSet;
import com.how2examples.ai.util.data.DataSetRow;
import com.how2examples.ai.util.math.EuclideanFunction;

public class KMeansClustererTest extends TestCase {
   public void testOneCluster() throws Exception {
      final int numberOfClusters = 1;
      final ImmutableArray<KMeansCluster> clusters = createClusters(numberOfClusters);
      assertEquals(numberOfClusters, clusters.size());
      final KMeansCluster cluster = clusters.get(0);
      assertMembers(cluster, "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L");
      assertArraysEqual(new double[] {145.75, 132.75}, cluster.getCentroid());
   }

   public void testTwoClusters() throws Exception {
      final int numberOfClusters = 2;
      final ImmutableArray<KMeansCluster> clusters = createClusters(numberOfClusters);
      assertEquals(numberOfClusters, clusters.size());
      assertMembers(clusters.get(0), "A", "C", "F", "G", "L");
      assertMembers(clusters.get(1), "B", "D", "E", "H", "I", "J", "K");
   }

   public void testThreeClusters() throws Exception {
      final int numberOfClusters = 3;
      final ImmutableArray<KMeansCluster> clusters = createClusters(numberOfClusters);
      assertEquals(numberOfClusters, clusters.size());
      assertMembers(clusters.get(0), "A", "C", "F", "L");
      assertMembers(clusters.get(1), "B", "D", "E", "H", "I", "J", "K");
      assertMembers(clusters.get(2), "G");
   }

   public void testMoreClustersThanDataSetRows() throws Exception {
      final int numberOfClusters = 13;
      final ImmutableArray<KMeansCluster> clusters = createClusters(numberOfClusters);
      assertEquals(numberOfClusters, clusters.size());
      assertMembers(clusters.get(0), "A", "F");
      assertMembers(clusters.get(1), "B");
      assertMembers(clusters.get(2), "C");
      assertMembers(clusters.get(3));
      assertMembers(clusters.get(4), "E", "K");
      assertMembers(clusters.get(5));
      assertMembers(clusters.get(6), "G");
      assertMembers(clusters.get(7));
      assertMembers(clusters.get(8), "H", "I", "J");
      assertMembers(clusters.get(9), "D");
      assertMembers(clusters.get(10), "L");
      assertMembers(clusters.get(11));
      assertMembers(clusters.get(12));
   }

   private ImmutableArray<KMeansCluster> createClusters(final int numberOfClusters) throws Exception {
      final Randomiser randomiser = new DummyRandomiser();
      final KMeansClusterer clusterer = new KMeansClusterer(new EuclideanFunction(), randomiser);
      final DataSet dataSet = getDataSet(this);
      final ImmutableArray<KMeansCluster> input = clusterer.createClusters(dataSet, numberOfClusters);
      return clusterer.cluster(dataSet, input);
   }

   private void assertMembers(KMeansCluster c, String... expectedMembers) {
      assertArraysEqual(expectedMembers, getMemberOutcomes(c));
   }

   private String[] getMemberOutcomes(KMeansCluster c) {
      final ImmutableArray<DataSetRow> members = c.getMembers();
      final String[] outcomes = new String[members.size()];
      for (int i = 0; i < outcomes.length; i++) {
         outcomes[i] = members.get(i).getOutcome();
      }
      return outcomes;
   }

   private static class DummyRandomiser implements Randomiser {
      // use hard-coded values so always get the same result
      private static double[] RESULTS = new double[] {177, 66, 160, 151, 152, 92, 113, 270, 204, 258, 93, 36, 69, 7, 143, 265, 62, 172, 118, 213, 225, 134, 180, 201, 193, 230};

      private int ctr;

      @Override
      public double getDouble(double minInclusive, double maxInclusive) {
         return RESULTS[ctr++];
      }
   }
}
