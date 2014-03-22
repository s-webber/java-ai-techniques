package com.how2examples.ai.ann;

import com.google.common.collect.ImmutableList;

/**
 * Represents an artificial neuron.
 * <p>
 * Neurons are the basic units used to construct an neural network. A neuron passes the sum of one or more inputs
 * through an activation function to produce an output.
 * <p>
 * <img src="doc-files/NeuralNetwork.png">
 */
class Neuron {
   private final ImmutableList<Connection> inputs;
   private final ImmutableList<Connection> outputs;
   private final ActivationFunction activationFunction;

   Neuron(ImmutableList<Connection> inputs, ImmutableList<Connection> outputs, ActivationFunction activationFunction) {
      this.inputs = inputs;
      this.outputs = outputs;
      this.activationFunction = activationFunction;
      for (Connection c : inputs) {
         c.setDestination(this);
      }
      for (Connection c : outputs) {
         c.setSource(this);
      }
   }

   ImmutableList<Connection> getInputs() {
      return inputs;
   }

   ImmutableList<Connection> getOutputs() {
      return outputs;
   }

   double getSignal() {
      final double inputSignal = sumInputs();
      return activationFunction.activate(inputSignal);
   }

   void activate() {
      final double outputSignal = getSignal();
      updateOutputs(outputSignal);
   }

   private double sumInputs() {
      double total = 0;
      for (Connection input : inputs) {
         total += input.getSignal();
      }
      return total;
   }

   private void updateOutputs(double outputSignal) {
      for (Connection output : outputs) {
         output.setInput(outputSignal);
      }
   }
}