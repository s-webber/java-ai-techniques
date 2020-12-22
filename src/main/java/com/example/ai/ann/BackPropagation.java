package com.example.ai.ann;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableList;

/**
 * Implements back propagation learning of a {@link NeuralNetwork}.
 * <p>
 * A supervised learning method used for training artificial neural networks based on the
 * "backward propagation of errors".
 * </p>
 * See: <a href="http://en.wikipedia.org/wiki/Backpropagation">Wikipedia</a>
 */
public class BackPropagation {
   private static final double LEARNING_RATE = .5; // TODO make configurable (specify in constructor?)

   public double backPropogate(final NeuralNetwork nn, final double expected, final double[] inputs) {
      final double actual = getActualOutput(nn, inputs);
      final Map<Neuron, Double> errors = backPropogateOutputLayer(nn, actual, expected);
      adjustWeights(errors);
      return actual;
   }

   private double getActualOutput(final NeuralNetwork nn, final double[] inputs) {
      double[] outputs = nn.activate(inputs);
      if (outputs.length != 1) { // TODO deal with multi output nodes
         throw new IllegalArgumentException("Currently only support a single output node");
      }
      return outputs[0];
   }

   private final Map<Neuron, Double> backPropogateOutputLayer(NeuralNetwork nn, double actual, double expected) {
      final Layer outputLayer = nn.getOutputLayer();
      final ImmutableList<Neuron> outputNeurons = outputLayer.getNeurons();
      if (outputNeurons.size() != 1) { // TODO deal with multi output nodes
         throw new IllegalArgumentException("Currently only support a single output node");
      }
      final Neuron o = outputNeurons.get(0);
      final Map<Neuron, Double> errors = new HashMap<>();
      final double error = (expected - actual) * actual * (1 - actual);
      errors.put(o, error);
      final ImmutableList<Layer> layers = nn.getLayers();
      for (int l = layers.size() - 2; l >= 0; l--) {
         backPropogateLayer(layers.get(l), errors);
      }
      return errors;
   }

   private void backPropogateLayer(Layer l, Map<Neuron, Double> errors) {
      for (Neuron n : l.getNeurons()) {
         double signal = n.getSignal();
         double errorsMultipliedByWeights = 0;
         for (Connection output : n.getOutputs()) {
            errorsMultipliedByWeights += errors.get(output.getDestination()) * output.getWeight();
         }
         double actualError = signal * (1 - signal) * errorsMultipliedByWeights;
         errors.put(n, actualError);
      }
   }

   private void adjustWeights(final Map<Neuron, Double> errors) {
      for (Map.Entry<Neuron, Double> e : errors.entrySet()) {
         final Neuron destination = e.getKey();
         double error = e.getValue();
         for (Connection o : destination.getInputs()) {
            final double change = error * o.getInput();
            o.adjustWeight(change * LEARNING_RATE);
         }
      }
   }
}
