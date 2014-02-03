package com.how2examples.ai;

import static junit.framework.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import com.how2examples.ai.util.data.DataSet;
import com.how2examples.ai.util.data.DelimitedTextFileReader;

public class TestUtils {
   public static DataSet getDataSet(final Object o) throws Exception {
      final String fileName = "src/test/resources/" + getClassName(o) + ".csv";
      final File file = new File(fileName);
      return DelimitedTextFileReader.readCommaSeperated(file);
   }

   private static String getClassName(final Object o) {
      final String fullName = o.getClass().getName();
      return fullName.substring(fullName.lastIndexOf('.') + 1);
   }

   public static void assertArraysEqual(final Object[] actual, final Object[] expected) {
      assertTrue(Arrays.toString(actual) + " " + Arrays.toString(expected), Arrays.equals(actual, expected));
   }

   public static void assertArraysEqual(final double[] actual, final double[] expected) {
      assertTrue(Arrays.toString(actual) + " " + Arrays.toString(expected), Arrays.equals(actual, expected));
   }

   public static DataSet createDataSet(final String fileContents) throws Exception {
      final File file = writeToFile(fileContents);
      return DelimitedTextFileReader.readCommaSeperated(file);
   }

   public static File writeToFile(final String contents) {
      final File file = createTempFile();
      try (FileWriter fw = new FileWriter(file)) {
         fw.write(contents);
         return file;
      } catch (final IOException e) {
         throw new RuntimeException(e);
      }
   }

   private static File createTempFile() {
      try {
         return File.createTempFile("tmp", null);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
