package com.how2examples.ai.ann;

/**
 * Represents a connection between layers of a neural network.
 * <p>
 * <b>Note:</b> unlike the majority of classes in this project, this class <i>is</i> mutable. The mutation happens for
 * two reasons:
 * <ul>
 * <li>The initial construction of neural networks. Each {@code Connection} instance has references to the
 * {@code Neuron} objects it is connected to, and each {@code Neuron} instance has a reference to the {@code Connection}
 * objects it is connected to.</li>
 * <li>For the back propagation learning algorithm the connection weights are adjustable (i.e. mutable). This reduces
 * the number of objects created (especially when the number of iterations of the learning process are high).</li>
 * </ul>
 * It was felt allowing this class to be mutable made the code more straight forward to write and read (although it is
 * acknowledged that the use of mutable can introduce complexity). By using package level modifiers the scope for
 * mutation is limited to this package.
 * </p>
 * <img src="doc-files/NeuralNetwork.png">
 */
class Connection {
   private double input = 1; // set to 1 for bias to work by default
   private double weight;
   private Neuron source;
   private Neuron destination;

   public Connection(double weight) {
      this.weight = weight;
   }

   double getInput() {
      return input;
   }

   void setInput(double input) {
      this.input = input;
   }

   double getWeight() {
      return weight;
   }

   void adjustWeight(double variance) {
      this.weight += variance;
   }

   Neuron getDestination() {
      return destination;
   }

   void setDestination(Neuron destination) {
      this.destination = destination;
   }

   Neuron getSource() {
      return source;
   }

   void setSource(Neuron source) {
      this.source = source;
   }

   double getSignal() {
      return input * weight;
   }
}