package com.how2examples.ai.problem.tiles;

import static com.how2examples.ai.problem.tiles.GridPosition.BOTTOM_CENTRE;
import static com.how2examples.ai.problem.tiles.GridPosition.BOTTOM_LEFT;
import static com.how2examples.ai.problem.tiles.GridPosition.BOTTOM_RIGHT;
import static com.how2examples.ai.problem.tiles.GridPosition.CENTRE;
import static com.how2examples.ai.problem.tiles.GridPosition.MIDDLE_LEFT;
import static com.how2examples.ai.problem.tiles.GridPosition.MIDDLE_RIGHT;
import static com.how2examples.ai.problem.tiles.GridPosition.TOP_CENTRE;
import static com.how2examples.ai.problem.tiles.GridPosition.TOP_LEFT;
import static com.how2examples.ai.problem.tiles.GridPosition.TOP_RIGHT;
import static java.util.EnumSet.of;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map.Entry;

import com.how2examples.ai.search.HeuristicSearchNode;

/**
 * Represents a game-state of a 3x3 sliding tiles puzzle grid.
 * <p>
 * The grid contains 8 tiles, with letters A-H, and a blank (i.e. unoccupied) square. Tiles can be moved one at a time,
 * either horizontally or vertically. The puzzle is solved when the tiles are positioned in the following order:
 * 
 * <pre>
 * ABC
 * DEF
 * GH
 * </pre>
 * </p>
 * <img src="../../search/doc-files/TreeSearch.png">
 */
class EightTilesNode implements HeuristicSearchNode {
   private static final EnumMap<Tile, GridPosition> GOAL = new EnumMap<Tile, GridPosition>(Tile.class);
   static {
      final Tile[] tiles = Tile.values();
      final GridPosition[] positions = GridPosition.values();
      for (int i = 0; i < tiles.length; i++) {
         GOAL.put(tiles[i], positions[i]);
      }
   }

   private static final EnumMap<GridPosition, EnumSet<GridPosition>> MOVES = new EnumMap<>(GridPosition.class);
   static {
      MOVES.put(TOP_LEFT, of(TOP_CENTRE, MIDDLE_LEFT));
      MOVES.put(TOP_CENTRE, of(TOP_LEFT, TOP_RIGHT, CENTRE));
      MOVES.put(TOP_RIGHT, of(TOP_CENTRE, MIDDLE_RIGHT));
      MOVES.put(MIDDLE_LEFT, of(TOP_LEFT, CENTRE, BOTTOM_LEFT));
      MOVES.put(CENTRE, of(TOP_CENTRE, MIDDLE_LEFT, MIDDLE_RIGHT, BOTTOM_CENTRE));
      MOVES.put(MIDDLE_RIGHT, of(TOP_RIGHT, CENTRE, BOTTOM_RIGHT));
      MOVES.put(BOTTOM_LEFT, of(MIDDLE_LEFT, BOTTOM_CENTRE));
      MOVES.put(BOTTOM_CENTRE, of(CENTRE, BOTTOM_LEFT, BOTTOM_RIGHT));
      MOVES.put(BOTTOM_RIGHT, of(MIDDLE_RIGHT, BOTTOM_CENTRE));
   }

   /**
    * The Manhattan distance from every grid position to every other grid position.
    * <p>
    * Used to estimate cost to goal, as used by heuristic function.
    */
   private static final EnumMap<GridPosition, EnumMap<GridPosition, Integer>> DISTANCES = new EnumMap<>(GridPosition.class);
   static {
      // For every position work out the distance to every other position.
      // Note: not very efficient as works out each distance twice 
      // (e.g. both TOP_LEFT to BOTTOM_RIGHT and BOTTOM_RIGHT to TOP_LEFT - even though the result for both is the same)
      for (GridPosition from : GridPosition.values()) {
         EnumMap<GridPosition, Integer> m = new EnumMap<>(GridPosition.class);
         DISTANCES.put(from, m);
         for (GridPosition to : GridPosition.values()) {
            m.put(to, calculateDistance(from, to));
         }
      }
   }

   /**
    * Returns the distance between the two specified positions.
    * <p>
    * Distance is calculated as the sum of horizontal and vertical distances (as tiles cannot be moved diagonally).
    */
   private static int calculateDistance(GridPosition from, GridPosition to) {
      return Math.abs(from.x - to.x) + Math.abs(from.y - to.y);
   }

   private final EightTilesNode parent;
   private final EnumMap<Tile, GridPosition> state;
   private final int cost;

   EightTilesNode(EnumMap<Tile, GridPosition> startState) {
      this(null, startState);
   }

   private EightTilesNode(EightTilesNode parent, EnumMap<Tile, GridPosition> state) {
      this.parent = parent;
      this.state = state;
      this.cost = calculateCost();
   }

   private int calculateCost() {
      int cost = 0;
      for (Entry<Tile, GridPosition> e : state.entrySet()) {
         GridPosition actual = e.getValue();
         GridPosition target = GOAL.get(e.getKey());
         cost += DISTANCES.get(actual).get(target);
      }
      return cost;
   }

   @Override
   public EightTilesNode[] getChildren() {
      EnumSet<GridPosition> possibleMoves = getPossibleMoves();

      List<EightTilesNode> children = new ArrayList<>();
      for (GridPosition possibleMove : possibleMoves) {
         EnumMap<Tile, GridPosition> copy = createNewState(possibleMove);
         if (isNewState(copy)) {
            children.add(new EightTilesNode(this, copy));
         }
      }

      return children.toArray(new EightTilesNode[children.size()]);
   }

   private EnumSet<GridPosition> getPossibleMoves() {
      GridPosition blankPosition = getBlankPosition();
      EnumSet<GridPosition> possibleMoves = MOVES.get(blankPosition);
      return possibleMoves;
   }

   private EnumMap<Tile, GridPosition> createNewState(GridPosition possibleMove) {
      EnumMap<Tile, GridPosition> copy = getCopyOfState();
      copy.put(getTile(possibleMove), getBlankPosition());
      return copy;
   }

   /** Returns the single position on the grid that is not occupied by a Tile. */
   private GridPosition getBlankPosition() {
      EnumSet<GridPosition> occupied = EnumSet.copyOf(state.values());
      EnumSet<GridPosition> unoccupied = EnumSet.complementOf(occupied);
      return unoccupied.iterator().next();
   }

   private Tile getTile(GridPosition position) {
      for (Entry<Tile, GridPosition> e : state.entrySet()) {
         if (e.getValue() == position) {
            return e.getKey();
         }
      }
      throw new IllegalArgumentException();
   }

   private boolean isNewState(EnumMap<Tile, GridPosition> nextState) {
      EightTilesNode p = this;
      while ((p = p.parent) != null) {
         if (nextState.equals(p.state)) {
            return false;
         }
      }
      return true;
   }

   @Override
   public boolean isGoal() {
      return cost == 0;
   }

   @Override
   public EightTilesNode getParent() {
      return parent;
   }

   @Override
   public int getEstimatedCostToGoal() {
      return cost;
   }

   @Override
   public int getNodeCost() {
      return 1;
   }

   /** Returns a copy of the state represented by this node */
   EnumMap<Tile, GridPosition> getCopyOfState() {
      return state.clone();
   }
}