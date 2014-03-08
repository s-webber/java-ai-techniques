package com.how2examples.ai.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * An implementation of {@code Iterator} that does not provide a way to modify the underlying collection.
 */
public class UnmodifiableIterator<T> implements Iterator<T> {
   private final Iterator<T> itr;

   public UnmodifiableIterator(Collection<T> c) {
      itr = c.iterator();
   }

   @Override
   public boolean hasNext() {
      return itr.hasNext();
   }

   @Override
   public T next() {
      return itr.next();
   }

   /**
    * Not supported.
    * 
    * @throws UnsupportedOperationException
    */
   @Override
   public void remove() {
      throw new UnsupportedOperationException();
   }
}
