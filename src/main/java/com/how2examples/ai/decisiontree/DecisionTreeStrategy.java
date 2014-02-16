package com.how2examples.ai.decisiontree;

import com.how2examples.ai.util.ImmutableArray;
import com.how2examples.ai.util.data.DataSetRow;

/**
 * Represents a function for evaluating the suitability of an attribute (variable) for splitting a data set.
 * <p>
 * The suitability of an attribute is defined by how well the attribute splits the set - with the aim of narrowing down
 * the number of distinct possible outcomes of each resulting subset.
 * <p>
 * <img src="doc-files/DecisionTree.png">
 * 
 * @see http://en.wikipedia.org/wiki/Decision_tree_learning#Formulae
 */
public interface DecisionTreeStrategy {
   double gain(final ImmutableArray<DataSetRow> data, final int columnIndex, final int outcomeColumnIndex);
}