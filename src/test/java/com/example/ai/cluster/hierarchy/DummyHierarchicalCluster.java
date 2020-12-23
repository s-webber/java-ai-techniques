package com.example.ai.cluster.hierarchy;

import com.google.common.collect.ImmutableList;

class DummyHierarchicalCluster implements HierarchicalCluster {
   @Override
   public String getId() {
      throw new UnsupportedOperationException();
   }

   @Override
   public ImmutableList<HierarchicalCluster> getChildren() {
      throw new UnsupportedOperationException();
   }

   @Override
   public double[] getVector() {
      return null;
   }
}
