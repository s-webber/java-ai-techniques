package com.how2examples.ai.problem.tsp;

import static com.how2examples.ai.problem.tsp.TravellingSalesPersonTestUtils.BHX;
import static com.how2examples.ai.problem.tsp.TravellingSalesPersonTestUtils.EXT;
import static com.how2examples.ai.problem.tsp.TravellingSalesPersonTestUtils.MAN;
import static com.how2examples.ai.problem.tsp.TravellingSalesPersonTestUtils.NCL;
import static com.how2examples.ai.problem.tsp.TravellingSalesPersonTestUtils.NOR;
import static com.how2examples.ai.problem.tsp.TravellingSalesPersonTestUtils.SOU;
import static com.how2examples.ai.problem.tsp.TravellingSalesPersonTestUtils.createLocations;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class LocationsTest {
   private final Locations locations = createLocations();

   @Test
   public void testLocationNames() {
      List<String> expected = asList(BHX, EXT, MAN, NCL, NOR, SOU);
      ImmutableList<String> actual = locations.getLocationNames();
      assertEquals(expected.size(), actual.size());
      assertTrue(expected.containsAll(actual));
   }

   @Test
   public void testGetDistance() {
      assertDistance(BHX, EXT, 157);
      assertDistance(EXT, NCL, 343);
      assertDistance(MAN, NOR, 253);
      assertDistance(NOR, SOU, 218);
   }

   @Test
   public void testGetDistanceInvalidDeparture() {
      try {
         locations.getDistance("XYZ", "MAN");
         fail();
      } catch (IllegalArgumentException e) {
         assertEquals("Have no record of legs departing from: XYZ", e.getMessage());
      }
   }

   @Test
   public void testGetDistanceInvalidDestination() {
      try {
         locations.getDistance(MAN, "XYZ");
         fail();
      } catch (IllegalArgumentException e) {
         assertEquals("Have no record of distance from: MAN to: XYZ", e.getMessage());
      }
   }

   private void assertDistance(String from, String to, int distance) {
      assertEquals(locations.getDistance(from, to), distance);
      assertEquals(locations.getDistance(to, from), distance);
   }
}
