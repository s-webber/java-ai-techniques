package com.example.ai.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class TreeSearchTest {
   @Test
   public void testBreadthFirstSearch() {
      final String sequence = generateSearchPath(new BreadthFirstSearchStrategy<DummyHeuristicSearchNode>(), true);
      assertEquals("A, B, C, D, E, F, G, H, I, J, K, L, M, N", sequence);
   }

   @Test
   public void testDepthFirstSearch() {
      final String sequence = generateSearchPath(new DepthFirstSearchStrategy<DummyHeuristicSearchNode>(), true);
      assertEquals("A, B, D, I, J, E, K, L, M, C, F, N", sequence);
   }

   @Test
   public void testDepthLimitedSearch() {
      final String sequence = generateSearchPath(new DepthLimitedSearchStrategy<DummyHeuristicSearchNode>(2), false);
      assertEquals("A, B, D, E, C, F, G, H", sequence);
   }

   @Test
   public void testIterativeDepthFirstSearch() {
      final DummyHeuristicSearchNode rootNode = generateExampleSearchTree();
      final DummyHeuristicSearchNode result = IterativeDepthFirstSearch.search(rootNode, 4);
      assertEquals("N", result.toString());
      final String sequence = rootNode.getSequence();
      assertEquals("A, B, C, A, B, D, E, C, F, G, H, A, B, D, I, J, E, K, L, M, C, F, N", sequence);
   }

   @Test
   public void testGreedySearch() {
      final String sequence = generateSearchPath(new GreedySearchStrategy<DummyHeuristicSearchNode>(), true);
      assertEquals("A, B, D, I, E, J, C, H, O, F, N", sequence);
   }

   @Test
   public void testAStarSearch() {
      final String sequence = generateSearchPath(new AStarSearchStrategy<DummyHeuristicSearchNode>(), true);
      assertEquals("A, B, C, D, E, H, I, F, N", sequence);
   }

   private static String generateSearchPath(final TreeSearchStrategy<DummyHeuristicSearchNode> strategy, final boolean isExpectedToFindGoal) {
      final DummyHeuristicSearchNode rootNode = generateExampleSearchTree();
      final DummyHeuristicSearchNode result = TreeSearch.search(rootNode, strategy);
      if (isExpectedToFindGoal) {
         assertEquals("N", result.toString());
      } else {
         assertNull(result);
      }
      return rootNode.getSequence();
   }

   public static DummyHeuristicSearchNode generateExampleSearchTree() {
      //         A
      //    -----|-----
      //    B         C
      //  --|--    ---|---
      //  D   E    F  G  H
      // -|- -|-   |    -|-
      // I J KLM   N    O P
      final DummyHeuristicSearchNode I = new DummyHeuristicSearchNode("I", 3);
      final DummyHeuristicSearchNode J = new DummyHeuristicSearchNode("J", 5);
      final DummyHeuristicSearchNode K = new DummyHeuristicSearchNode("K", 6);
      final DummyHeuristicSearchNode L = new DummyHeuristicSearchNode("L", 8);
      final DummyHeuristicSearchNode M = new DummyHeuristicSearchNode("M", 6);
      final DummyHeuristicSearchNode N = new DummyHeuristicSearchNode("N", 1, true);
      final DummyHeuristicSearchNode O = new DummyHeuristicSearchNode("O", 4);
      final DummyHeuristicSearchNode P = new DummyHeuristicSearchNode("P", 7);

      final DummyHeuristicSearchNode D = new DummyHeuristicSearchNode("D", 4, I, J);
      final DummyHeuristicSearchNode E = new DummyHeuristicSearchNode("E", 4, K, L, M);
      final DummyHeuristicSearchNode F = new DummyHeuristicSearchNode("F", 5, N);
      final DummyHeuristicSearchNode G = new DummyHeuristicSearchNode("G", 6);
      final DummyHeuristicSearchNode H = new DummyHeuristicSearchNode("H", 4, O, P);

      final DummyHeuristicSearchNode B = new DummyHeuristicSearchNode("B", 5, D, E);

      final DummyHeuristicSearchNode C = new DummyHeuristicSearchNode("C", 6, F, G, H);

      final DummyHeuristicSearchNode A = new DummyHeuristicSearchNode("A", B, C);

      return A;
   }
}
