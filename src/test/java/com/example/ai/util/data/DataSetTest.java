package com.example.ai.util.data;

import static com.example.ai.util.ImmutableListFactory.createList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

public class DataSetTest {
   @Test
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
