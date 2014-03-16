package com.how2examples.ai.util.data;

import static com.how2examples.ai.TestUtils.createDataSet;
import static com.how2examples.ai.TestUtils.writeToFile;

import java.io.File;

import junit.framework.TestCase;

import com.google.common.collect.ImmutableList;

public class DelimitedTextFileReaderTest extends TestCase {
   private final static String HEADER = "h1,h2,h3\n";
   private final static String VALID_ROW = "r1,r2,r3\n";
   private final static String SHORT_ROW = "r1,r2\n";
   private final static String LONG_ROW = "r1,r2,r3,r4\n";

   public void testCsv() throws Exception {
      final String header = "a,b,c,d,e\n";
      // input includes a mixture of letters, numbers and other characters (including whitespace) - plus empty elements
      final String[] input = {"1.04,2,3_3,4e0,5\n", "qwe\trty,!\"$()-+={}[];@~#/?><.,,r t|yu/i,%^&*t\n", "z,x,c,v b N m,b\n"};
      final StringBuilder fileContents = new StringBuilder(header);
      for (final String s : input) {
         fileContents.append(s);
      }

      final DataSet ds = createDataSet(fileContents.toString());

      assertEquals(5, ds.getNumberOfColumns());
      assertEquals("[a, b, c, d, e]", ds.getKeys().toString());
      final ImmutableList<DataSetRow> values = ds.getValues();
      assertEquals(input.length, ds.size());
      assertEquals(input.length, values.size());
      for (int i = 0; i < input.length; i++) {
         assertDataSetRowValues(input[i], values.get(i));
      }
   }

   public void testDuplicatesAllowed() throws Exception {
      final DataSet ds = createDataSet(HEADER + VALID_ROW + VALID_ROW);
      assertDataSet(ds, VALID_ROW, VALID_ROW);
   }

   public void testCommentsIgnored() throws Exception {
      final DataSet ds = createDataSet(HEADER + "#a comment\n" + VALID_ROW + "#x1,x2,x3\n" + VALID_ROW + "#another comment\n");
      assertDataSet(ds, VALID_ROW, VALID_ROW);
   }

   public void testBlankLinesIgnored() throws Exception {
      final DataSet ds = createDataSet(HEADER + "\n" + VALID_ROW + "\t\n" + VALID_ROW + " \n");
      assertDataSet(ds, VALID_ROW, VALID_ROW);
   }

   public void testNoNewLineNeededAtEndOfFile() throws Exception {
      final DataSet ds = createDataSet(HEADER + VALID_ROW + VALID_ROW.trim());
      assertDataSet(ds, VALID_ROW, VALID_ROW);
   }

   public void testTooFewElements() throws Exception {
      assertTooFewElements(HEADER + SHORT_ROW + VALID_ROW + VALID_ROW);
      assertTooFewElements(HEADER + VALID_ROW + SHORT_ROW + VALID_ROW);
      assertTooFewElements(HEADER + VALID_ROW + VALID_ROW + SHORT_ROW);
      assertTooFewElements(HEADER + SHORT_ROW + SHORT_ROW + SHORT_ROW);
   }

   public void testTooManyElements() throws Exception {
      assertTooManyElements(HEADER + LONG_ROW + VALID_ROW + VALID_ROW);
      assertTooManyElements(HEADER + VALID_ROW + LONG_ROW + VALID_ROW);
      assertTooManyElements(HEADER + VALID_ROW + VALID_ROW + LONG_ROW);
      assertTooManyElements(HEADER + LONG_ROW + LONG_ROW + LONG_ROW);
   }

   private void assertTooFewElements(final String fileContents) {
      assertWidthsDontMatch(fileContents, SHORT_ROW);
   }

   private void assertTooManyElements(final String fileContents) {
      assertWidthsDontMatch(fileContents, LONG_ROW);
   }

   private void assertWidthsDontMatch(final String fileContents, final String incorrectRow) {
      try {
         final File f = writeToFile(fileContents);
         DelimitedTextFileReader.readCommaSeperated(f);
         fail();
      } catch (final Exception e) {
         assertEquals("Line has different number of elements than header: " + incorrectRow.trim(), e.getMessage());
      }
   }

   private void assertDataSet(final DataSet ds, final String... expected) {
      final ImmutableList<DataSetRow> values = ds.getValues();

      assertEquals(expected.length, ds.size());
      assertEquals(expected.length, values.size());

      for (int i = 0; i < expected.length; i++) {
         assertDataSetRowValues(expected[i], values.get(i));
      }
   }

   private void assertDataSetRowValues(final String expected, final DataSetRow actual) {
      final String[] expectedElements = expected.trim().split(",");
      assertEquals(expectedElements.length, actual.getNumberOfInputs() + 1);
      for (int i = 0; i < expectedElements.length; i++) {
         assertEquals(expectedElements[i], actual.getValue(i));
      }
      assertEquals(expectedElements[expectedElements.length - 1], actual.getOutcome());
   }
}