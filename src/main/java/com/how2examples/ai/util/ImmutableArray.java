package com.how2examples.ai.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * An ordered collection that cannot be modified.
 * <p>
 * Elements cannot be added or removed.
 * </p>
 * <p>
 * <b>Note:</b> this class enforces no restrictions over whether the elements stored are themselves immutable.
 * </p>
 */
public class ImmutableArray<E> implements Iterable<E> {
   @SuppressWarnings("rawtypes")
   public static final ImmutableArray EMPTY_IMMUTABLE_ARRAY = new ImmutableArray<Object>(new Object[0]);

   private final List<E> elements;

   public ImmutableArray(final E[] elements) {
      this.elements = Arrays.asList(elements);
   }

   public ImmutableArray(final Collection<E> elements) {
      this.elements = new ArrayList<>(elements);
   }

   public E get(final int idx) {
      return elements.get(idx);
   }

   public int size() {
      return elements.size();
   }

   public List<E> asList() {
      return Collections.unmodifiableList(elements);
   }

   @Override
   public String toString() {
      return elements.toString();
   }

   @Override
   public Iterator<E> iterator() {
      return new ImmutableIterator();
   }

   // TODO: "ctr" not synchronised
   private class ImmutableIterator implements Iterator<E> {
      private int ctr = 0;

      @Override
      public boolean hasNext() {
         return ctr < elements.size();
      }

      @Override
      public E next() {
         return elements.get(ctr++);
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}
