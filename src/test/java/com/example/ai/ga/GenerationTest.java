package com.example.ai.ga;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

public class GenerationTest {
   private final Chromosone<Object> CHROMOSONE_A = new Chromosone<Object>(new Object(), -1);
   private final Chromosone<Object> CHROMOSONE_B = new Chromosone<Object>(new Object(), 100);
   private final Chromosone<Object> CHROMOSONE_C = new Chromosone<Object>(new Object(), 9);

   private final Generation<Object> GENERATION = new Generation<>(asList(CHROMOSONE_A, CHROMOSONE_B, CHROMOSONE_C));

   @Test
   public void testGetFirst() {
      assertSame(CHROMOSONE_B, GENERATION.getFirst());
   }

   @Test
   public void testGet() {
      assertSame(CHROMOSONE_B, GENERATION.get(0));
      assertSame(CHROMOSONE_C, GENERATION.get(1));
      assertSame(CHROMOSONE_A, GENERATION.get(2));
   }

   @Test
   public void testSumFitness() {
      assertEquals(108, GENERATION.getSumFitness());
   }

   @Test
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

   @Test
   public void testSize() {
      assertEquals(3, GENERATION.size());
   }
}
