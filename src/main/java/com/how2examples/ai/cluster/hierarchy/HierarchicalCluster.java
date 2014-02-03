package com.how2examples.ai.cluster.hierarchy;

import com.how2examples.ai.util.ImmutableArray;

/**
 * A node in a tree structure of clusters.
 * <p>
 * <img src="doc-files/HierarchicalClusterer.png">
 */
public interface HierarchicalCluster {
   String getId();

   ImmutableArray<HierarchicalCluster> getChildren();

   double[] getVector();
}
