package com.example.ai.decisiontree;

import com.google.common.collect.ImmutableList;
import com.example.ai.util.data.DataSetRow;

/**
 * Represents a function for evaluating the suitability of an attribute (variable) for splitting a data set.
 * <p>
 * The suitability of an attribute is defined by how well the attribute splits the set - with the aim of narrowing down
 * the number of distinct possible outcomes of each resulting subset.
 * </p>
 * <p>
 * <img src="doc-files/DecisionTree.png">
 * </p>
 * See: <a href="http://en.wikipedia.org/wiki/Decision_tree_learning#Formulae">Wikipedia</a>
 */
public interface DecisionTreeStrategy {
   double gain(final ImmutableList<DataSetRow> data, final int columnIndex, final int outcomeColumnIndex);
}
