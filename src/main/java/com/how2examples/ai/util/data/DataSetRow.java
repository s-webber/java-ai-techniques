package com.how2examples.ai.util.data;

import java.util.Arrays;

/**
 * A member of a data set.
 * <p>
 * It lists values for each of the variables of the data set.
 * 
 * @see DataSet
 */
public class DataSetRow {
   private final String[] values;

   public DataSetRow(String values[]) {
      this.values = values;
   }

   public String getOutcome() {
      return values[values.length - 1];
   }

   public double getOutcomeAsDouble() {
      return Double.parseDouble(getOutcome());
   }

   public double[] getInputsAsDoubleArray() {
      // length-1 to skip last column which is the result/outcome 
      double[] v = new double[values.length - 1];
      for (int i = 0; i < v.length; i++) {
         v[i] = Double.parseDouble(values[i]);
      }
      return v;
   }

   public int[] getInputsAsIntArray() {
      // length-1 to skip last column which is the result/outcome 
      int[] v = new int[values.length - 1];
      for (int i = 0; i < v.length; i++) {
         v[i] = Integer.parseInt(values[i]);
      }
      return v;
   }

   public int getNumberOfInputs() {
      return values.length - 1;
   }

   public String getValue(int columnIndex) {
      return values[columnIndex];
   }

   @Override
   public String toString() {
      return Arrays.toString(values);
   }
}
