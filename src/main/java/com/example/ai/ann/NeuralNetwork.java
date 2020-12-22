package com.example.ai.ann;

import com.google.common.collect.ImmutableList;

/**
 * Represents a artificial neural network.
 * <p>
 * <img src="doc-files/NeuralNetwork.png">
 * </p>
 * See: <a href="http://en.wikipedia.org/wiki/Artificial_neural_networks">Wikipedia</a>
 */
public class NeuralNetwork {
   private final ImmutableList<Layer> layers;

   NeuralNetwork(ImmutableList<Layer> layers) {
      this.layers = layers;
   }

   public double[] activate(double... inputs) {
      validateArgument(inputs);

      setInputs(inputs);

      activateLayers();

      return getOutputSignals();
   }

   private void validateArgument(final double[] inputs) {
      if (inputs.length != getInputLayerNeurons().size()) {
         throw new IllegalArgumentException();
      }
   }

   private void setInputs(double... inputs) {
      for (Neuron neuron : getInputLayerNeurons()) {
         for (int i = 0; i < inputs.length; i++) {
            neuron.getInputs().get(i).setInput(inputs[i]);
         }
      }
   }

   private void activateLayers() {
      for (Layer layer : layers) {
         layer.activate();
      }
   }

   private double[] getOutputSignals() {
      final ImmutableList<Neuron> outputLayerNeurons = getOutputLayerNeurons();
      final int numOutputs = outputLayerNeurons.size();
      final double[] outputSignals = new double[numOutputs];
      for (int i = 0; i < numOutputs; i++) {
         final Neuron outputNeuron = outputLayerNeurons.get(i);
         outputSignals[i] = getSignal(outputNeuron);
      }
      return outputSignals;
   }

   private double getSignal(final Neuron outputNeuron) {
      ImmutableList<Connection> outputs = outputNeuron.getOutputs();
      if (outputs.size() != 1) {
         new IllegalArgumentException("Expected neuron to have 1 output but has: " + outputs.size());
      }
      return outputs.get(0).getSignal();
   }

   private ImmutableList<Neuron> getInputLayerNeurons() {
      return getInputLayer().getNeurons();
   }

   private Layer getInputLayer() {
      return layers.get(0);
   }

   ImmutableList<Neuron> getOutputLayerNeurons() {
      return getOutputLayer().getNeurons();
   }

   Layer getOutputLayer() {
      return layers.get(layers.size() - 1);
   }

   ImmutableList<Layer> getLayers() {
      return layers;
   }
}