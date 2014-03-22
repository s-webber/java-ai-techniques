package com.how2examples.ai;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Assert;

import com.how2examples.ai.util.data.DataSet;
import com.how2examples.ai.util.data.DelimitedTextFileReader;

public class TestUtils {
   public static DataSet getDataSet(final Class<?> c) {
      final String fullName = c.getName();
      final String nameMinusPackage = fullName.substring(fullName.lastIndexOf('.') + 1);
      return getDataSet(nameMinusPackage);
   }

   public static DataSet getDataSet(final String name) {
      final String fileName = "src/test/resources/" + name + ".csv";
      try {
         final File file = new File(fileName);
         return DelimitedTextFileReader.readCommaSeperated(file);
      } catch (IOException e) {
         throw new RuntimeException("Could not read data set from: " + fileName);
      }
   }

   public static DataSet createDataSet(final String fileContents) {
      try {
         final File file = writeToFile(fileContents);
         return DelimitedTextFileReader.readCommaSeperated(file);
      } catch (IOException e) {
         throw new RuntimeException("Could not create data set", e);
      }
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

   public static void assertExactlyEquals(double expected, double actual) {
      Assert.assertEquals(expected, actual, 0);
   }

   private static File createTempFile() {
      try {
         return File.createTempFile("tmp", null);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
