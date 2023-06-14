package com.example.ai.problem.tsp;

import static com.example.ai.TestUtils.assertExactlyEquals;
import static com.example.ai.problem.tsp.TravellingSalesPersonTestUtils.BHX;
import static com.example.ai.problem.tsp.TravellingSalesPersonTestUtils.EXT;
import static com.example.ai.problem.tsp.TravellingSalesPersonTestUtils.MAN;
import static com.example.ai.problem.tsp.TravellingSalesPersonTestUtils.NCL;
import static com.example.ai.problem.tsp.TravellingSalesPersonTestUtils.NOR;
import static com.example.ai.problem.tsp.TravellingSalesPersonTestUtils.SOU;
import static com.example.ai.problem.tsp.TravellingSalesPersonTestUtils.createLocations;
import static com.example.ai.util.ImmutableListFactory.createList;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.jupiter.api.Test;

import com.example.ai.ga.Chromosone;
import com.example.ai.util.Randomiser;
import com.google.common.collect.ImmutableList;

public class TravellingSalesPersonChromosoneCreatorTest {
   /** NOR repeated twice. */
   private static final ImmutableList<String> INVALID_LIST_REPEATED_LOCATION = createList(MAN, EXT, SOU, NOR, BHX, NOR);
   /** Location name: "XYZ" not included in test data. */
   private static final ImmutableList<String> INVALID_LIST_UNKNOWN_LOCATION = createList(MAN, EXT, SOU, NOR, BHX, NCL, "XYZ");

   private final Mockery context = new Mockery();
   private final Randomiser randomiser = context.mock(Randomiser.class);
   private final Locations locations = createLocations();
   private final ImmutableList<String> locationNames = locations.getLocationNames();
   private final TravellingSalesPersonChromosoneCreator creator = new TravellingSalesPersonChromosoneCreator(locations, randomiser);

   @Test
   public void testCreate() {
      final List<String> expected = asList(NOR, NCL, MAN, BHX, EXT, SOU);

      context.checking(new Expectations() {
         {
            oneOf(randomiser).shuffle(locationNames);
            will(returnValue(expected));
         }
      });

      final Chromosone<ImmutableList<String>> output = creator.create();
      assertEquals(expected, output.getValue());
      // 287 + 150 + 97 + 157 + 154 + 218 = 1063
      assertExactlyEquals(-1063.0, output.getFitness());
   }

   /** Test mid-range values returned from randomiser. */
   @Test
   public void testMutate1() {
      ImmutableList<String> input = createList(SOU, EXT, MAN, BHX, NCL, NOR);
      ImmutableList<String> expectedOutput = createList(SOU, NCL, MAN, BHX, EXT, NOR);

      assertMutation(input, expectedOutput, 0.3, 0.7);
      assertMutation(input, expectedOutput, 0.7, 0.3);
   }

   /** Test small/large values returned from randomiser. */
   @Test
   public void testMutate2() {
      ImmutableList<String> input = createList(BHX, MAN, EXT, SOU, NOR, NCL);
      ImmutableList<String> expectedOutput = createList(NCL, MAN, EXT, SOU, NOR, BHX);

      assertMutation(input, expectedOutput, 0.0, 0.9999);
      assertMutation(input, expectedOutput, 0.9999, 0.0);
   }

   /** Test duplicate values returned from randomiser. */
   @Test
   public void testMutate3() {
      ImmutableList<String> input = createList(MAN, EXT, SOU, NOR, BHX, NCL);
      ImmutableList<String> expectedOutput = createList(MAN, EXT, NOR, SOU, BHX, NCL);

      assertMutation(input, expectedOutput, 0.5, 0.5);
   }

   @Test
   public void testMutateRepeatedLocation() {
      assertThrows(IllegalArgumentException.class, () -> creator.mutate(INVALID_LIST_REPEATED_LOCATION));
   }

   @Test
   public void testMutateUnknownLocation() {
      assertThrows(IllegalArgumentException.class, () -> creator.mutate(INVALID_LIST_UNKNOWN_LOCATION));
   }

   /** Test when the two inputs are equal. */
   @Test
   public void testCrossover1() {
      ImmutableList<String> input = createList(MAN, EXT, SOU, NOR, BHX, NCL);

      assertCrossover(input, input, input);
   }

   @Test
   public void testCrossover2() {
      ImmutableList<String> input1 = createList(MAN, EXT, SOU, NOR, BHX, NCL);
      ImmutableList<String> input2 = createList(NOR, NCL, MAN, BHX, EXT, SOU);
      ImmutableList<String> expected = createList(MAN, BHX, NCL, NOR, SOU, EXT);

      assertCrossover(input1, input2, expected);
   }

   @Test
   public void testCrossover3() {
      ImmutableList<String> input1 = createList(MAN, EXT, SOU, NOR, BHX, NCL);
      ImmutableList<String> input2 = createList(BHX, MAN, EXT, SOU, NOR, NCL);
      ImmutableList<String> expected = createList(MAN, EXT, SOU, NOR, BHX, NCL);

      assertCrossover(input1, input2, expected);
   }

   @Test
   public void testCrossover4() {
      ImmutableList<String> input1 = createList(MAN, EXT, SOU, NOR, BHX, NCL);
      ImmutableList<String> input2 = createList(MAN, SOU, EXT, NOR, BHX, NCL);
      ImmutableList<String> expected = createList(MAN, SOU, NOR, BHX, NCL, EXT);

      assertCrossover(input1, input2, expected);
   }

   @Test
   public void testCrossoverRepeatedLocationInput1() {
      assertThrows(IllegalArgumentException.class, () -> creator.crossover(INVALID_LIST_REPEATED_LOCATION, locationNames));
   }

   @Test
   public void testCrossoverUnknownLocationInput1() {
      assertThrows(IllegalArgumentException.class, () -> creator.crossover(INVALID_LIST_UNKNOWN_LOCATION, locationNames));
   }

   @Test
   public void testCrossoverRepeatedLocationInput2() {
      assertThrows(IllegalArgumentException.class, () -> creator.crossover(locationNames, INVALID_LIST_REPEATED_LOCATION));
   }

   @Test
   public void testCrossoverUnknownLocationInput2() {
      assertThrows(IllegalArgumentException.class, () -> creator.crossover(locationNames, INVALID_LIST_UNKNOWN_LOCATION));
   }

   @Test
   public void testCrossoverRepeatedLocationBothInputs() {
      assertThrows(IllegalArgumentException.class, () -> creator.crossover(INVALID_LIST_REPEATED_LOCATION, INVALID_LIST_REPEATED_LOCATION));
   }

   @Test
   public void testCrossoverUnknownLocationBothInputs() {
      assertThrows(IllegalArgumentException.class, () -> creator.crossover(INVALID_LIST_UNKNOWN_LOCATION, INVALID_LIST_UNKNOWN_LOCATION));
   }

   private void assertMutation(ImmutableList<String> input, ImmutableList<String> expected, final double d1, final double d2) {
      context.checking(new Expectations() {
         {
            oneOf(randomiser).nextDouble();
            will(returnValue(d1));

            oneOf(randomiser).nextDouble();
            will(returnValue(d2));
         }
      });

      final Chromosone<ImmutableList<String>> output = creator.mutate(input);
      assertEquals(expected, output.getValue());
   }

   private void assertCrossover(ImmutableList<String> input1, ImmutableList<String> input2, ImmutableList<String> expected) {
      final Chromosone<ImmutableList<String>> actual = creator.crossover(input1, input2);
      assertEquals(expected, actual.getValue());
   }
}
