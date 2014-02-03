package com.how2examples.ai.cluster.hierarchy;

import junit.framework.TestCase;

import com.how2examples.ai.TestUtils;
import com.how2examples.ai.util.data.DataSet;
import com.how2examples.ai.util.math.EuclideanFunction;

public class HierarchicalClustererTest extends TestCase {
   private final HierarchicalClusterer clusterer = new HierarchicalClusterer(new EuclideanFunction());

   public void test() throws Exception {
      final HierarchicalCluster cluster = clusterer.cluster(dataSet());
      assertEquals("[[[E|K]|[[B|D]|[I|[H|J]]]]|[G|[[A|F]|[C|L]]]]", cluster.getId());
   }

   private DataSet dataSet() throws Exception {
      return TestUtils.getDataSet(this);
   }
}
