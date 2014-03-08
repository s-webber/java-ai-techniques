package com.how2examples.ai.problem.tiles;

import static com.how2examples.ai.problem.tiles.Tile.A;
import static com.how2examples.ai.problem.tiles.Tile.B;
import static com.how2examples.ai.problem.tiles.Tile.BLANK;
import static com.how2examples.ai.problem.tiles.Tile.C;
import static com.how2examples.ai.problem.tiles.Tile.D;
import static com.how2examples.ai.problem.tiles.Tile.E;
import static com.how2examples.ai.problem.tiles.Tile.F;
import static com.how2examples.ai.problem.tiles.Tile.G;
import static com.how2examples.ai.problem.tiles.Tile.H;

import java.util.EnumMap;

import junit.framework.TestCase;

public class EightTilesNodeTest extends TestCase {
   private static final int NUM_GRID_POSITIONS = GridPosition.values().length;
   private static final Tile _ = BLANK;

   public void testTopLeft() {
      Tile[] state = {_, C, B, D, A, E, F, G, H};
      Tile[] child1 = {C, _, B, D, A, E, F, G, H};
      Tile[] child2 = {D, C, B, _, A, E, F, G, H};
      assertPossibleMoves(state, child1, child2);
   }

   public void testTopMiddle() {
      Tile[] state = {C, _, B, D, A, E, F, G, H};
      Tile[] child1 = {_, C, B, D, A, E, F, G, H};
      Tile[] child2 = {C, B, _, D, A, E, F, G, H};
      Tile[] child3 = {C, A, B, D, _, E, F, G, H};
      assertPossibleMoves(state, child1, child2, child3);
   }

   public void testTopRight() {
      Tile[] state = {C, B, _, D, A, E, F, G, H};
      Tile[] child1 = {C, _, B, D, A, E, F, G, H};
      Tile[] child2 = {C, B, E, D, A, _, F, G, H};
      assertPossibleMoves(state, child1, child2);
   }

   public void testMiddleLeft() {
      Tile[] state = {C, B, D, _, A, E, F, G, H};
      Tile[] child1 = {_, B, D, C, A, E, F, G, H};
      Tile[] child2 = {C, B, D, A, _, E, F, G, H};
      Tile[] child3 = {C, B, D, F, A, E, _, G, H};
      assertPossibleMoves(state, child1, child2, child3);
   }

   public void testCentre() {
      Tile[] state = {C, B, D, A, _, E, F, G, H};
      Tile[] child1 = {C, _, D, A, B, E, F, G, H};
      Tile[] child2 = {C, B, D, _, A, E, F, G, H};
      Tile[] child3 = {C, B, D, A, E, _, F, G, H};
      Tile[] child4 = {C, B, D, A, G, E, F, _, H};
      assertPossibleMoves(state, child1, child2, child3, child4);
   }

   public void testMiddleRight() {
      Tile[] state = {C, B, D, A, E, _, F, G, H};
      Tile[] child1 = {C, B, _, A, E, D, F, G, H};
      Tile[] child2 = {C, B, D, A, _, E, F, G, H};
      Tile[] child3 = {C, B, D, A, E, H, F, G, _};
      assertPossibleMoves(state, child1, child2, child3);
   }

   public void testBottomLeft() {
      Tile[] state = {C, B, D, A, E, F, _, G, H};
      Tile[] child1 = {C, B, D, _, E, F, A, G, H};
      Tile[] child2 = {C, B, D, A, E, F, G, _, H};
      assertPossibleMoves(state, child1, child2);
   }

   public void testBottomMiddle() {
      Tile[] state = {C, B, D, A, E, F, G, _, H};
      Tile[] child1 = {C, B, D, A, _, F, G, E, H};
      Tile[] child2 = {C, B, D, A, E, F, _, G, H};
      Tile[] child3 = {C, B, D, A, E, F, G, H, _};
      assertPossibleMoves(state, child1, child2, child3);
   }

   public void testBottomRight() {
      Tile[] state = {C, B, D, A, E, F, G, H, _};
      Tile[] child1 = {C, B, D, A, E, _, G, H, F};
      Tile[] child2 = {C, B, D, A, E, F, G, _, H};
      assertPossibleMoves(state, child1, child2);
   }

   public void testIsGoal() {
      Tile[] state = {A, B, C, D, E, F, G, H, _};
      EightTilesNode n = new EightTilesNode(arrayToMap(state));
      assertTrue(n.isGoal());
      assertEquals(0, n.getEstimatedCostToGoal());
   }

   public void testIsNotGoal1() {
      Tile[] state = {A, B, C, D, E, F, G, _, H};
      EightTilesNode n = new EightTilesNode(arrayToMap(state));
      assertFalse(n.isGoal());
   }

   public void testIsNotGoal2() {
      Tile[] state = {A, B, E, D, C, H, G, F, _};
      EightTilesNode n = new EightTilesNode(arrayToMap(state));
      assertFalse(n.isGoal());
   }

   public void testEstimateCost1() {
      // "F" one square from correct position
      Tile[] state = {A, B, C, D, E, _, G, H, F};
      EightTilesNode n = new EightTilesNode(arrayToMap(state));
      assertEquals(1, n.getEstimatedCostToGoal());
   }

   public void testEstimateCost2() {
      // "A" in wrong corner
      Tile[] state = {_, B, C, D, E, F, G, H, A};
      EightTilesNode n = new EightTilesNode(arrayToMap(state));
      assertEquals(4, n.getEstimatedCostToGoal());
   }

   public void testEstimateCost3() {
      // each tile 1 square from correct position
      Tile[] state = {D, C, B, A, F, E, H, G, _};
      EightTilesNode n = new EightTilesNode(arrayToMap(state));
      assertEquals(8, n.getEstimatedCostToGoal());
   }

   public void testNodeCost() {
      Tile[] state = {_, A, B, C, D, E, F, G, H};
      EightTilesNode n = new EightTilesNode(arrayToMap(state));
      assertEquals(1, n.getNodeCost());
      assertEquals(1, n.getChildren()[0].getNodeCost());
   }

   private void assertPossibleMoves(Tile[] state, Tile[]... expectedMoves) {
      EightTilesNode n = new EightTilesNode(arrayToMap(state));
      EightTilesNode[] children = n.getChildren();
      assertEquals(expectedMoves.length, children.length);
      for (int i = 0; i < expectedMoves.length; i++) {
         EnumMap<Tile, GridPosition> expected = arrayToMap(expectedMoves[i]);
         EnumMap<Tile, GridPosition> actual = children[i].getCopyOfState();
         assertEquals(expected, actual);
      }
   }

   static EnumMap<Tile, GridPosition> arrayToMap(Tile[] state) {
      EnumMap<Tile, GridPosition> result = new EnumMap<Tile, GridPosition>(Tile.class);
      for (int i = 0; i < NUM_GRID_POSITIONS; i++) {
         result.put(state[i], GridPosition.values()[i]);
      }
      return result;
   }
}
