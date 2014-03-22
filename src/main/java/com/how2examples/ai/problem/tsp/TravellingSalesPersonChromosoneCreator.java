package com.how2examples.ai.problem.tsp;

import static com.how2examples.ai.util.ImmutableListFactory.createList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.how2examples.ai.ga.Chromosone;
import com.how2examples.ai.ga.ChromosoneFactory;
import com.how2examples.ai.util.Randomiser;

/**
 * Creates solutions (i.e. journeys) for the travelling salesman problem.
 * <p>
 * See: <a href="http://en.wikipedia.org/wiki/Travelling_salesman_problem">Wikipedia</a>
 */
class TravellingSalesPersonChromosoneCreator implements ChromosoneFactory<ImmutableList<String>> {
   private final Locations locations;
   private final Randomiser randomiser;

   TravellingSalesPersonChromosoneCreator(Locations locations, Randomiser randomiser) {
      this.locations = locations;
      this.randomiser = randomiser;
   }

   @Override
   public Chromosone<ImmutableList<String>> create() {
      final ImmutableList<String> locationNames = getLocationNames();
      final ImmutableList<String> shuffledLocationNames = createList(randomiser.shuffle(locationNames));
      return create(shuffledLocationNames);
   }

   @Override
   public Chromosone<ImmutableList<String>> crossover(ImmutableList<String> input1, ImmutableList<String> input2) {
      assertValid(input1);
      assertValid(input2);

      final Parents p = new Parents(input1, input2);
      final Set<String> remainingLocations = new HashSet<String>(getLocationNames());
      final String[] result = new String[input1.size()];

      result[0] = p.getNext().get(0);
      for (int i = 1; i < input1.size(); i++) {
         String previous = result[i - 1];
         remainingLocations.remove(previous);

         String next = getUnusedAdjacentLocation(previous, p.getNext(), remainingLocations);
         if (next == null) {
            next = getUnusedAdjacentLocation(previous, p.getNext(), remainingLocations);
            if (next == null) {
               next = remainingLocations.iterator().next();
            }
         }

         result[i] = next;
      }

      return create(result);
   }

   private String getUnusedAdjacentLocation(final String location, ImmutableList<String> input, Set<String> remainingLocations) {
      List<String> adjacentLocations = getAdjacentLocations(location, input);
      for (String s : adjacentLocations) {
         if (remainingLocations.contains(s)) {
            return s;
         }
      }
      return null;
   }

   private List<String> getAdjacentLocations(final String location, ImmutableList<String> input) {
      String previous = null;
      Iterator<String> itr = input.iterator();
      while (itr.hasNext()) {
         String next = itr.next();
         if (location.equals(next)) {
            List<String> result = new ArrayList<>();
            if (itr.hasNext()) {
               result.add(itr.next());
            }
            if (previous != null) {
               result.add(previous);
            }
            return result;
         }
         previous = next;
      }
      throw new IllegalStateException();
   }

   @Override
   public Chromosone<ImmutableList<String>> mutate(ImmutableList<String> original) {
      assertValid(original);

      final String[] neighbour = toArray(original);
      final int numberLocations = neighbour.length;
      int i1 = (int) Math.floor(numberLocations * randomiser.nextDouble());
      int i2 = (int) Math.floor((numberLocations - 1) * randomiser.nextDouble());
      if (i2 >= i1) {
         i2++;
      }
      swapElements(neighbour, i1, i2);
      return create(neighbour);
   }

   private String[] toArray(final ImmutableList<String> input) {
      return input.toArray(new String[input.size()]);
   }

   private void swapElements(String[] a, int e1, int e2) {
      final String t = a[e1];
      a[e1] = a[e2];
      a[e2] = t;
   }

   private ImmutableList<String> getLocationNames() {
      return locations.getLocationNames();
   }

   private Chromosone<ImmutableList<String>> create(String[] journey) {
      return create(createList(journey));
   }

   private Chromosone<ImmutableList<String>> create(ImmutableList<String> journey) {
      return new Chromosone<>(journey, calculateFitness(journey));
   }

   private double calculateFitness(ImmutableList<String> journey) {
      return -calculateJourneyCost(journey);
   }

   private int calculateJourneyCost(ImmutableList<String> journey) {
      int cost = 0;

      final Iterator<String> itr = journey.iterator();
      final String start = itr.next();
      String previous = start;
      while (itr.hasNext()) {
         String next = itr.next();
         cost += locations.getDistance(previous, next);
         previous = next;
      }
      // finish by returning to starting point
      cost += locations.getDistance(previous, start);

      return cost;
   }

   private void assertValid(ImmutableList<String> input) {
      ImmutableList<String> locationNames = getLocationNames();
      if (locationNames.size() != input.size()) {
         throw new IllegalArgumentException("Unexpected size: " + input);
      }
      if (!input.containsAll(locationNames)) {
         throw new IllegalArgumentException("Unexpected locations: " + input);
      }
   }

   private static class Parents {
      final ImmutableList<String> p1;
      final ImmutableList<String> p2;
      boolean returnFirst;

      public Parents(ImmutableList<String> p1, ImmutableList<String> p2) {
         this.p1 = p1;
         this.p2 = p2;
      }

      ImmutableList<String> getNext() {
         returnFirst = !returnFirst;
         return returnFirst ? p1 : p2;
      }
   }
}
