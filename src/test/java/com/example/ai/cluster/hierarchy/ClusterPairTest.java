package com.example.ai.cluster.hierarchy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClusterPairTest {
   private final HierarchicalCluster c1 = new DummyHierarchicalCluster();
   private final HierarchicalCluster c2 = new DummyHierarchicalCluster();
   private final HierarchicalCluster c3 = new DummyHierarchicalCluster();
   private final HierarchicalCluster c4 = new DummyHierarchicalCluster();
   private final ClusterPair clusterPair = new ClusterPair(c1, c2);

   @Test
   public void testFirstCluster() {
      assertSame(c1, clusterPair.getFirstCluster());
   }

   @Test
   public void testSecondCluster() {
      assertSame(c2, clusterPair.getSecondCluster());
   }

   @Test
   public void testEqualsTrue() {
      assertPairEqual(clusterPair, clusterPair);
      assertPairEqual(clusterPair, new ClusterPair(c1, c2));
      assertPairEqual(clusterPair, new ClusterPair(c2, c1));
   }

   @Test
   public void testEqualsFalse() {
      assertPairNotEqual(clusterPair, new ClusterPair(c1, c1));
      assertPairNotEqual(clusterPair, new ClusterPair(c3, c2));
      assertPairNotEqual(clusterPair, new ClusterPair(c1, c4));
      assertPairNotEqual(clusterPair, new ClusterPair(c3, c4));
   }

   private static void assertPairEqual(final ClusterPair cp1, final ClusterPair cp2) {
      assertTrue(cp1.equals(cp2));
      assertTrue(cp2.equals(cp1));
      assertEquals(cp1.hashCode(), cp2.hashCode());
   }

   private static void assertPairNotEqual(final ClusterPair cp1, final ClusterPair cp2) {
      assertFalse(cp2.equals(cp1));
      assertFalse(cp1.equals(cp2));
   }
}
