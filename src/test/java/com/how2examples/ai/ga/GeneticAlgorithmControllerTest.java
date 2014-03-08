package com.how2examples.ai.ga;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

public class GeneticAlgorithmControllerTest extends TestCase {
   @SuppressWarnings("unchecked")
   public void test() {
      final Mockery context = createContext();
      final GenerationFactory<Object> generationFactory = context.mock(GenerationFactory.class);
      final Generation<Object> g0 = context.mock(Generation.class, "g0");
      final Generation<Object> g1 = context.mock(Generation.class, "g1");
      final Generation<Object> g2 = context.mock(Generation.class, "g2");
      final Generation<Object> g3 = context.mock(Generation.class, "g3");
      final Object expected = new Object();
      final Chromosone<Object> result = new Chromosone<Object>(expected, 0);
      final int maxNumberOfGenerations = 3;

      context.checking(new Expectations() {
         {
            oneOf(generationFactory).createInitialGeneration();
            will(returnValue(g0));

            oneOf(generationFactory).generateNewGeneration(g0);
            will(returnValue(g1));

            oneOf(generationFactory).generateNewGeneration(g1);
            will(returnValue(g2));

            oneOf(generationFactory).generateNewGeneration(g2);
            will(returnValue(g3));

            oneOf(g3).getFirst();
            will(returnValue(result));
         }
      });

      final Object actual = GeneticAlgorithmController.process(generationFactory, maxNumberOfGenerations);
      assertSame(expected, actual);
   }

   private Mockery createContext() {
      final Mockery context = new Mockery();
      context.setImposteriser(ClassImposteriser.INSTANCE);
      return context;
   }
}
