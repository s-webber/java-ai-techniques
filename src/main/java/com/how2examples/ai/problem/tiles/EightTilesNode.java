package com.how2examples.ai.problem.tiles;

import static com.how2examples.ai.problem.tiles.Square.BOTTOM_CENTRE;
import static com.how2examples.ai.problem.tiles.Square.BOTTOM_LEFT;
import static com.how2examples.ai.problem.tiles.Square.BOTTOM_RIGHT;
import static com.how2examples.ai.problem.tiles.Square.CENTRE;
import static com.how2examples.ai.problem.tiles.Square.MIDDLE_LEFT;
import static com.how2examples.ai.problem.tiles.Square.MIDDLE_RIGHT;
import static com.how2examples.ai.problem.tiles.Square.TOP_CENTRE;
import static com.how2examples.ai.problem.tiles.Square.TOP_LEFT;
import static com.how2examples.ai.problem.tiles.Square.TOP_RIGHT;
import static com.how2examples.ai.problem.tiles.Tile.BLANK;
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
   private static final EnumMap<Tile, Square> GOAL = new EnumMap<Tile, Square>(Tile.class);
   static {
      final Tile[] tiles = Tile.values();
      final Square[] squares = Square.values();
      for (int i = 0; i < tiles.length; i++) {
         GOAL.put(tiles[i], squares[i]);
      }
   }

   private static final EnumMap<Square, EnumSet<Square>> MOVES = new EnumMap<>(Square.class);
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
    * The Manhattan distance from every square to every other square.
    * <p>
    * Used to estimate cost to goal, as used by heuristic function.
    */
   private static final EnumMap<Square, EnumMap<Square, Integer>> DISTANCES = new EnumMap<>(Square.class);
   static {
      // For every square work out the distance to every other square.
      // Note: not very efficient as works out each distance twice 
      // (e.g. both TOP_LEFT to BOTTOM_RIGHT and BOTTOM_RIGHT to TOP_LEFT - even though the result for both is the same)
      for (Square from : Square.values()) {
         EnumMap<Square, Integer> m = new EnumMap<>(Square.class);
         DISTANCES.put(from, m);
         for (Square to : Square.values()) {
            m.put(to, calculateDistance(from, to));
         }
      }
   }

   /**
    * Returns the distance between the two specified squares.
    * <p>
    * Distance is calculated as the sum of horizontal and vertical distances (as tiles cannot be moved diagonally).
    */
   private static int calculateDistance(Square from, Square to) {
      return Math.abs(from.x - to.x) + Math.abs(from.y - to.y);
   }

   private final EightTilesNode parent;
   private final EnumMap<Tile, Square> state;
   private final int cost;

   EightTilesNode(EnumMap<Tile, Square> startState) {
      this(null, startState);
   }

   private EightTilesNode(EightTilesNode parent, EnumMap<Tile, Square> state) {
      this.parent = parent;
      this.state = state;
      this.cost = calculateCost();
   }

   private int calculateCost() {
      int cost = 0;
      for (Entry<Tile, Square> e : state.entrySet()) {
         Tile t = e.getKey();
         if (t != BLANK) {
            Square actual = e.getValue();
            Square target = GOAL.get(t);
            cost += DISTANCES.get(actual).get(target);
         }
      }
      return cost;
   }

   @Override
   public EightTilesNode[] getChildren() {
      EnumSet<Square> possibleMoves = getPossibleMoves();

      List<EightTilesNode> children = new ArrayList<>();
      for (Square possibleMove : possibleMoves) {
         EnumMap<Tile, Square> copy = createNewState(possibleMove);
         if (isNewState(copy)) {
            children.add(new EightTilesNode(this, copy));
         }
      }

      return children.toArray(new EightTilesNode[children.size()]);
   }

   private EnumSet<Square> getPossibleMoves() {
      Square blankPosition = getBlankPosition();
      EnumSet<Square> possibleMoves = MOVES.get(blankPosition);
      return possibleMoves;
   }

   private EnumMap<Tile, Square> createNewState(Square possibleMove) {
      EnumMap<Tile, Square> copy = getCopyOfState();
      copy.put(BLANK, possibleMove);
      copy.put(getTile(possibleMove), getBlankPosition());
      return copy;
   }

   private Square getBlankPosition() {
      return state.get(BLANK);
   }

   private Tile getTile(Square square) {
      for (Entry<Tile, Square> e : state.entrySet()) {
         if (e.getValue() == square) {
            return e.getKey();
         }
      }
      throw new IllegalArgumentException();
   }

   private boolean isNewState(EnumMap<Tile, Square> nextState) {
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
   EnumMap<Tile, Square> getCopyOfState() {
      return state.clone();
   }
}