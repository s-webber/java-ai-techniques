package com.how2examples.ai.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import com.google.common.collect.ImmutableList;

public class ImmutableListFactoryTest extends TestCase {
   private static final String FIRST = "abc";
   private static final String SECOND = "xyz";
   private static final String THIRD = "qwe";

   public void testEmptyList() {
      assertSame(ImmutableListFactory.emptyList(), ImmutableListFactory.emptyList());
      assertEquals(ImmutableListFactory.emptyList().size(), 0);
   }

   public void testCreateFromList() {
      final List<String> input = Arrays.asList(FIRST, SECOND, THIRD);
      final ImmutableList<String> output = ImmutableListFactory.createList(input);
      assertListContents(output);
      assertListContents(input); // check input isn't altered
   }

   public void testCreateFromArray() {
      final String[] input = {FIRST, SECOND, THIRD};
      final ImmutableList<String> output = ImmutableListFactory.createList(input);
      assertListContents(output);

      // check input isn't altered
      assertSame(FIRST, input[0]);
      assertSame(SECOND, input[1]);
      assertSame(THIRD, input[2]);
   }

   /** Test that have properly understood behaviour of Guava's ImmutableList. */
   public void testImmutable() {
      final List<String> input = Arrays.asList(FIRST, SECOND, THIRD);
      final ImmutableList<String> output = ImmutableListFactory.createList(input);

      // assert changes to original not reflected in copy
      assertSame(SECOND, output.get(1));
      input.set(1, "modified");
      assertSame(SECOND, output.get(1));

      // test can't add to output
      try {
         output.add("hkj");
         fail();
      } catch (UnsupportedOperationException e) {
         // expected
      }

      // test can't modify output
      try {
         output.add(1, "hkj");
         fail();
      } catch (UnsupportedOperationException e) {
         // expected
      }

      // test iterator - including that can't remove elements
      Iterator<String> itr = output.iterator();
      assertSame(FIRST, itr.next());
      try {
         itr.remove();
         fail();
      } catch (UnsupportedOperationException e) {
         // expected
      }
   }

   private void assertListContents(final List<String> output) {
      assertEquals(3, output.size());
      assertSame(FIRST, output.get(0));
      assertSame(SECOND, output.get(1));
      assertSame(THIRD, output.get(2));
   }
}
