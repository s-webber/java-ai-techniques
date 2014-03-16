package com.how2examples.ai.util.data;

import java.util.Arrays;

import junit.framework.TestCase;

public class DataSetRowTest extends TestCase {
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
      assertEquals(Double.parseDouble(outcome), r.getOutcomeAsDouble());
      assertEquals(numberOfInputs, r.getNumberOfInputs());
      for (int i = 0; i < numberOfInputs; i++) {
         final String expectedValue = values[i];
         assertEquals(values[i], r.getValue(i));
         assertEquals(Integer.parseInt(expectedValue), actualInputsAsIntArray[i]);
         assertEquals(Double.parseDouble(expectedValue), actualInputsAsDoubleArray[i]);
      }

      try {
         r.getValue(values.length);
         fail();
      } catch (ArrayIndexOutOfBoundsException e) {
         // expected
      }

      assertEquals(Arrays.toString(values), r.toString());
   }

   public void testFloatingPointInput() {
      final String thirdInput = "-7654.255";
      final String outcome = "8.5";
      final String[] values = {"12", "675", thirdInput, outcome};
      final DataSetRow r = new DataSetRow(values);

      final double[] actualInputsAsDoubleArray = r.getInputsAsDoubleArray();
      assertEquals(thirdInput, r.getValue(2));
      assertEquals(Double.parseDouble(thirdInput), actualInputsAsDoubleArray[2]);
      assertEquals(outcome, r.getOutcome());
      assertEquals(Double.parseDouble(outcome), r.getOutcomeAsDouble());

      try {
         r.getInputsAsIntArray();
         fail();
      } catch (NumberFormatException e) {
         // expected
      }

      assertEquals(Arrays.toString(values), r.toString());
   }

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
