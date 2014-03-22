package com.how2examples.ai.ann;

import com.google.common.collect.ImmutableList;

/**
 * A layer of a neural network.
 * <p>
 * A neural network can consist of multiple layers. A layer consists of neurons that are connected to the previous and
 * next layers.
 * <p>
 * <img src="doc-files/NeuralNetwork.png">
 */
class Layer {
   private final ImmutableList<Neuron> neurons;

   Layer(ImmutableList<Neuron> neurons) {
      this.neurons = neurons;
   }

   ImmutableList<Neuron> getNeurons() {
      return neurons;
   }

   void activate() {
      for (Neuron neuron : neurons) {
         neuron.activate();
      }
   }
}
