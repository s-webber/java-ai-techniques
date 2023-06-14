package com.example.ai.cluster.hierarchy;

import static com.example.ai.TestUtils.assertExactlyEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.example.ai.util.data.DataSetRow;

public class ParentClusterTest {
   private final LeafCluster child2_1 = createLeafCluster("-5", "8", "9", "x");
   private final LeafCluster child2_2 = createLeafCluster("3.5", "4", "0", "y");
   private final ParentCluster child1 = new ParentCluster(child2_1, child2_2);
   private final LeafCluster child2 = createLeafCluster("4", ".5", "-1780", "z");
   private final ParentCluster parentCluster = new ParentCluster(child1, child2);

   @Test
   public void testGetId() {
      HierarchicalCluster cluster = parentCluster;
      assertEquals("[[x|y]|z]", cluster.getId());
      cluster = getFirstChild(cluster);
      assertEquals("[x|y]", cluster.getId());
      cluster = getFirstChild(cluster);
      assertEquals("x", cluster.getId());
   }

   private HierarchicalCluster getFirstChild(final HierarchicalCluster cluster) {
      return cluster.getChildren().get(0);
   }

   @Test
   public void testGetChildren() {
      final ImmutableList<HierarchicalCluster> children = parentCluster.getChildren();
      assertEquals(2, children.size());
      assertSame(child1, children.get(0));
      assertSame(child2, children.get(1));
   }

   @Test
   public void testGetVector() {
      final double[] vector = parentCluster.getVector();
      assertEquals(3, vector.length);
      assertExactlyEquals(1.625, vector[0]); // (((-5+3.5)/2)+4)/2
      assertExactlyEquals(3.25, vector[1]); // (((8+4)/2)+.5)/2
      assertExactlyEquals(-887.75, vector[2]); // (((9+0)/2)-1780)/2
   }

   /**
    * Test getVector returns a safe-copy.
    * <p>
    * i.e. changes to a returned array do not alter the values returned in subsequent calls.
    */
   @Test
   public void testGetVectorReturnsCopy() {
      final double v = parentCluster.getVector()[1];
      parentCluster.getVector()[1] = v + 1;
      assertExactlyEquals(v, parentCluster.getVector()[1]);
   }

   private LeafCluster createLeafCluster(final String... values) {
      final DataSetRow row = new DataSetRow(values);
      return new LeafCluster(row);
   }
}
