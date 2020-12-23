package com.example.ai.problem.tsp;

import com.google.common.collect.ImmutableList;
import com.example.ai.TestUtils;
import com.example.ai.util.data.DataSet;

class TravellingSalesPersonTestUtils {
   static final String SOU = "SOU";
   static final String EXT = "EXT";
   static final String BHX = "BHX";
   static final String MAN = "MAN";
   static final String NCL = "NCL";
   static final String NOR = "NOR";

   static Locations createLocations() {
      ImmutableList<Leg> legs = LegsFactory.createLegs(dataSet());
      Locations locations = new Locations(legs);
      return locations;
   }

   private static DataSet dataSet() {
      return TestUtils.getDataSet("TravellingSalesPersonTestData");
   }
}
