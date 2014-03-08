package com.how2examples.ai.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

   /**
    * Returns a new array containing all the elements of this instance.
    * <p>
    * The caller is free to modify the returned array.
    */
   @SuppressWarnings("unchecked")
   public E[] toArray() {
      return (E[]) elements.toArray();
   }

   @Override
   public String toString() {
      return elements.toString();
   }

   @Override
   public Iterator<E> iterator() {
      return new UnmodifiableIterator<>(elements);
   }
}
