package com.example.ai.ga;

import static com.example.ai.util.ImmutableListFactory.createList;
import static java.util.Collections.sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * An ordered collection of {@link Chromosone} instances.
 * <p>
 * Elements are ordered best-first based on their fitness.
 */
public class Generation<T> implements Iterable<Chromosone<T>> {
   private static final Comparator<Chromosone<?>> CHROMOSONE_COMPARATOR = new Comparator<Chromosone<?>>() {
      @Override
      public int compare(Chromosone<?> o1, Chromosone<?> o2) {
         double f1 = o1.getFitness();
         double f2 = o2.getFitness();
         if (f1 > f2) {
            return -1;
         } else if (f1 < f2) {
            return 1;
         } else {
            return 0;
         }
      }
   };

   private final ImmutableList<Chromosone<T>> orderedChromosones;
   private final long sumFitness;

   Generation(List<Chromosone<T>> chromosones) {
      this.orderedChromosones = createOrderedList(chromosones);
      this.sumFitness = getSumFitness(orderedChromosones);
   }

   /** Returns sorted copy of input */
   private static <T> ImmutableList<Chromosone<T>> createOrderedList(List<Chromosone<T>> chromosones) {
      final List<Chromosone<T>> orderedChromosones = new ArrayList<Chromosone<T>>(chromosones);
      sort(orderedChromosones, CHROMOSONE_COMPARATOR);
      return createList(orderedChromosones);
   }

   private static <T> long getSumFitness(List<Chromosone<T>> chromosones) {
      long sumFitness = 0;
      for (Chromosone<T> chromosone : chromosones) {
         sumFitness += chromosone.getFitness();
      }
      return sumFitness;
   }

   /** Returns the combined sum of fitness values for all elements in this generation. */
   public long getSumFitness() {
      return sumFitness;
   }

   /** Returns the element at the specified position in this generation. */
   public Chromosone<T> get(int index) {
      return orderedChromosones.get(index);
   }

   /** Returns the first element (i.e. the object with the highest fitness value) */
   public Chromosone<T> getFirst() {
      return get(0);
   }

   /** Returns the number of elements in this generation. */
   public int size() {
      return orderedChromosones.size();
   }

   @Override
   public Iterator<Chromosone<T>> iterator() {
      return orderedChromosones.iterator();
   }
}
