package com.example.ai.problem.tiles;

import static com.example.ai.problem.tiles.EightTilesNodeTest.arrayToMap;
import static com.example.ai.problem.tiles.Tile.A;
import static com.example.ai.problem.tiles.Tile.B;
import static com.example.ai.problem.tiles.Tile.C;
import static com.example.ai.problem.tiles.Tile.D;
import static com.example.ai.problem.tiles.Tile.E;
import static com.example.ai.problem.tiles.Tile.F;
import static com.example.ai.problem.tiles.Tile.G;
import static com.example.ai.problem.tiles.Tile.H;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.ai.search.AStarSearchStrategy;
import com.example.ai.search.TreeSearch;
import com.example.ai.search.TreeSearchStrategy;

public class EightTilesAStarTest {
   private static final Tile _ = null;

   private static final Tile[] GOAL = {A, B, C, D, E, F, G, H, _};

   @Test
   public void testAlreadyComplete() {
      final Tile[][] expectedSequence = {GOAL};
      assertSequence(expectedSequence);
   }

   @Test
   public void testOneMoveRequired() {
      final Tile[][] expectedSequence = { {A, B, C, D, E, F, G, _, H}, GOAL};

      assertSequence(expectedSequence);
   }

   @Test
   public void testTwoMovesRequired() {
      final Tile[][] expectedSequence = { {A, B, C, D, E, F, _, G, H}, {A, B, C, D, E, F, G, _, H}, GOAL};

      assertSequence(expectedSequence);
   }

   @Test
   public void testComplex1() {
      final Tile[][] expectedSequence = {
               // starting position:
               {_, A, B, C, D, E, F, G, H},
               {A, _, B, C, D, E, F, G, H},
               {A, D, B, C, _, E, F, G, H},
               {A, D, B, _, C, E, F, G, H},
               {A, D, B, F, C, E, _, G, H},
               {A, D, B, F, C, E, G, _, H},
               {A, D, B, F, C, E, G, H, _},
               {A, D, B, F, C, _, G, H, E},
               {A, D, B, F, _, C, G, H, E},
               {A, D, B, _, F, C, G, H, E},
               {A, D, B, G, F, C, _, H, E},
               {A, D, B, G, F, C, H, _, E},
               {A, D, B, G, _, C, H, F, E},
               {A, _, B, G, D, C, H, F, E},
               {A, B, _, G, D, C, H, F, E},
               {A, B, C, G, D, _, H, F, E},
               {A, B, C, G, D, E, H, F, _},
               {A, B, C, G, D, E, H, _, F},
               {A, B, C, G, D, E, _, H, F},
               {A, B, C, _, D, E, G, H, F},
               {A, B, C, D, _, E, G, H, F},
               {A, B, C, D, E, _, G, H, F},
               // goal state:
               GOAL};

      assertSequence(expectedSequence);
   }

   @Test
   public void testComplex2() {
      final Tile[][] expectedSequence = {
               // starting position:
               {_, H, G, F, E, D, C, B, A},
               {H, _, G, F, E, D, C, B, A},
               {H, G, _, F, E, D, C, B, A},
               {H, G, D, F, E, _, C, B, A},
               {H, G, D, F, E, A, C, B, _},
               {H, G, D, F, E, A, C, _, B},
               {H, G, D, F, E, A, _, C, B},
               {H, G, D, _, E, A, F, C, B},
               {_, G, D, H, E, A, F, C, B},
               {G, _, D, H, E, A, F, C, B},
               {G, D, _, H, E, A, F, C, B},
               {G, D, A, H, E, _, F, C, B},
               {G, D, A, H, E, B, F, C, _},
               {G, D, A, H, E, B, F, _, C},
               {G, D, A, H, E, B, _, F, C},
               {G, D, A, _, E, B, H, F, C},
               {_, D, A, G, E, B, H, F, C},
               {D, _, A, G, E, B, H, F, C},
               {D, A, _, G, E, B, H, F, C},
               {D, A, B, G, E, _, H, F, C},
               {D, A, B, G, E, C, H, F, _},
               {D, A, B, G, E, C, H, _, F},
               {D, A, B, G, E, C, _, H, F},
               {D, A, B, _, E, C, G, H, F},
               {_, A, B, D, E, C, G, H, F},
               {A, _, B, D, E, C, G, H, F},
               {A, B, _, D, E, C, G, H, F},
               {A, B, C, D, E, _, G, H, F},
               // goal state:
               GOAL};

      assertSequence(expectedSequence);
   }

