package com.example.ai.cluster.hierarchy;

/**
 * Associates two {@link HierarchicalCluster} instances.
 * <p>
 * <img src="doc-files/HierarchicalClusterer.png">
 */
class ClusterPair {
   private final HierarchicalCluster c1;
   private final HierarchicalCluster c2;

   ClusterPair(final HierarchicalCluster c1, final HierarchicalCluster c2) {
      this.c1 = c1;
      this.c2 = c2;
   }

   HierarchicalCluster getFirstCluster() {
      return c1;
   }

   HierarchicalCluster getSecondCluster() {
      return c2;
   }

   @Override
   public int hashCode() {
      return c1.hashCode() + c2.hashCode();
   }

   /**
    * Indicates whether some other object is "equal to" this {@code ClusterPair} instance.
    * <p>
    * Two cluster pairs are considered equal if, and only if, they both reference the same two
    * {@code HierarchicalCluster} instances. The order in which the {@code HierarchicalCluster} instances were specified
    * in the constructor is not important. e.g.:
    * 
    * <pre>
    * new ClusterPair(c1, c2).equals(new ClusterPair(c2, c1)) == true
    * </pre>
    */
   @Override
   public boolean equals(final Object o) {
      if (o instanceof ClusterPair) {
         final ClusterPair p = (ClusterPair) o;
         return (c1 == p.c1 && c2 == p.c2) || (c1 == p.c2 && c2 == p.c1);
      } else {
         return false;
      }
   }
}
