package com.how2examples.ai.util.data;

import com.how2examples.ai.util.ImmutableArray;

/**
 * Represents a collection of data presented in tabular form.
 * <p>
 * Each column represents a particular variable.
 */
public class DataSet {
   private final ImmutableArray<String> keys;
   private final ImmutableArray<DataSetRow> values;

   public DataSet(ImmutableArray<String> key, ImmutableArray<DataSetRow> values) {
      this.keys = key;
      this.values = values;
   }

   public int getNumberOfColumns() {
      return keys.size();
   }

   public ImmutableArray<String> getKeys() {
      return keys;
   }

   public ImmutableArray<DataSetRow> getValues() {
      return values;
   }

   public int size() {
      return values.size();
   }
}
