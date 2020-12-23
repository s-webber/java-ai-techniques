package com.example.ai.ann;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BackPropagationTest {
   private static final int MAX_ITERATIONS = 500;
   private static final double ACCEPTABLE_DIFFERENCE = 0.01;

   @Test
   public void testBackPropagation1() {
      assertBackPropagation(new double[] {.1, .9}, .5);
   }

   @Test
   public void testBackPropagation2() {
      assertBackPropagation(new double[] {.9, .1}, .5);
   }

   @Test
   public void testBackPropagation3() {
      assertBackPropagation(new double[] {.7, .2}, .5);
   }

   @Test
   public void testBackPropagatio4() {
      assertBackPropagation(new double[] {.7, -.2}, .5);
   }

   @Test
   public void testBackPropagation5() {
      assertBackPropagation(new double[] {-.7, .2}, .5);
   }

   @Test
   public void testBackPropagation6() {
      assertBackPropagation(new double[] {-.7, -.2}, .5);
   }

   @Test
   public void testBackPropagation7() {
      assertBackPropagation(new double[] {.7, .2}, .1);
   }

   @Test
   public void testBackPropagation8() {
      assertBackPropagation(new double[] {-.7, .2}, .9);
   }

   /**
    * Attempts to train a neural network to return the specified expected result given the specified inputs.
    * <p>
    * If the neural network cannot be trained to get within {@link ACCEPTABLE_DIFFERENCE} of the expected value within
    * {@link MAX_ITERATIONS} iterations then the assertion fails.
    */
   private void assertBackPropagation(final double[] inputs, final double expectedOutput) {
      final NeuralNetwork nn = createBackPropagationNeuralNetwork();
      final BackPropagation bp = new BackPropagation();
      double actual = 0;

      // go through multiple iterations of training
      for (int i = 0; i < MAX_ITERATIONS; i++) {
         actual = bp.backPropogate(nn, expectedOutput, inputs);

         // if within acceptable range then success (so can exit loop early)
         if (withinRange(expectedOutput, actual)) {
            return;
         }
      }

      // if haven't got within acceptable range of expected value then fail
      assertTrue(withinRange(expectedOutput, actual));
   }

   private boolean withinRange(final double expected, double previous) {
      return (previous > expected - ACCEPTABLE_DIFFERENCE) && (previous < expected + ACCEPTABLE_DIFFERENCE);
   }

   private static NeuralNetwork createBackPropagationNeuralNetwork() {
      final double[][][] weights = {
      /** first */
      { {-2, -2, 2}, {3, 3, 2}},
      /** second */
      { {-2, -4, 3}, {2, 2, -2}},
      /** third */
      {{3, 1, -2}}};
      return NeuralNetworkFactory.create(weights, new LogisticFunction());
   }
}
