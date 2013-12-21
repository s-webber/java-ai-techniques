package com.how2examples.ai.util.data;

import junit.framework.TestCase;

import com.how2examples.ai.util.ImmutableArray;

public class DataSetTest extends TestCase {
   public void test() {
      final ImmutableArray<String> keys = toImmutableArray("fgh", "fghfhj", "ytry");
      final ImmutableArray<DataSetRow> rows = toImmutableArray(row("a", "b", "c"), row("1678", "-876", "7868"), row("12", "hjgjh", "-9.25"));
      final DataSet dataSet = new DataSet(keys, rows);

      assertEquals(keys.size(), dataSet.getNumberOfColumns());
      assertSame(keys, dataSet.getKeys());
      assertSame(rows, dataSet.getValues());
   }

   @SuppressWarnings("unchecked")
   private <E> ImmutableArray<E> toImmutableArray(E... elements) {
      return new ImmutableArray<E>(elements);
   }

   private DataSetRow row(String... data) {
      return new DataSetRow(data);
   }
}
