package com.example.ai.ga;

/**
 * Uses an iterative process to select the best solution.
 * <p>
 * <img src="doc-files/GeneticAlgorithm.png">
 */
public class GeneticAlgorithmController {
   public static <T> T process(GenerationFactory<T> generationFactory, int maxNumberOfGenerations) {
      Generation<T> currentGeneration = generationFactory.createInitialGeneration();
      for (int i = 0; i < maxNumberOfGenerations; i++) {
         currentGeneration = generationFactory.generateNewGeneration(currentGeneration);
      }
      return currentGeneration.getFirst().getValue();
   }
}
