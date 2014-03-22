package com.how2examples.ai.ann;

/**
 * Implements a logistic function (a common sigmoid function).
 * <p>
 * <img src="doc-files/NeuralNetwork.png">
 * </p>
 * See: <a href="http://en.wikipedia.org/wiki/Logistic_function">Wikipedia</a>
 */
public class LogisticFunction implements ActivationFunction {
   @Override
   public double activate(double input) {
      return 1 / (1 + Math.exp(-input));
   }
}
