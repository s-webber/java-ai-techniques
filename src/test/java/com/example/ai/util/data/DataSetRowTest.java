package com.example.ai.util.data;

import static com.example.ai.TestUtils.assertExactlyEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

public class DataSetRowTest {
   @Test
   public void testIntegerInput() {
      final String outcome = "456";
      final String[] values = {"-143", "896", "6", outcome};
      final int numberOfInputs = values.length - 1; // -1 to exclude output
      final DataSetRow r = new DataSetRow(values);
      final int[] actualInputsAsIntArray = r.getInputsAsIntArray();
      final double[] actualInputsAsDoubleArray = r.getInputsAsDoubleArray();

      assertEquals(outcome, r.getOutcome());
      assertEquals(outcome, r.getValue(values.length - 1));
      assertEquals(Integer.parseInt(outcome), r.getOutcomeAsInt());
      assertExactlyEquals(Double.parseDouble(outcome), r.getOutcomeAsDouble());
      assertEquals(numberOfInputs, r.getNumberOfInputs());
      for (int i = 0; i < numberOfInputs; i++) {
         final String expectedValue = values[i];
         assertEquals(values[i], r.getValue(i));
         assertEquals(Integer.parseInt(expectedValue), actualInputsAsIntArray[i]);
         assertExactlyEquals(Double.parseDouble(expectedValue), actualInputsAsDoubleArray[i]);
      }

      try {
         r.getValue(values.length);
         fail();
      } catch (ArrayIndexOutOfBoundsException e) {
         // expected
      }

      assertEquals(Arrays.toString(values), r.toString());
   }

   @Test
   public void testFloatingPointInput() {
      final String thirdInput = "-7654.255";
      final String outcome = "8.5";
      final String[] values = {"12", "675", thirdInput, outcome};
      final DataSetRow r = new DataSetRow(values);

      final double[] actualInputsAsDoubleArray = r.getInputsAsDoubleArray();
      assertEquals(thirdInput, r.getValue(2));
      assertExactlyEquals(Double.parseDouble(thirdInput), actualInputsAsDoubleArray[2]);
      assertEquals(outcome, r.getOutcome());
      assertExactlyEquals(Double.parseDouble(outcome), r.getOutcomeAsDouble());

      try {
         r.getInputsAsIntArray();
         fail();
      } catch (NumberFormatException e) {
         // expected
      }

      assertEquals(Arrays.toString(values), r.toString());
   }

   @Test
   public void testStringInput() {
      final String secondInput = "fdg";
      final String outcome = "xdsds";
      final String[] values = {"12", secondInput, "86.5", outcome};
      final DataSetRow r = new DataSetRow(values);
      try {
         r.getInputsAsIntArray();
         fail();
      } catch (NumberFormatException e) {
         // expected
      }
      try {
         r.getInputsAsDoubleArray();
         fail();
      } catch (NumberFormatException e) {
         // expected
      }
      try {
         r.getOutcomeAsDouble();
         fail();
      } catch (NumberFormatException e) {
         // expected
      }
      assertEquals(secondInput, r.getValue(1));
      assertEquals(outcome, r.getOutcome());

      assertEquals(Arrays.toString(values), r.toString());
   }
}
