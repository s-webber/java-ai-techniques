package com.example.ai.decisiontree;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import com.example.ai.util.data.DataSetRow;

/**
 * Node of a decision tree - contains links to child nodes.
 * <p>
 * <img src="doc-files/DecisionTree.png">
 */
public class ChoiceNode implements DecisionTreeNode {
   private final String key;
   private final int columnIndex;
   private final Map<String, DecisionTreeNode> children;

   public ChoiceNode(final String key, final int columnIndex, final Map<String, DecisionTreeNode> children) {
      this.key = key;
      this.columnIndex = columnIndex;
      // put in tree map - so ordered - and immutable (regardless of subsequent changes to children)
      this.children = Collections.unmodifiableMap(new TreeMap<String, DecisionTreeNode>(children));
   }

   @Override
   public String getId() {
      return key;
   }

   @Override
   public String getOutcome(final DataSetRow line) {
      final String value = line.getValue(columnIndex);
      return children.get(value).getOutcome(line);
   }

   @Override
   public Map<String, DecisionTreeNode> getChildren() {
      return children;
   }
}
