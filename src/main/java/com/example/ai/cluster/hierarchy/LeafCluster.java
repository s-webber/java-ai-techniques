package com.example.ai.cluster.hierarchy;

import static com.example.ai.util.ImmutableListFactory.emptyList;

import com.google.common.collect.ImmutableList;
import com.example.ai.util.data.DataSetRow;

/**
 * Represents a node that has no children.
 * <p>
 * <img src="doc-files/HierarchicalClusterer.png">
 */
class LeafCluster implements HierarchicalCluster {
   private final DataSetRow row;

   LeafCluster(final DataSetRow row) {
      this.row = row;
   }

   @Override
   public String getId() {
      return row.getOutcome();
   }

   @Override
   public ImmutableList<HierarchicalCluster> getChildren() {
      return emptyList();
   }

   @Override
   public double[] getVector() {
      return row.getInputsAsDoubleArray();
   }
}
