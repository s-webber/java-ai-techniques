package com.how2examples.ai.ga;

import static java.util.Arrays.asList;

import java.util.Iterator;

import junit.framework.TestCase;

public class GenerationTest extends TestCase {
   private final Chromosone<Object> CHROMOSONE_A = new Chromosone<Object>(new Object(), -1);
   private final Chromosone<Object> CHROMOSONE_B = new Chromosone<Object>(new Object(), 100);
   private final Chromosone<Object> CHROMOSONE_C = new Chromosone<Object>(new Object(), 9);

   private final Generation<Object> GENERATION = new Generation<>(asList(CHROMOSONE_A, CHROMOSONE_B, CHROMOSONE_C));

   public void testGetFirst() {
      assertSame(CHROMOSONE_B, GENERATION.getFirst());
   }

   public void testGet() {
      assertSame(CHROMOSONE_B, GENERATION.get(0));
      assertSame(CHROMOSONE_C, GENERATION.get(1));
      assertSame(CHROMOSONE_A, GENERATION.get(2));
   }

   public void testSumFitness() {
      assertEquals(108, GENERATION.getSumFitness());
   }

   public void testIterator() {
      Iterator<Chromosone<Object>> itr = GENERATION.iterator();
      assertTrue(itr.hasNext());
      assertSame(CHROMOSONE_B, itr.next());
      assertTrue(itr.hasNext());
      assertSame(CHROMOSONE_C, itr.next());
      assertTrue(itr.hasNext());
      assertSame(CHROMOSONE_A, itr.next());
      assertFalse(itr.hasNext());
   }

   public void testSize() {
      assertEquals(3, GENERATION.size());
   }
}
