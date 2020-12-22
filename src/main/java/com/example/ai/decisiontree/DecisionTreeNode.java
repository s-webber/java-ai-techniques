package com.example.ai.decisiontree;

import java.util.Map;

import com.example.ai.util.data.DataSetRow;

/**
 * A node within a decision tree.
 * <p>
 * <img src="doc-files/DecisionTree.png">
 */
public interface DecisionTreeNode {
   String getId();

   String getOutcome(DataSetRow line);

   Map<String, DecisionTreeNode> getChildren();
}
