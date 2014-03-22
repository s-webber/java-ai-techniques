package com.how2examples.ai.ga;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

public class GenerationFactoryTest {
   private final Mockery context = new Mockery();
   @SuppressWarnings("unchecked")
   private final ChromosoneFactory<Object> chromosoneFactory = context.mock(ChromosoneFactory.class);
   @SuppressWarnings("unchecked")
   private final SelectionStrategy<Object> selectionStrategy = context.mock(SelectionStrategy.class);

   @Test
   public void testCreateInitialGeneration() {
      final int generationSize = 10;
      final GeneticAlgorithmConfiguration config = new GeneticAlgorithmConfiguration(generationSize, .25, .25, .25);
      final List<Chromosone<Object>> expected = createChromosones(generationSize);

      for (int i = 0; i < generationSize; i++) {
         expectCreate(expected.get(i));
      }

      final GenerationFactory<Object> generationCreator = createGenerationCreator(config);
      final Generation<Object> actual = generationCreator.createInitialGeneration();
      assertGeneration(expected, actual);
   }

   @Test
   public void testGenerateNewGeneration() {
      final int generationSize = 10;
      final GeneticAlgorithmConfiguration config = new GeneticAlgorithmConfiguration(generationSize, .2, .1, .3);
      final Generation<Object> previousGeneration = createGeneration(config);
      final List<Chromosone<Object>> previousGenerationCandidates = createChromosones(7);
      final List<Chromosone<Object>> newGenerationCandidates = createChromosones(6);
      final Iterator<Chromosone<Object>> previousItr = previousGenerationCandidates.iterator();
      final Iterator<Chromosone<Object>> newItr = newGenerationCandidates.iterator();

      expectSelection(previousGeneration, previousGenerationCandidates);

      expectCreate(newItr.next());
      expectCreate(newItr.next());

      expectMutate(previousItr.next(), newItr.next());

      expectCrossover(previousItr.next(), previousItr.next(), newItr.next());
      expectCrossover(previousItr.next(), previousItr.next(), newItr.next());
      expectCrossover(previousItr.next(), previousItr.next(), newItr.next());

      final GenerationFactory<Object> generationCreator = createGenerationCreator(config);
      final Generation<Object> actual = generationCreator.generateNewGeneration(previousGeneration);
      final List<Chromosone<Object>> expected = new ArrayList<>();
      expected.addAll(newGenerationCandidates);
      expected.add(previousGeneration.get(0));
      expected.add(previousGeneration.get(1));
      expected.add(previousGeneration.get(2));
      expected.add(previousGeneration.get(3));
      assertGeneration(expected, actual);
   }

   private void expectSelection(final Generation<Object> previousGeneration, final List<Chromosone<Object>> previousGenerationCandidates) {
      final int numberToSelect = previousGenerationCandidates.size();
      context.checking(new Expectations() {
         {

            oneOf(selectionStrategy).selectCandidates(previousGeneration, numberToSelect);
            will(returnValue(new LinkedList<>(previousGenerationCandidates)));
         }
      });
   }

   private void expectCreate(final Chromosone<Object> newValue) {
      context.checking(new Expectations() {
         {
            oneOf(chromosoneFactory).create();
            will(returnValue(newValue));
         }
      });
   }

   private void expectMutate(final Chromosone<Object> from, final Chromosone<Object> to) {
      context.checking(new Expectations() {
         {
            oneOf(chromosoneFactory).mutate(from);
            will(returnValue(to));
         }
      });
   }

   private void expectCrossover(final Chromosone<Object> parent1, final Chromosone<Object> parent2, final Chromosone<Object> to) {
      context.checking(new Expectations() {
         {
            oneOf(chromosoneFactory).crossover(parent1, parent2);
            will(returnValue(to));
         }
      });
   }

   private GenerationFactory<Object> createGenerationCreator(final GeneticAlgorithmConfiguration config) {
      return new GenerationFactory<>(config, chromosoneFactory, selectionStrategy);
   }

   private Generation<Object> createGeneration(GeneticAlgorithmConfiguration config) {
      return new Generation<>(createChromosones(config.getGenerationSize()));
   }

   private List<Chromosone<Object>> createChromosones(int generationSize) {
      List<Chromosone<Object>> result = new ArrayList<>();
      for (int i = 0; i < generationSize; i++) {
         result.add(new Chromosone<Object>(new Object(), 0));
      }
      return result;
   }

   private void assertGeneration(final List<Chromosone<Object>> expected, final Generation<Object> actual) {
      assertEquals(expected.size(), actual.size());
      for (int i = 0; i < expected.size(); i++) {
         assertEquals(expected.get(i), actual.get(i));
      }
   }
}
