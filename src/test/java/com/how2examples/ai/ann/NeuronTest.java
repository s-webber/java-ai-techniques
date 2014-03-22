package com.how2examples.ai.ann;

import static com.how2examples.ai.TestUtils.assertExactlyEquals;
import static com.how2examples.ai.util.ImmutableListFactory.createList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class NeuronTest {
   @Test
   public void test() {
      final Connection[] inputs = {new Connection(1), new Connection(-2)};
      final Connection[] outputs = {new Connection(1), new Connection(0.5)};
      final ActivationFunction activationFunction = new BinaryStepFunction();
      final Neuron neuron = new Neuron(createList(inputs), createList(outputs), activationFunction);

      neuron.activate();
      assertOutputs(neuron, 0, 0);

      setInputs(neuron, 10, -5.5);
      neuron.activate();
      assertOutputs(neuron, 1, 1);
   }

   private void setInputs(final Neuron neuron, final double... inputValues) {
      final ImmutableList<Connection> inputConnections = neuron.getInputs();
      assertEquals(inputConnections.size(), inputValues.length);
      for (int i = 0; i < inputValues.length; i++) {
         inputConnections.get(i).setInput(inputValues[i]);
      }
   }

   private void assertOutputs(final Neuron neuron, final double... expectedValues) {
      final ImmutableList<Connection> outputConnections = neuron.getOutputs();
      assertEquals(outputConnections.size(), expectedValues.length);
      for (int i = 0; i < expectedValues.length; i++) {
         assertExactlyEquals(outputConnections.get(i).getInput(), expectedValues[i]);
      }
   }
}
