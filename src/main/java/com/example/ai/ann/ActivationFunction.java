package com.example.ai.ann;

/**
 * Represents the activation function of a neuron.
 * <p>
 * An activation function converts a neuron's weighted input to its output value.
 * </p>
 * <img src="doc-files/NeuralNetwork.png">
 */
public interface ActivationFunction {
   double activate(double input);
}