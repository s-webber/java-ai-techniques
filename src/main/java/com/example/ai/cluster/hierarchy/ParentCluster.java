package com.example.ai.cluster.hierarchy;

import static com.example.ai.util.ImmutableListFactory.createList;

import com.google.common.collect.ImmutableList;
import com.example.ai.util.math.VectorFunctions;

/**
 * Represents a node that has children.
 * <p>
 * <img src="doc-files/HierarchicalClusterer.png">
 */
class ParentCluster implements HierarchicalCluster {
   private final ImmutableList<HierarchicalCluster> children;

   ParentCluster(final HierarchicalCluster child1, final HierarchicalCluster child2) {
      this.children = createList(child1, child2);
   }

   @Override
   public String getId() {
      return "[" + children.get(0).getId() + "|" + children.get(1).getId() + "]";
   }

   @Override
   public ImmutableList<HierarchicalCluster> getChildren() {
      return children;
   }

   @Override
   public double[] getVector() {
      return VectorFunctions.averageVectors(new double[][] {children.get(0).getVector(), children.get(1).getVector()});
   }
}
