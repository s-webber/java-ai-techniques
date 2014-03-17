package com.how2examples.ai.util.data;

import static com.how2examples.ai.util.ImmutableListFactory.createList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Constructs {@link DataSet} instances based on character-separated values read from a file.
 * <p>
 * Currently only supports comma-separated values.
 */
public class DelimitedTextFileReader {
   private static final String DELIMETER = ",";
   private static final String COMMENT_CHAR = "#";

   /**
    * Constructs a new {@link DataSet} containing the comma-separated values read from the specified file.
    * <p>
    * The first line must contain the data set's headers. Subsequent lines will be ignored if they match either of the
    * following criteria:
    * <ul>
    * <li>Lines beginning with a {@code #} (as are assumed to be comments).</li>
    * <li>Blank lines.</li>
    * </ul>
    * 
    * @param inputCsvFile the file to read the the comma-separated values from
    * @return A {@link DataSet} representing the comma-separated values read from the specified file.
    */
   public static DataSet readCommaSeperated(final File inputCsvFile) throws IOException {
      try (final FileReader fr = new FileReader(inputCsvFile); final BufferedReader br = new BufferedReader(fr)) {
         String next = br.readLine();
         final String[] keys = next.split(DELIMETER);
         final List<DataSetRow> values = new ArrayList<>();
         while ((next = br.readLine()) != null) {
            if (!isBlank(next) && !isComment(next)) {
               final String value[] = next.split(DELIMETER);
               if (value.length != keys.length) {
                  throw new RuntimeException("Line has different number of elements than header: " + next);
               }
               values.add(new DataSetRow(value));
            }
         }
         return new DataSet(createList(keys), createList(values));
      }
   }

   private static boolean isBlank(String next) {
      return next.trim().length() == 0;
   }

   private static boolean isComment(String next) {
      return next.startsWith(COMMENT_CHAR);
   }
}
