package com.example.ai.ga;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GeneticAlgorithmConfigurationTest {
   @Test
   public void testBigGeneration() {
      GeneticAlgorithmConfiguration config = new GeneticAlgorithmConfiguration(5000, .125, .3, .05);
      assertEquals(5000, config.getGenerationSize());
      assertEquals(625, config.getNumberOfNewCandiatesToCreate());
      assertEquals(1500, config.getNumberOfNewCandiatesToMutate());
      assertEquals(250, config.getNumberOfNewCandiatesToCrossover());
   }

   @Test
   public void testSmallGeneration() {
      GeneticAlgorithmConfiguration config = new GeneticAlgorithmConfiguration(10, .2, .4, .3);
      assertEquals(10, config.getGenerationSize());
      assertEquals(2, config.getNumberOfNewCandiatesToCreate());
      assertEquals(4, config.getNumberOfNewCandiatesToMutate());
      assertEquals(3, config.getNumberOfNewCandiatesToCrossover());
   }

   @Test
   public void testSumOfRatiosEqualsOne() {
      GeneticAlgorithmConfiguration config = new GeneticAlgorithmConfiguration(10, .2, .5, .3);
      assertEquals(10, config.getGenerationSize());
      assertEquals(2, config.getNumberOfNewCandiatesToCreate());
      assertEquals(5, config.getNumberOfNewCandiatesToMutate());
      assertEquals(3, config.getNumberOfNewCandiatesToCrossover());
   }

   @Test
   public void testSumOfRatiosGreaterThanOne() {
      try {
         new GeneticAlgorithmConfiguration(10, .2, .5, .31);
      } catch (IllegalArgumentException e) {
         assertEquals("sum of ratios >1: 1.01", e.getMessage());
      }
   }

   @Test
   public void testNegativeGenerationSize() {
      try {
         new GeneticAlgorithmConfiguration(-1, .2, .5, .3);
      } catch (IllegalArgumentException e) {
         assertEquals("generation size <1: -1", e.getMessage());
      }
   }

   @Test
   public void testNegativeFirstRatio() {
      try {
         new GeneticAlgorithmConfiguration(10, -.01, .5, .3);
      } catch (IllegalArgumentException e) {
         assertEquals("ratio <0: -0.01", e.getMessage());
      }
   }

   @Test
   public void testNegativeSecondRatio() {
      try {
         new GeneticAlgorithmConfiguration(10, .2, -.1, .3);
      } catch (IllegalArgumentException e) {
         assertEquals("ratio <0: -0.1", e.getMessage());
      }
   }

   @Test
   public void testNegativeThirdRatio() {
      try {
         new GeneticAlgorithmConfiguration(10, .2, .5, -100);
      } catch (IllegalArgumentException e) {
         assertEquals("ratio <0: -100.0", e.getMessage());
      }
   }

   @Test
   public void testZeroGenerationSize() {
      try {
         new GeneticAlgorithmConfiguration(0, .2, .5, .3);
      } catch (IllegalArgumentException e) {
         assertEquals("generation size <1: 0", e.getMessage());
      }
   }
}