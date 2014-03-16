package com.how2examples.ai.util.data;

import static com.how2examples.ai.util.ImmutableListFactory.createList;
import junit.framework.TestCase;

import com.google.common.collect.ImmutableList;

public class DataSetTest extends TestCase {
   public void test() {
      final ImmutableList<String> keys = createList("fgh", "fghfhj", "ytry");
      final ImmutableList<DataSetRow> rows = createList(row("a", "b", "c"), row("1678", "-876", "7868"), row("12", "hjgjh", "-9.25"));
      final DataSet dataSet = new DataSet(keys, rows);

      assertEquals(keys.size(), dataSet.getNumberOfColumns());
      assertSame(keys, dataSet.getKeys());
      assertSame(rows, dataSet.getValues());
   }

   private DataSetRow row(String... data) {
      return new DataSetRow(data);
   }
}
