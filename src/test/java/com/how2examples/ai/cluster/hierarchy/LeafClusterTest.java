package com.how2examples.ai.cluster.hierarchy;

import junit.framework.TestCase;

import com.how2examples.ai.util.ImmutableListFactory;
import com.how2examples.ai.util.data.DataSetRow;

public class LeafClusterTest extends TestCase {
   public void testGetId() {
      final LeafCluster c = createLeafCluster();
      assertEquals("xyz", c.getId());
   }

   public void testGetChildren() {
      final LeafCluster c = createLeafCluster();
      assertSame(ImmutableListFactory.emptyList(), c.getChildren());
   }

   public void testGetVector() {
      final LeafCluster c = createLeafCluster();
      final double[] vector = c.getVector();
      assertEquals(3, vector.length);
      assertEquals(9.0, vector[0]);
      assertEquals(1.5, vector[1]);
      assertEquals(3.0, vector[2]);
   }

   /**
    * Test getVector returns a safe-copy.
    * <p>
    * i.e. changes to a returned array do not alter the values stored in LeafCluster.
    */
   public void testGetVectorReturnsCopy() {
      final LeafCluster c = createLeafCluster();
      assertEquals(1.5, c.getVector()[1]);
      c.getVector()[1] = 6.75;
      assertEquals(1.5, c.getVector()[1]);
   }

   private LeafCluster createLeafCluster() {
      final String[] values = new String[] {"9", "1.5", "3", "xyz"};
      final DataSetRow row = new DataSetRow(values);
      return new LeafCluster(row);
   }
}
