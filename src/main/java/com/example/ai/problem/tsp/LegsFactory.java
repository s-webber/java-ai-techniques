package com.example.ai.problem.tsp;

import static com.example.ai.util.ImmutableListFactory.createList;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.example.ai.util.data.DataSet;
import com.example.ai.util.data.DataSetRow;

/** Creates a collection of {@link Leg} instance from a {@link DataSet}. */
class LegsFactory {
   static ImmutableList<Leg> createLegs(DataSet ds) {
      List<Leg> a = new ArrayList<>();
      for (DataSetRow r : ds.getValues()) {
         Leg leg = new Leg(r.getValue(0), r.getValue(1), r.getOutcomeAsInt());
         a.add(leg);
      }
      return createList(a);
   }
}
