package com.how2examples.ai.problem.tsp;

import static com.how2examples.ai.util.ImmutableListFactory.createList;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.collect.ImmutableList;

/** Details of all locations that need to be visited. */
class Locations {
   private final Map<String, Map<String, Integer>> distances;
   private final ImmutableList<String> locationNames;

   Locations(ImmutableList<Leg> legs) {
      this.distances = new TreeMap<String, Map<String, Integer>>();

      for (Leg leg : legs) {
         String a = leg.getCity1();
         String b = leg.getCity2();
         int distance = leg.getDistance();
         addMapping(a, b, distance);
         addMapping(b, a, distance);
      }

      final Set<String> keys = distances.keySet();
      this.locationNames = createList(keys.toArray(new String[keys.size()]));
   }

   private void addMapping(String a, String b, int distance) {
      Map<String, Integer> m = distances.get(a);
      if (m == null) {
         m = new HashMap<>();
         distances.put(a, m);
      }
      m.put(b, distance);
   }

   /** Returns names of all locations that need to be visited. */
   ImmutableList<String> getLocationNames() {
      return locationNames;
   }

   /**
    * Returns the distance between two locations.
    * 
    * @param from name of location departing from
    * @param to name of location arriving at
    * @return distance between {@code from} and {@code to}.
    */
   int getDistance(String from, String to) {
      Map<String, Integer> m = distances.get(from);
      if (m == null) {
         throw new IllegalArgumentException("Have no record of legs departing from: " + from);
      }
      Integer distance = m.get(to);
      if (distance == null) {
         throw new IllegalArgumentException("Have no record of distance from: " + from + " to: " + to);
      }
      return distance;
   }
}
