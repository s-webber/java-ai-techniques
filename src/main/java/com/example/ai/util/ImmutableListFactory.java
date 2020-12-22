package com.example.ai.util;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public class ImmutableListFactory {
   private static final ImmutableList<Object> EMPTY_LIST = new Builder<>().build();

   @SuppressWarnings("unchecked")
   public static <E> ImmutableList<E> emptyList() {
      return (ImmutableList<E>) EMPTY_LIST;
   }

   @SafeVarargs
   public static <E> ImmutableList<E> createList(E... input) {
      final Builder<E> builder = new Builder<>();
      builder.add(input);
      return builder.build();
   }

   public static <E> ImmutableList<E> createList(List<E> input) {
      final Builder<E> builder = new Builder<>();
      builder.addAll(input.iterator());
      return builder.build();
   }
}
