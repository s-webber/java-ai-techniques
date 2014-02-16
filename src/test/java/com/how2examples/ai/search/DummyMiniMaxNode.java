package com.how2examples.ai.search;

class DummyMiniMaxNode implements MiniMaxNode {
   final String id;
   final Integer goodness;
   final MiniMaxNode[] children;

   DummyMiniMaxNode(final String id, final MiniMaxNode... children) {
      this(id, null, children);
   }

   DummyMiniMaxNode(final String id, final Integer goodness, final MiniMaxNode... children) {
      this.id = id;
      this.goodness = goodness;
      this.children = children;
   }

   @Override
   public MiniMaxNode[] getChildren() {
      return children;
   }

   @Override
   public int getGoodness() {
      return goodness;
   }
}