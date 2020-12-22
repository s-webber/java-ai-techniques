package com.example.ai.cluster.hierarchy;

import com.google.common.collect.ImmutableList;

/**
 * A node in a tree structure of clusters.
 * <p>
 * <img src="doc-files/HierarchicalClusterer.png">
 */
public interface HierarchicalCluster {
   String getId();

   ImmutableList<HierarchicalCluster> getChildren();

   double[] getVector();
}
