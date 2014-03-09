package com.how2examples.ai.decisiontree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.how2examples.ai.util.ImmutableArray;
import com.how2examples.ai.util.data.DataSet;
import com.how2examples.ai.util.data.DataSetRow;

/**
 * Implements decision tree learning to represent a {@link DataSet} as a decision tree.
 * <p>
 * Implementation of the ID3 (Iterative Dichotomiser 3) decision tree learning algorithm.
 * </p>
 * <p>
 * <img src="doc-files/DecisionTree.png">
 * </p>
 * See: <a href="http://en.wikipedia.org/wiki/ID3_algorithm">Wikipedia</a>
 */
public class DecisionTreeGenerator {
   private final DecisionTreeStrategy strategy;

   public DecisionTreeGenerator(final DecisionTreeStrategy strategy) {
      this.strategy = strategy;
   }

   public DecisionTreeNode generateTree(final DataSet dataSet) {
      final ImmutableArray<DataSetRow> values = dataSet.getValues();
      if (isOnlyOneOutcome(values)) {
         return new OutcomeNode(values.get(0).getOutcome());
      } else {
         // minus 1 as final column is the outcome (so not included in candidates for filtering)
         return recursiveGenerateTree(dataSet, new boolean[dataSet.getNumberOfColumns() - 1]);
      }
   }

   private DecisionTreeNode recursiveGenerateTree(final DataSet dataSet, final boolean[] alreadyFilteredKeys) {
      final ImmutableArray<String> keys = dataSet.getKeys();
      final Map<String, DecisionTreeNode> children = new TreeMap<>();
      final int columnToSplitOn = getBestColumnIndexToSplitOn(dataSet, alreadyFilteredKeys);
      final Map<String, List<DataSetRow>> subsets = getSubsets(dataSet.getValues(), columnToSplitOn);
      final boolean[] updatedAlreadyFilteredKeys = createNewAlreadyFilteredKeys(alreadyFilteredKeys, columnToSplitOn);
      for (final Entry<String, List<DataSetRow>> e : subsets.entrySet()) {
         final String key = e.getKey();
         final DecisionTreeNode child;
         final ImmutableArray<DataSetRow> values = new ImmutableArray<>(e.getValue());
         if (isOnlyOneOutcome(values)) {
            child = new OutcomeNode(e.getValue().get(0).getOutcome());
         } else {
            final DataSet subset = new DataSet(keys, values);
            child = recursiveGenerateTree(subset, updatedAlreadyFilteredKeys);
         }
         children.put(key, child);
      }
      return new ChoiceNode(keys.get(columnToSplitOn), columnToSplitOn, children);
   }

   private int getBestColumnIndexToSplitOn(final DataSet dataSet, final boolean[] alreadyFilteredKeys) {
      final ImmutableArray<DataSetRow> values = dataSet.getValues();
      int bestIndexSoFar = -1;
      double bestGainSoFar = -Double.MAX_VALUE;
      for (int i = 0; i < alreadyFilteredKeys.length; i++) {
         if (!alreadyFilteredKeys[i]) {
            // n.alreadyFilteredKeys.length = last column = outcome
            final double gain = strategy.gain(values, i, alreadyFilteredKeys.length);
            if (gain > bestGainSoFar) {
               bestGainSoFar = gain;
               bestIndexSoFar = i;
            }
         }
      }
      if (bestIndexSoFar == -1) {
         throw new IllegalArgumentException("The following " + dataSet.size() + " rows share the same inputs but not the same outcome: " + values);
      }
      return bestIndexSoFar;
   }

   private static Map<String, List<DataSetRow>> getSubsets(final ImmutableArray<DataSetRow> data, final int columnIndex) {
      final Map<String, List<DataSetRow>> result = new HashMap<>();
      for (final DataSetRow line : data) {
         final String value = line.getValue(columnIndex);
         List<DataSetRow> subset = result.get(value);
         if (subset == null) {
            subset = new ArrayList<DataSetRow>();
            result.put(value, subset);
         }
         subset.add(line);
      }
      return result;
   }

   private static boolean[] createNewAlreadyFilteredKeys(final boolean[] currentState, final int columnIndex) {
      final boolean[] newState = new boolean[currentState.length];
      for (int i = 0; i < newState.length; i++) {
         newState[i] = currentState[i];
      }
      newState[columnIndex] = true;
      return newState;
   }

   private boolean isOnlyOneOutcome(final ImmutableArray<DataSetRow> lines) {
      final String value = lines.get(0).getOutcome();
      for (int i = 1; i < lines.size(); i++) {
         if (!value.equals(lines.get(i).getOutcome())) {
            return false;
         }
      }
      return true;
   }
}
