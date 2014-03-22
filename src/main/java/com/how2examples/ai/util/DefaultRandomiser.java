package com.how2examples.ai.util;

import static com.how2examples.ai.util.ImmutableListFactory.createList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;

/**
 * Implementation of {@link Randomiser} which uses {@code java.util.Random}.
 */
public class DefaultRandomiser implements Randomiser {
   private final Random randomNumberGenerator;

   public DefaultRandomiser() {
      randomNumberGenerator = new Random();
   }

   public DefaultRandomiser(final long seed) {
      randomNumberGenerator = new Random(seed);
   }

   @Override
   public double getDouble(double minInclusive, double maxInclusive) {
      return (nextDouble() * (maxInclusive - minInclusive)) + minInclusive;
   }

   @Override
   public double nextDouble() {
      return randomNumberGenerator.nextDouble();
   }

   @Override
   public <T> ImmutableList<T> shuffle(ImmutableList<T> locationNames) {
      List<T> locationNamesMuttableList = new ArrayList<>(locationNames);
      Collections.shuffle(locationNamesMuttableList);
      return createList(locationNamesMuttableList);
   }
}
