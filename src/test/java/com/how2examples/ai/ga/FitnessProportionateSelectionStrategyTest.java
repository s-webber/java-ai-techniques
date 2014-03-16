package com.how2examples.ai.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import junit.framework.TestCase;

import com.how2examples.ai.util.ImmutableArray;
import com.how2examples.ai.util.Randomiser;

public class FitnessProportionateSelectionStrategyTest extends TestCase {
   public void testEvenSplit() {
      List<Chromosone<Object>> input = createInput(1, 1, 1);
      assertGetInstance(input, .3333, .6666);
   }

   public void testUnevenSplit() {
      List<Chromosone<Object>> input = createInput(80, 19, 1);
      assertGetInstance(input, .7999, .9899);
   }

   private List<Chromosone<Object>> createInput(int... fitness) {
      List<Chromosone<Object>> input = new ArrayList<>();
      for (int f : fitness) {
         input.add(new Chromosone<Object>(new Object(), f));
      }
      return input;
   }

   private void assertGetInstance(List<Chromosone<Object>> input, double... limits) {
      assertEquals(input.size(), limits.length + 1);

      int ctr = 0;
      final double[] data = new double[2 + (limits.length * 2)];
      data[ctr++] = 0.0;
      for (double limit : limits) {
         data[ctr++] = limit;
         data[ctr++] = limit + .0001;
      }
      data[ctr++] = 0.9999;

      final SelectionStrategy<Object> s = new FitnessProportionateSelectionStrategy<>(new DummyRandomiser(data));
      final Queue<Object> output = s.selectCandidates(new Generation<>(input), data.length);

      assertEquals(input.get(0).getValue(), output.poll());
      for (int i = 0; i < limits.length; i++) {
         assertEquals(input.get(i).getValue(), output.poll());
         assertEquals(input.get(i + 1).getValue(), output.poll());
      }
      assertEquals(input.get(input.size() - 1).getValue(), output.poll());
   }

   private static class DummyRandomiser implements Randomiser {
      // use hard-coded values so always get predictable results
      private final double[] data;

      private int ctr;

      public DummyRandomiser(double[] data) {
         this.data = data;
      }

      @Override
      public double getDouble(double minInclusive, double maxInclusive) {
         throw new UnsupportedOperationException();
      }

      @Override
      public double nextDouble() {
         return data[ctr++];
      }

      @Override
      public <T> ImmutableArray<T> shuffle(ImmutableArray<T> locationNames) {
         throw new UnsupportedOperationException();
      }
   }
}
