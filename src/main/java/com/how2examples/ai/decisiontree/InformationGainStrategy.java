package com.how2examples.ai.decisiontree;

import static com.how2examples.ai.util.ImmutableListFactory.createList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableList;
import com.how2examples.ai.util.data.DataSetRow;

/**
 * Calculates information gain, based on the concept of entropy.
 * <p>
 * Entropy defines the variation of a data set (i.e. how many distinct possible outcomes are represented by the elements
 * it contains). Information gain represents the <i>reduction</i> in entropy that would be achieved by splitting the
 * data set into subsets based on a particular attribute (variable).
 * <p>
 * <img src="doc-files/DecisionTree.png">
 */
public class InformationGainStrategy implements DecisionTreeStrategy {
   /**
    * Calculates information gain from splitting the data set on the specified column.
    * <p>
    * (information gain = reduction in entropy)
    */
   @Override
   public double gain(final ImmutableList<DataSetRow> data, final int columnIndex, final int outcomeColumnIndex) {
      double subsetEntropy = 0;
      final Map<String, Integer> frequencies = calculateFrequencies(data, columnIndex);
      for (final Entry<String, Integer> e : frequencies.entrySet()) {
         final int frequency = e.getValue();
         final double valueProbability = (double) frequency / frequencies.size();
         final ImmutableList<DataSetRow> subset = getSubset(data, columnIndex, e.getKey());
         subsetEntropy += valueProbability * entropy(subset, outcomeColumnIndex);
      }
      return entropy(data, outcomeColumnIndex) - subsetEntropy;
   }

   private static double entropy(final ImmutableList<DataSetRow> data, final int columnIndex) {
      double entropy = 0;
      final Map<String, Integer> frequencies = calculateFrequencies(data, columnIndex);
      for (final int frequency : frequencies.values()) {
         entropy += ((double) -frequency / data.size()) * log((double) frequency / data.size(), 2);
      }
      return entropy;
   }

   private static Map<String, Integer> calculateFrequencies(final ImmutableList<DataSetRow> data, final int columnIndex) {
      final Map<String, Integer> frequencies = new HashMap<>();
      for (final DataSetRow line : data) {
         final String value = line.getValue(columnIndex);
         Integer frequency = frequencies.get(value);
         if (frequency == null) {
            frequency = new Integer(1);
         } else {
            frequency++;
         }
         frequencies.put(value, frequency);
      }
      return frequencies;
   }

   private static double log(final double number, final double base) {
      return Math.log(number) / Math.log(base);
   }

   private static ImmutableList<DataSetRow> getSubset(final ImmutableList<DataSetRow> data, final int columnIndex, final String value) {
      final List<DataSetRow> subset = new ArrayList<>();
      for (final DataSetRow line : data) {
         if (value.equals(line.getValue(columnIndex))) {
            subset.add(line);
         }
      }
      return createList(subset);
   }
}
