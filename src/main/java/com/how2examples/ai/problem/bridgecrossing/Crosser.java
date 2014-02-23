package com.how2examples.ai.problem.bridgecrossing;

/**
 * An item that can cross the bridge.
 * <p>
 * <blockquote>Four people need to cross a rickety rope bridge to get back to their camp at night. They only have one
 * torch. One can cross the bridge in 1 minute, another in 2 minutes, the third in 5 minutes, and the fourth takes 10
 * minutes to cross.</blockquote>
 */
public enum Crosser {
   A(1),
   B(2),
   C(5),
   D(10),
   TORCH(0);

   private final int timeTaken;

   Crosser(int timeTaken) {
      this.timeTaken = timeTaken;
   }

   int getTimeTaken() {
      return timeTaken;
   }
}
