package com.example.ai.ga;

/**
 * Contains configuration details used by {@link GenerationFactory} when creating new {@link Generation} instances.
 * 
 * @see ChromosoneFactory
 * @see GenerationFactory
 */
public class GeneticAlgorithmConfiguration {
   private final int generationSize;
   private final int numberOfNewCandiatesToCreate;
   private final int numberOfNewCandiatesToMutate;
   private final int numberOfNewCandiatesToCrossover;

   /**
    * @param generationSize the required total size of new {@link Generation} instances
    * @param ratioCreate the ratio (as a decimal) of candidates to create using {@link ChromosoneFactory#create()} when
    * creating a new {@link Generation} from an existing one.
    * @param ratioMutate the ratio (as a decimal) of candidates to create using {@link ChromosoneFactory#mutate(Object)}
    * when creating a new {@link Generation} from an existing one.
    * @param ratioCrossover the ratio (as a decimal) of candidates to create using
    * {@link ChromosoneFactory#crossover(Object, Object)} when creating a new {@link Generation} from an existing one.
    */
   public GeneticAlgorithmConfiguration(int generationSize, double ratioCreate, double ratioMutate, double ratioCrossover) {
      vaidateGenerationSize(generationSize);
      vaidateRatios(ratioCreate, ratioMutate, ratioCrossover);
      this.generationSize = generationSize;
      this.numberOfNewCandiatesToCreate = percent(generationSize, ratioCreate);
      this.numberOfNewCandiatesToMutate = percent(generationSize, ratioMutate);
      this.numberOfNewCandiatesToCrossover = percent(generationSize, ratioCrossover);
   }

   private void vaidateGenerationSize(int generationSize) {
      if (generationSize < 1) {
         throw new IllegalArgumentException("generation size <1: " + generationSize);
      }
   }

   private void vaidateRatios(double r1, double r2, double r3) {
      validateRatio(r1);
      validateRatio(r2);
      validateRatio(r3);
      double total = r1 + r2 + r3;
      if (total > 1) {
         throw new IllegalArgumentException("sum of ratios >1: " + total);
      }
   }

   private void validateRatio(double r) {
      if (r < 0) {
         throw new IllegalArgumentException("ratio <0: " + r);
      }
   }

   private int percent(int generationSize, double ratio) {
      return (int) (generationSize * ratio);
   }

   /**
    * Returns the required total size of new {@link Generation} instances.
    */
   int getGenerationSize() {
      return generationSize;
   }

   /**
    * Returns the number of candidates to create using {@link ChromosoneFactory#create()} when creating a new
    * {@link Generation} from an existing one.
    */
   int getNumberOfNewCandiatesToCreate() {
      return numberOfNewCandiatesToCreate;
   }

   /**
    * Returns the number of candidates to create using {@link ChromosoneFactory#mutate(Object)} when creating a new
    * {@link Generation} from an existing one.
    */
   int getNumberOfNewCandiatesToMutate() {
      return numberOfNewCandiatesToMutate;
   }

   /**
    * Returns the number of candidates to create using {@link ChromosoneFactory#crossover(Object, Object)} when creating
    * a new {@link Generation} from an existing one.
    */
   int getNumberOfNewCandiatesToCrossover() {
      return numberOfNewCandiatesToCrossover;
   }
}
