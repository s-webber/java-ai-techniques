package com.how2examples.ai.cluster.hierarchy;

import com.how2examples.ai.util.ImmutableArray;

class DummyHierarchicalCluster implements HierarchicalCluster {
   @Override
   public String getId() {
      throw new UnsupportedOperationException();
   }

   @Override
   public ImmutableArray<HierarchicalCluster> getChildren() {
      throw new UnsupportedOperationException();
   }

   @Override
   public double[] getVector() {
      return null;
   }
}
