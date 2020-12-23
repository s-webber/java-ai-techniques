package com.example.ai.decisiontree;

import java.util.Collections;
import java.util.Map;

import com.example.ai.util.data.DataSetRow;

/**
 * Leaf node of a decision tree - a node that contains a single actual outcome.
 * <p>
 * <img src="doc-files/DecisionTree.png">
 */
public class OutcomeNode implements DecisionTreeNode {
   private final String outcome;

   OutcomeNode(String outcome) {
      this.outcome = outcome;
   }

   @Override
   public String getId() {
      return outcome;
   }

   @Override
   public String getOutcome(DataSetRow line) {
      return outcome;
   }

   @SuppressWarnings("unchecked")
   @Override
   public Map<String, DecisionTreeNode> getChildren() {
      return Collections.EMPTY_MAP;
   }
}