   @Test
   public void testComplex3() {
      final Tile[][] expectedSequence = {
               // starting position:
               {_, H, G, F, E, D, B, A, C},
               {F, H, G, _, E, D, B, A, C},
               {F, H, G, B, E, D, _, A, C},
               {F, H, G, B, E, D, A, _, C},
               {F, H, G, B, _, D, A, E, C},
               {F, _, G, B, H, D, A, E, C},
               {_, F, G, B, H, D, A, E, C},
               {B, F, G, _, H, D, A, E, C},
               {B, F, G, A, H, D, _, E, C},
               {B, F, G, A, H, D, E, _, C},
               {B, F, G, A, _, D, E, H, C},
               {B, F, G, A, D, _, E, H, C},
               {B, F, G, A, D, C, E, H, _},
               {B, F, G, A, D, C, E, _, H},
               {B, F, G, A, _, C, E, D, H},
               {B, _, G, A, F, C, E, D, H},
               {B, G, _, A, F, C, E, D, H},
               {B, G, C, A, F, _, E, D, H},
               {B, G, C, A, _, F, E, D, H},
               {B, _, C, A, G, F, E, D, H},
               {_, B, C, A, G, F, E, D, H},
               {A, B, C, _, G, F, E, D, H},
               {A, B, C, G, _, F, E, D, H},
               {A, B, C, G, D, F, E, _, H},
               {A, B, C, G, D, F, _, E, H},
               {A, B, C, _, D, F, G, E, H},
               {A, B, C, D, _, F, G, E, H},
               {A, B, C, D, E, F, G, _, H},
               // goal state:
               GOAL};

      assertSequence(expectedSequence);
   }

   @Test
   public void testComplex4() {
      final Tile[][] expectedSequence = {
               // starting position:
               {_, H, G, A, B, C, F, E, D},
               {H, _, G, A, B, C, F, E, D},
               {H, G, _, A, B, C, F, E, D},
               {H, G, C, A, B, _, F, E, D},
               {H, G, C, A, _, B, F, E, D},
               {H, G, C, _, A, B, F, E, D},
               {_, G, C, H, A, B, F, E, D},
               {G, _, C, H, A, B, F, E, D},
               {G, A, C, H, _, B, F, E, D},
               {G, A, C, H, B, _, F, E, D},
               {G, A, C, H, B, D, F, E, _},
               {G, A, C, H, B, D, F, _, E},
               {G, A, C, H, B, D, _, F, E},
               {G, A, C, _, B, D, H, F, E},
               {_, A, C, G, B, D, H, F, E},
               {A, _, C, G, B, D, H, F, E},
               {A, B, C, G, _, D, H, F, E},
               {A, B, C, G, D, _, H, F, E},
               {A, B, C, G, D, E, H, F, _},
               {A, B, C, G, D, E, H, _, F},
               {A, B, C, G, D, E, _, H, F},
               {A, B, C, _, D, E, G, H, F},
               {A, B, C, D, _, E, G, H, F},
               {A, B, C, D, E, _, G, H, F},
               // goal state:
               GOAL};

      assertSequence(expectedSequence);
   }

   private void assertSequence(final Tile[][] expectedSequence) {
      final EightTilesNode result = findSolution(expectedSequence[0]);
      assertNotNull(result);
      assertTrue(result.isGoal());

      final List<EnumMap<Tile, GridPosition>> states = getSequence(result);

      assertEquals(expectedSequence.length, states.size());
      for (int i = 0; i < expectedSequence.length; i++) {
         EnumMap<Tile, GridPosition> expected = arrayToMap(expectedSequence[i]);
         assertEquals(expected, states.get(i));
      }
   }

   private List<EnumMap<Tile, GridPosition>> getSequence(final EightTilesNode result) {
      EightTilesNode parent = result;
      List<EnumMap<Tile, GridPosition>> states = new LinkedList<>();
      do {
         states.add(0, parent.getCopyOfState());
      } while ((parent = parent.getParent()) != null);
      return states;
   }

   private EightTilesNode findSolution(Tile[] startingPositions) {
      EightTilesNode startingPosition = new EightTilesNode(arrayToMap(startingPositions));
      TreeSearchStrategy<EightTilesNode> strategy = new AStarSearchStrategy<>();
      return TreeSearch.search(startingPosition, strategy);
   }
}
