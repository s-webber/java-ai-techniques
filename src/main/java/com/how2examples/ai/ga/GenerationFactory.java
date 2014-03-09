package com.how2examples.ai.ga;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

/**
 * Creates new {@link Generation} instances.
 * <p>
 * <img src="doc-files/GeneticAlgorithm.png">
 */
public class GenerationFactory<T> {
   private final GeneticAlgorithmConfiguration config;
   private final ChromosoneFactory<T> chromosoneFactory;
   private final SelectionStrategy<T> selectionStrategy;

   public GenerationFactory(GeneticAlgorithmConfiguration config, ChromosoneFactory<T> chromosoneFactory, SelectionStrategy<T> selectionStrategy) {
      this.chromosoneFactory = chromosoneFactory;
      this.config = config;
      this.selectionStrategy = selectionStrategy;
   }

   /**
    * Creates a new generation.
    * <p>
    * The size of the generation and the way the elements are created will be determined by the
    * {@link GeneticAlgorithmConfiguration} and {@link ChromosoneFactory} instances provided in the constructor.
    * 
    * @return the newly created generation
    */
   public Generation<T> createInitialGeneration() {
      final List<Chromosone<T>> initialGeneration = new ArrayList<Chromosone<T>>();
      for (int i = 0; i < config.getGenerationSize(); i++) {
         final Chromosone<T> chromosone = chromosoneFactory.create();
         initialGeneration.add(chromosone);
      }
      return new Generation<>(initialGeneration);
   }

   /**
    * Creates a new generation based on the specified existing generation.
    * <p>
    * The way the new generation is created will be determined by the {@link GeneticAlgorithmConfiguration} and
    * {@link ChromosoneFactory} instances provided in the constructor.
    * 
    * @param previousGeneration the existing generation to select from when choosing candidates for the new generation
    * @return the newly created generation
    */
   public Generation<T> generateNewGeneration(final Generation<T> previousGeneration) {
      final int requiredGenerationSize = config.getGenerationSize();
      final List<Chromosone<T>> newGeneration = new ArrayList<Chromosone<T>>(requiredGenerationSize);

      final int numberToCreate = config.getNumberOfNewCandiatesToCreate();
      final int numberToMutate = config.getNumberOfNewCandiatesToMutate();
      final int numberToCrossover = config.getNumberOfNewCandiatesToCrossover();

      final int numberOfCandidates = numberToMutate + (numberToCrossover * 2);
      final Queue<T> candidates = getSelection(previousGeneration, numberOfCandidates);
      for (int i = 0; i < numberToCreate; i++) {
         newGeneration.add(chromosoneFactory.create());
      }
      for (int i = 0; i < numberToMutate; i++) {
         newGeneration.add(chromosoneFactory.mutate(candidates.poll()));
      }
      for (int i = 0; i < numberToCrossover; i++) {
         newGeneration.add(chromosoneFactory.crossover(candidates.poll(), candidates.poll()));
      }

      // populate remainder of new generation with best candidates from previous generation
      Iterator<Chromosone<T>> itr = previousGeneration.iterator();
      while (newGeneration.size() < config.getGenerationSize()) {
         newGeneration.add(itr.next());
      }

      return new Generation<>(newGeneration);
   }

   private Queue<T> getSelection(Generation<T> generation, int numberToSelect) {
      return selectionStrategy.selectCandidates(generation, numberToSelect);
   }
}
