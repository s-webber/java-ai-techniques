package com.example.ai.cluster.hierarchy;

import static com.example.ai.TestUtils.assertExactlyEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.example.ai.util.ImmutableListFactory;
import com.example.ai.util.data.DataSetRow;

public class LeafClusterTest {
   @Test
   public void testGetId() {
      final LeafCluster c = createLeafCluster();
      assertEquals("xyz", c.getId());
   }

   @Test
   public void testGetChildren() {
      final LeafCluster c = createLeafCluster();
      assertSame(ImmutableListFactory.emptyList(), c.getChildren());
   }

   @Test
   public void testGetVector() {
      final LeafCluster c = createLeafCluster();
      final double[] vector = c.getVector();
      assertEquals(3, vector.length);
      assertExactlyEquals(9.0, vector[0]);
      assertExactlyEquals(1.5, vector[1]);
      assertExactlyEquals(3.0, vector[2]);
   }

   /**
    * Test getVector returns a safe-copy.
    * <p>
    * i.e. changes to a returned array do not alter the values stored in LeafCluster.
    */
   @Test
   public void testGetVectorReturnsCopy() {
      final LeafCluster c = createLeafCluster();
      assertExactlyEquals(1.5, c.getVector()[1]);
      c.getVector()[1] = 6.75;
      assertExactlyEquals(1.5, c.getVector()[1]);
   }

   private LeafCluster createLeafCluster() {
      final String[] values = new String[] {"9", "1.5", "3", "xyz"};
      final DataSetRow row = new DataSetRow(values);
      return new LeafCluster(row);
   }
}
