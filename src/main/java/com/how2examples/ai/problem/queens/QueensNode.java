package com.how2examples.ai.problem.queens;

import static java.lang.Long.bitCount;

import java.util.ArrayList;

import com.how2examples.ai.search.BlindSearchNode;

/**
 * Represents a particular state in an attempt to find a solution to a n-queens problem.
 * <p>
 * The n-queens problem requires placing <i>n</i> queens on an <i>n</i>x<i>n</i> chess board. A solution requires that
 * no two queens share the same row, column, or diagonal.
 * </p>
 * <img src="../../search/doc-files/TreeSearch.png">
 * 
 * @see http://en.wikipedia.org/wiki/Eight_queens
 */
class QueensNode implements BlindSearchNode {
   private final QueensConfiguration configuration;
   /** The state that lead directly to the current state represented by this node. */
   private QueensNode parent;
   /** The number of queens present in the current state */
   private int numberQueens;
   /** The positions of the queens present in the current state */
   private long queenPositions;
   /**
    * Squares in the current state that are no longer available.
    * <p>
    * (Either because they are populated by, or covered by, an already placed queen.)
    */
   private long unavailableSquares;

   QueensNode(QueensConfiguration configuration) {
      this.configuration = configuration;
   }

   private QueensNode(QueensConfiguration configuration, QueensNode parent, long queenPositions, long unavailableSquares) {
      this.configuration = configuration;
      this.parent = parent;
      this.numberQueens = bitCount(queenPositions);
      this.queenPositions = queenPositions;
      this.unavailableSquares = unavailableSquares;
   }

   boolean isSet(int file, int rank) {
      return (getSquareId(file, rank) & queenPositions) != 0;
   }

   @Override
   public QueensNode[] getChildren() {
      ArrayList<QueensNode> result = new ArrayList<QueensNode>();

      final int file = numberQueens;
      for (int rank = 0; rank < getBoardWidth(); rank++) {
         long square = getSquareId(file, rank);
         if ((unavailableSquares & square) == 0) {
            long childQueenPositions = queenPositions + square;
            long childUnavailableSquares = unavailableSquares | getCoverage(file, rank);
            QueensNode child = new QueensNode(configuration, this, childQueenPositions, childUnavailableSquares);
            result.add(child);
         }
      }

      return result.toArray(new QueensNode[result.size()]);
   }

   private int getBoardWidth() {
      return configuration.getBoardWidth();
   }

   private long getSquareId(int file, int rank) {
      return configuration.getSquareId(file, rank);
   }

   private long getCoverage(int file, int rank) {
      return configuration.getCoverage(file, rank);
   }

   @Override
   public QueensNode getParent() {
      return parent;
   }

   @Override
   public boolean isGoal() {
      return numberQueens == getBoardWidth();
   }

   @Override
   public String getId() {
      return "EightQueensNode-" + hashCode();
   }
}
