package com.how2examples.ai.problem.noughtsandcrosses;

import static com.how2examples.ai.problem.noughtsandcrosses.Square.BOTTOM_CENTRE;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.BOTTOM_LEFT;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.BOTTOM_RIGHT;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.CENTRE;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.MIDDLE_LEFT;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.MIDDLE_RIGHT;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.TOP_CENTRE;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.TOP_LEFT;
import static com.how2examples.ai.problem.noughtsandcrosses.Square.TOP_RIGHT;
import static java.util.EnumSet.of;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Contains configuration of all possible winning lines.
 * <p>
 * i.e. all sets of 3 squares that form a horizontal, vertical, or diagonal line.
 */
class Lines {
   private static final List<EnumSet<Square>> LINES = new ArrayList<>();
   static {
      // horizontal
      LINES.add(of(TOP_LEFT, TOP_CENTRE, TOP_RIGHT));
      LINES.add(of(MIDDLE_LEFT, CENTRE, MIDDLE_RIGHT));
      LINES.add(of(BOTTOM_LEFT, BOTTOM_CENTRE, BOTTOM_RIGHT));

      // vertical
      LINES.add(of(TOP_LEFT, MIDDLE_LEFT, BOTTOM_LEFT));
      LINES.add(of(TOP_CENTRE, CENTRE, BOTTOM_CENTRE));
      LINES.add(of(TOP_RIGHT, MIDDLE_RIGHT, BOTTOM_RIGHT));

      // diagonal
      LINES.add(of(TOP_LEFT, CENTRE, BOTTOM_RIGHT));
      LINES.add(of(TOP_RIGHT, CENTRE, BOTTOM_LEFT));
   }

   /**
    * Returns true if the specified set contains a winning line.
    * <p>
    * i.e. if the set contains three {@link Square} instances that form a horizontal, vertical, or diagonal line.
    */
   static boolean hasLine(EnumSet<Square> squaresPopulatedBySymbol) {
      for (EnumSet<Square> line : LINES) {
         if (squaresPopulatedBySymbol.containsAll(line)) {
            return true;
         }
      }
      return false;
   }
}
