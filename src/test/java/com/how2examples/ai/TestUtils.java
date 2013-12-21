package com.how2examples.ai;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.how2examples.ai.util.data.DataSet;
import com.how2examples.ai.util.data.DelimitedTextFileReader;

public class TestUtils {
   public static DataSet createDataSet(final String fileContents) throws Exception {
      final File file = writeToFile(fileContents);
      return DelimitedTextFileReader.readCommaSeperated(file);
   }

   public static File writeToFile(final String contents) {
      FileWriter fw = null;
      try {
         final File file = File.createTempFile("tmp", null);
         fw = new FileWriter(file);
         fw.write(contents);
         return file;
      } catch (final IOException e) {
         throw new RuntimeException(e);
      } finally {
         try {
            fw.close();
         } catch (final IOException e) {
         }
      }
   }
}
