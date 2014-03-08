package com.how2examples.ai.util;

import static java.util.Arrays.asList;

import java.util.Iterator;

import junit.framework.TestCase;

public class UnmodifiableIteratorTest extends TestCase {
   public void testNormalOperation() {
      final Object o1 = new Object();
      final Object o2 = new Object();
      final Object o3 = new Object();
      final Iterator<Object> itr = new UnmodifiableIterator<>(asList(o1, o2, o3));
      assertTrue(itr.hasNext());
      assertSame(o1, itr.next());
      assertTrue(itr.hasNext());
      assertSame(o2, itr.next());
      assertTrue(itr.hasNext());
      assertSame(o3, itr.next());
      assertFalse(itr.hasNext());
   }

   public void testRemoveThrowsUnsupportedOperationException() {
      final Iterator<String> itr = new UnmodifiableIterator<>(asList("a", "b", "c"));
      itr.next();
      try {
         itr.remove();
         fail();
      } catch (UnsupportedOperationException e) {
         // expected
      }
   }
}
