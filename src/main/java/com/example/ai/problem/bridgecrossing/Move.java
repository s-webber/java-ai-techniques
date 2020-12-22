package com.example.ai.problem.bridgecrossing;

import java.util.EnumSet;

/**
 * A valid combination of {@link Crosser} instances that can cross the bridge at the same time.
 * <p>
 * <blockquote>The bridge is too dangerous to cross without a torch, and it's only strong enough to support two people
 * at any given time.</blockquote>
 */
enum Move {
   A(Crosser.A),
   B(Crosser.B),
   C(Crosser.C),
   D(Crosser.D),
   A_B(Crosser.A, Crosser.B),
   A_C(Crosser.A, Crosser.C),
   A_D(Crosser.A, Crosser.D),
   B_C(Crosser.B, Crosser.C),
   B_D(Crosser.B, Crosser.D),
   C_D(Crosser.C, Crosser.D);

   private final EnumSet<Crosser> crossers;
   private final int timeTaken;

   Move(Crosser... people) {
      crossers = createCrossers(people);
      timeTaken = getTimeTaken(people);
   }

   private static int getTimeTaken(Crosser... people) {
      int result = 0;
      for (Crosser person : people) {
         result = Math.max(result, person.getTimeTaken());
      }
      return result;
   }

   private EnumSet<Crosser> createCrossers(Crosser... people) {
      // every move must include the torch (the bridge is too dangerous to cross without a torch)
      EnumSet<Crosser> result = EnumSet.of(Crosser.TORCH);
      for (Crosser person : people) {
         result.add(person);
      }
      return result;
   }

   EnumSet<Crosser> getCrossers() {
      return crossers.clone();
   }

   int getTimeTaken() {
      return timeTaken;
   }
}
