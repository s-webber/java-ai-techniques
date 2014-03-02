package com.how2examples.ai.problem.bridgecrossing;

import static com.how2examples.ai.problem.bridgecrossing.Crosser.TORCH;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.how2examples.ai.search.HeuristicSearchNode;

/**
 * Represents a particular state in an attempt to solve the bridge crossing problem.
 * <p>
 * <blockquote>Four people need to cross a rickety rope bridge to get back to their camp at night. Unfortunately, they
 * only have one torch and it only has enough light left for seventeen minutes. The bridge is too dangerous to cross
 * without a torch, and it's only strong enough to support two people at any given time. Each of the campers walks at a
 * different speed. One can cross the bridge in 1 minute, another in 2 minutes, the third in 5 minutes, and the fourth
 * takes 10 minutes to cross. How do the campers make it across in 17 minutes?</blockquote>
 */
class BridgeCrossingNode implements HeuristicSearchNode {
   /** All persons, plus the torch, start on the opposite side of the bank to their destination */
   private static final EnumSet<Crosser> START_STATE = EnumSet.allOf(Crosser.class);

   /**
    * The current state.
    * <p>
    * Represents all people, and the torch, that are on the "wrong" side of the bridge (i.e. people who still need to
    * cross).
    */
   private final EnumSet<Crosser> state;
   /**
    * The previous state that directly led to to {@link state}.
    * <p>
    * {@code null} if first move.
    */
   private final BridgeCrossingNode parent;
   /**
    * The move that caused the transition from {@link parent} to {@link state}.
    * <p>
    * {@code null} if first move.
    */
   private final Move previousMove;

   BridgeCrossingNode() {
      this(null, START_STATE, null);
   }

   private BridgeCrossingNode(final BridgeCrossingNode parent, final EnumSet<Crosser> state, final Move move) {
      this.parent = parent;
      this.state = state;
      this.previousMove = move;
   }

   /** Is the torch on the opposite side of the bridge to where it started? */
   private boolean isTorchCrossed() {
      return !state.contains(TORCH);
   }

   @Override
   public String getId() {
      return previousMove == null ? "Root" : previousMove.toString();
   }

   @Override
   public BridgeCrossingNode getParent() {
      return parent;
   }

   Move getMove() {
      return previousMove;
   }

   @Override
   public HeuristicSearchNode[] getChildren() {
      final List<BridgeCrossingNode> result = new ArrayList<BridgeCrossingNode>();

      for (Move possibleNextMove : Move.values()) {
         if (isValidNextMove(possibleNextMove) && isWorthConsidering(possibleNextMove)) {
            final EnumSet<Crosser> newState = createNewState(possibleNextMove);
            BridgeCrossingNode p = new BridgeCrossingNode(this, newState, possibleNextMove);
            result.add(p);
         }
      }

      return result.toArray(new HeuristicSearchNode[0]);
   }

   private EnumSet<Crosser> createNewState(Move nextMove) {
      EnumSet<Crosser> newState = state.clone();
      if (isTorchCrossed()) {
         newState.addAll(nextMove.getCrossers());
      } else {
         newState.removeAll(nextMove.getCrossers());
      }
      return newState;
   }

   /** Are the people represented by {@code possibleNextMove} all on the same side of the bank as the torch? */
   private boolean isValidNextMove(Move possibleNextMove) {
      EnumSet<Crosser> crossersOnTorchSide;
      if (isTorchCrossed()) {
         crossersOnTorchSide = EnumSet.complementOf(state);
      } else {
         crossersOnTorchSide = state;
      }
      return crossersOnTorchSide.containsAll(possibleNextMove.getCrossers());
   }

   private boolean isWorthConsidering(Move possibleNextMove) {
      if (possibleNextMove == previousMove) {
         // no point undoing the move that led directly to the current state
         // (as else might of well not performed the previous move in the first place!)
         return false;
      } else {
         return true;
      }
   }

   @Override
   public int getEstimatedCostToGoal() {
      if (isGoal()) {
         return 0;
      } else {
         // Note: not optimal - could instead do something clever here to come up with a better estimate -
         // but, as long as we don't over-estimate the cost to the goal, the A* search strategy will find the best solution eventually
         return state.size();
      }
   }

   @Override
   public int getNodeCost() {
      return previousMove == null ? 0 : previousMove.getTimeTaken();
   }

   @Override
   public boolean isGoal() {
      return state.isEmpty();
   }
}
