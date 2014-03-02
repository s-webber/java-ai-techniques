package com.how2examples.ai.search;

class DummyHeuristicSearchNode implements HeuristicSearchNode {
   /**
    * Maintains a record of the order in which nodes have actually been searched.
    * <p>
    * (So tests can compare against expected order.)
    */
   private final StringBuilder sequence = new StringBuilder();

   private final String id;
   private final int nodeCost;
   private final int estimatedCostToGoal;
   private final DummyHeuristicSearchNode[] children;
   private DummyHeuristicSearchNode parent;
   private final boolean isGoal;

   DummyHeuristicSearchNode(final String id, final DummyHeuristicSearchNode... children) {
      this(id, 0, children);
   }

   DummyHeuristicSearchNode(final String id, final int estimatedCostToGoal, final DummyHeuristicSearchNode... children) {
      this(id, estimatedCostToGoal, false, children);
   }

   DummyHeuristicSearchNode(final String id, final int estimatedCostToGoal, final boolean isGoal, final DummyHeuristicSearchNode... children) {
      this.id = id;
      this.nodeCost = estimatedCostToGoal;
      this.estimatedCostToGoal = estimatedCostToGoal;
      this.children = children;
      this.isGoal = isGoal;
      for (final DummyHeuristicSearchNode child : children) {
         child.parent = this;
      }
   }

   @Override
   public String getId() {
      return id;
   }

   @Override
   public DummyHeuristicSearchNode[] getChildren() {
      return children;
   }

   @Override
   public int getEstimatedCostToGoal() {
      return estimatedCostToGoal;
   }

   @Override
   public int getNodeCost() {
      return nodeCost;
   }

   @Override
   public boolean isGoal() {
      update(this);
      return isGoal;
   }

   @Override
   public String toString() {
      return id;
   }

   @Override
   public DummyHeuristicSearchNode getParent() {
      return parent;
   }

   private void update(final DummyHeuristicSearchNode simpleNode) {
      DummyHeuristicSearchNode topNode = this;
      while (topNode.getParent() != null) {
         topNode = topNode.getParent();
      }
      if (topNode.sequence.length() > 0) {
         topNode.sequence.append(", ");
      }
      topNode.sequence.append(simpleNode.getId());
   }

   String getSequence() {
      return sequence.toString();
   }
}