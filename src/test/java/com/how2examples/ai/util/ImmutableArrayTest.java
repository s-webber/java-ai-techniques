package com.how2examples.ai.util;

import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

public class ImmutableArrayTest extends TestCase {
   private static final String FIRST = "first";
   private static final String SECOND = "second";
   private static final String THIRD = "third";
   private static final ImmutableArray<String> TEST_OBJECT = new ImmutableArray<String>(new String[] {FIRST, SECOND, THIRD});

   public void testEmptyImmutableArray() {
      assertEquals(0, ImmutableArray.EMPTY_IMMUTABLE_ARRAY.size());
   }

   public void testSize() {
      assertEquals(3, TEST_OBJECT.size());
   }

   public void testToString() {
      assertEquals("[first, second, third]", TEST_OBJECT.toString());
   }

   public void testAsListMatchesOriginal() {
      final List<String> list = TEST_OBJECT.asList();
      assertEquals(TEST_OBJECT.size(), list.size());
      for (int i = 0; i < TEST_OBJECT.size(); i++) {
         assertSame(TEST_OBJECT.get(i), list.get(i));
      }
   }

   public void testAsListUnmodifiable() {
      final List<String> list = TEST_OBJECT.asList();
      try {
         list.add("test");
         fail();
      } catch (UnsupportedOperationException e) {
         // expected
      }
   }

   public void testIterator() {
      final Iterator<String> itr = TEST_OBJECT.iterator();
      assertTrue(itr.hasNext());
      assertSame(FIRST, itr.next());
      assertTrue(itr.hasNext());
      assertSame(SECOND, itr.next());
      assertTrue(itr.hasNext());
      assertSame(THIRD, itr.next());
      assertFalse(itr.hasNext());
   }

   public void testIteratorThrowsUnsupportedOperationException() {
      final Iterator<String> itr = TEST_OBJECT.iterator();
      try {
         itr.remove();
         fail();
      } catch (UnsupportedOperationException e) {
         // expected
      }
   }

   public void testGet() {
      assertSame(FIRST, TEST_OBJECT.get(0));
      assertSame(SECOND, TEST_OBJECT.get(1));
      assertSame(THIRD, TEST_OBJECT.get(2));
   }

   public void testGetThrowsIndexOutOfBoundsException() {
      assertIndexOutOfBoundsException(-1);
      assertIndexOutOfBoundsException(4);
   }

   private void assertIndexOutOfBoundsException(final int idx) {
      try {
         TEST_OBJECT.get(idx);
         fail();
      } catch (IndexOutOfBoundsException e) {
         // expected
      }
   }
}
