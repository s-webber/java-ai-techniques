package com.how2examples.ai.ann;

import static com.how2examples.ai.TestUtils.assertExactlyEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class NeuralNetworkFactoryTest {
   /**
    * Creates a simple neural network using {@link NeuralNetworkFactory#create(double[][][], ActivationFunction)}.
    * <p>
    * Checks layers, neurons, weights and connections are configured as expected.
    */
   @Test
   public void testCreateNeuralNetwork() {
      final double[][][] weights = new double[][][] { { {-1, -1, 1.5}, {-1, -1, 0.5}}, {{1, -1, -0.5}}};
      NeuralNetwork nn = NeuralNetworkFactory.create(weights, new BinaryStepFunction());

      final ImmutableList<Layer> layers = nn.getLayers();
      assertEquals(2, layers.size());

      final Layer inputLayer = layers.get(0);
      final Layer outputLayer = layers.get(1);
      assertEquals(2, inputLayer.getNeurons().size());
      assertEquals(1, outputLayer.getNeurons().size());

      final Neuron inputNeuron1 = inputLayer.getNeurons().get(0);
      assertNeuron(inputNeuron1, weights[0][0]);

      final Neuron inputNeuron2 = inputLayer.getNeurons().get(1);
      assertNeuron(inputNeuron2, weights[0][1]);

      final Neuron outputNeuron = outputLayer.getNeurons().get(0);
      assertNeuron(outputNeuron, weights[1][0]);

      assertConnections(outputNeuron, inputNeuron1, inputNeuron2);

      assertSame(outputLayer, nn.getOutputLayer());
   }

   private void assertNeuron(Neuron neuron, double[] weights) {
      final ImmutableList<Connection> inputs = neuron.getInputs();
      assertEquals(weights.length, inputs.size());
      for (int i = 0; i < weights.length; i++) {
         assertExactlyEquals(weights[i], inputs.get(i).getWeight());
      }

      assertEquals(1, neuron.getOutputs().size());
   }

   private void assertConnections(Neuron outputNeuron, Neuron inputNeuron1, Neuron inputNeuron2) {
      final ImmutableList<Connection> inputs = outputNeuron.getInputs();
      assertSame(inputs.get(0), inputNeuron1.getOutputs().get(0));
      assertSame(inputs.get(1), inputNeuron2.getOutputs().get(0));
   }
}
