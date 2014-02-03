package com.how2examples.ai.cluster.hierarchy;

import com.how2examples.ai.util.ImmutableArray;
import com.how2examples.ai.util.data.DataSetRow;

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

   @SuppressWarnings("unchecked")
   @Override
   public ImmutableArray<HierarchicalCluster> getChildren() {
      return ImmutableArray.EMPTY_IMMUTABLE_ARRAY;
   }

   @Override
   public double[] getVector() {
      return row.getInputsAsDoubleArray();
   }
}
