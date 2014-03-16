package com.how2examples.ai.util.data;

import com.google.common.collect.ImmutableList;

/**
 * Represents a collection of data presented in tabular form.
 * <p>
 * Each column represents a particular variable.
 */
public class DataSet {
   private final ImmutableList<String> keys;
   private final ImmutableList<DataSetRow> values;

   public DataSet(ImmutableList<String> key, ImmutableList<DataSetRow> values) {
      this.keys = key;
      this.values = values;
   }

   public int getNumberOfColumns() {
      return keys.size();
   }

   public ImmutableList<String> getKeys() {
      return keys;
   }

   public ImmutableList<DataSetRow> getValues() {
      return values;
   }

   public int size() {
      return values.size();
   }
}
