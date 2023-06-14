package com.example.ai.cluster.hierarchy;

import static com.example.ai.TestUtils.getDataSet;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.ai.util.data.DataSet;
import com.example.ai.util.math.EuclideanFunction;

public class HierarchicalClustererTest {
   private final HierarchicalClusterer clusterer = new HierarchicalClusterer(new EuclideanFunction());

   @Test
   public void test() {
      final DataSet dataSet = getDataSet(getClass());
      final HierarchicalCluster cluster = clusterer.cluster(dataSet);
      assertEquals("[[[E|K]|[[B|D]|[I|[H|J]]]]|[G|[[A|F]|[C|L]]]]", cluster.getId());
   }
}
