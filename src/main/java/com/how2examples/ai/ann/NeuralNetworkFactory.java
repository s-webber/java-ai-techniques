package com.how2examples.ai.ann;

import static com.how2examples.ai.util.ImmutableListFactory.createList;

import java.util.ArrayList;
import java.util.List;

/**
 * Constructs new instances of {@link NeuralNetwork}.
 */
public class NeuralNetworkFactory {
   /**
    * @param weights [layers][neurons in layer][weights]
    */
   public static NeuralNetwork create(double[][][] weights, ActivationFunction function) {
      final Connection[][][] connections = toConnections(weights);
      final int numLayers = weights.length;
      final List<Layer> layers = new ArrayList<>(numLayers);
      for (int l = 0; l < numLayers; l++) {
         final int numNeuronsInLayer = weights[l].length;
         final List<Neuron> neurons = new ArrayList<>(numNeuronsInLayer);
         for (int i = 0; i < numNeuronsInLayer; i++) {
            final Neuron n = createNeuron(l, i, connections, function);
            neurons.add(n);
         }
         final Layer layer = new Layer(createList(neurons));
         layers.add(layer);
      }
      return new NeuralNetwork(createList(layers));
   }

   private static Connection[][][] toConnections(double[][][] weights) {
      Connection[][][] connections = new Connection[weights.length][][];
      for (int x = 0; x < weights.length; x++) {
         connections[x] = new Connection[weights[x].length][];
         for (int y = 0; y < weights[x].length; y++) {
            connections[x][y] = new Connection[weights[x][y].length];
            for (int z = 0; z < weights[x][y].length; z++) {
               connections[x][y][z] = new Connection(weights[x][y][z]);
            }
         }
      }
      return connections;
   }

   private static Neuron createNeuron(int layer, int index, Connection[][][] weights, ActivationFunction function) {
      Connection[] inputs = weights[layer][index];
      Connection[] outputs;
      if (weights.length == layer + 1) {
         outputs = new Connection[] {new Connection(1.0)};
      } else {
         outputs = new Connection[weights[layer + 1].length];
         for (int i = 0; i < outputs.length; i++) {
            outputs[i] = weights[layer + 1][i][index];
         }
      }
      return new Neuron(createList(inputs), createList(outputs), function);
   }
}
