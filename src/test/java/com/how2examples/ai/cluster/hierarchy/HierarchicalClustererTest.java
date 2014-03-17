package com.how2examples.ai.cluster.hierarchy;

import static com.how2examples.ai.TestUtils.getDataSet;
import junit.framework.TestCase;

import com.how2examples.ai.util.data.DataSet;
import com.how2examples.ai.util.math.EuclideanFunction;

public class HierarchicalClustererTest extends TestCase {
   private final HierarchicalClusterer clusterer = new HierarchicalClusterer(new EuclideanFunction());

   public void test() {
      final DataSet dataSet = getDataSet(getClass());
      final HierarchicalCluster cluster = clusterer.cluster(dataSet);
      assertEquals("[[[E|K]|[[B|D]|[I|[H|J]]]]|[G|[[A|F]|[C|L]]]]", cluster.getId());
   }
}
