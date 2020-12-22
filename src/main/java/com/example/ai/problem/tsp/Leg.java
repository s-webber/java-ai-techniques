package com.example.ai.problem.tsp;

/** A pair of cities and the distance between them. */
class Leg {
   private final String city1;
   private final String city2;
   private final int distance;

   Leg(String city1, String city2, int distance) {
      this.city1 = city1;
      this.city2 = city2;
      this.distance = distance;
   }

   String getCity1() {
      return city1;
   }

   String getCity2() {
      return city2;
   }

   int getDistance() {
      return distance;
   }
}
