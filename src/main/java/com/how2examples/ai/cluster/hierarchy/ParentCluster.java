package com.how2examples.ai.cluster.hierarchy;

import com.how2examples.ai.util.ImmutableArray;
import com.how2examples.ai.util.math.VectorFunctions;

/**
 * Represents a node that has children.
 * <p>
 * <img src="doc-files/HierarchicalClusterer.png">
 */
class ParentCluster implements HierarchicalCluster {
   private final ImmutableArray<HierarchicalCluster> children;

   ParentCluster(final HierarchicalCluster child1, final HierarchicalCluster child2) {
      this.children = new ImmutableArray<>(new HierarchicalCluster[] {child1, child2});
   }

   @Override
   public String getId() {
      return "[" + children.get(0).getId() + "|" + children.get(1).getId() + "]";
   }

   @Override
   public ImmutableArray<HierarchicalCluster> getChildren() {
      return children;
   }

   @Override
   public double[] getVector() {
      return VectorFunctions.averageVectors(new double[][] {children.get(0).getVector(), children.get(1).getVector()});
   }
}
