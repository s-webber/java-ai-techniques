package com.how2examples.ai.cluster.kmeans;

import static com.how2examples.ai.TestUtils.assertExactlyEquals;
import static com.how2examples.ai.util.ImmutableListFactory.createList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.how2examples.ai.util.data.DataSetRow;

public class KMeansClusterTest {
   private final double[] expectedCentroid = {9.25, -7, 0};
   private final String[][] expectedMembers = { {"6", "4", "5", "a"}, {"8.9", "5.25", "1.8", "b"}, {"-9", "0", "10965", "c"}};

   @Test
   public void testGetCentroid() {
      final KMeansCluster c = createCluster();
      final double[] actualCentroid = c.getCentroid();
      assertTrue(Arrays.equals(expectedCentroid, actualCentroid));
   }

   /**
    * Test getCentroid returns a safe-copy.
    * <p>
    * i.e. changes to a returned array do not alter the values stored in KMeansCluster.
    */
   @Test
   public void testGetCentroidReturnsCopy() {
      final KMeansCluster c = createCluster();
      final double[] actualCentroid = c.getCentroid();
      actualCentroid[1] = 9999;
      assertTrue(actualCentroid[1] != c.getCentroid()[1]);
      assertExactlyEquals(expectedCentroid[1], c.getCentroid()[1]);
   }

   @Test
   public void testGetMembers() {
      final KMeansCluster c = createCluster();
      final ImmutableList<DataSetRow> actualMembers = c.getMembers();
      assertEquals(expectedMembers.length, actualMembers.size());
      for (int i = 0; i < expectedMembers.length; i++) {
         final DataSetRow expectedRow = new DataSetRow(expectedMembers[i]);
         final DataSetRow actualRow = actualMembers.get(i);
         assertDataSetRowEquals(expectedRow, actualRow);
      }
   }

   private void assertDataSetRowEquals(final DataSetRow expected, final DataSetRow actual) {
      assertEquals(expected.getOutcome(), actual.getOutcome());
      assertTrue(Arrays.equals(expected.getInputsAsDoubleArray(), actual.getInputsAsDoubleArray()));
   }

   private KMeansCluster createCluster() {
      final List<DataSetRow> members = new ArrayList<DataSetRow>();
      for (final String[] expectedMember : expectedMembers) {
         members.add(new DataSetRow(expectedMember));
      }
      return new KMeansCluster(expectedCentroid, createList(members));
   }
}
