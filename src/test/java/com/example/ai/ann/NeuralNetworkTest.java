package com.example.ai.ann;

import static com.example.ai.TestUtils.assertExactlyEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NeuralNetworkTest {
   @Test
   public void testXorTrueTrue() {
      assertXor(1, 1, false);
   }

   @Test
   public void testXorTrueFalse() {
      assertXor(1, 0, true);
   }

   @Test
   public void testXorFalseTrue() {
      assertXor(0, 1, true);
   }

   @Test
   public void testXorFalseFalse() {
      assertXor(0, 0, false);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testTooFewInputs() {
      NeuralNetwork nn = createXorNeuralNetwork();
      nn.activate(new double[] {1}); // specify 1 input instead of the required 2
   }

   @Test(expected = IllegalArgumentException.class)
   public void testTooManyInputs() {
      NeuralNetwork nn = createXorNeuralNetwork();
      nn.activate(new double[] {1, 1, 1}); // specify 3 inputs instead of the required 2
   }

   private void assertXor(double input1, double input2, boolean expected) {
      final NeuralNetwork nn = createXorNeuralNetwork();
      assertActivate(nn, new double[] {input1, input2}, expected ? 1 : 0);
   }

   private NeuralNetwork createXorNeuralNetwork() {
      final double[][][] weights = { { {-1, -1, 1.5}, {-1, -1, 0.5}}, {{1, -1, -0.5}}};
      return NeuralNetworkFactory.create(weights, new BinaryStepFunction());
   }

   private void assertActivate(final NeuralNetwork nn, final double[] input, final double expectedOutput) {
      final double[] actualOutput = nn.activate(input);
      assertEquals(1, actualOutput.length);
      assertExactlyEquals(expectedOutput, actualOutput[0]);
   }
}
