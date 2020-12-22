package com.example.ai.ann;

/**
 * Implements a binary step function - the output is always either 1 or 0.
 * <p>
 * <img src="doc-files/NeuralNetwork.png">
 */
public class BinaryStepFunction implements ActivationFunction {
   @Override
   public double activate(double input) {
      return input > 0 ? 1 : 0;
   }
}
