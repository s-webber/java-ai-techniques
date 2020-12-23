package com.example.ai.decisiontree;

import static com.example.ai.TestUtils.createDataSet;
import static com.example.ai.TestUtils.getDataSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.example.ai.util.data.DataSet;
import com.example.ai.util.data.DataSetRow;

public class DecisionTreeTest {
   private static final DecisionTreeStrategy STRATEGY = new InformationGainStrategy();
   private static final DecisionTreeGenerator GENERATOR = new DecisionTreeGenerator(STRATEGY);

   @Test
   public void testOneInputColumn() {
      final DataSet dataSet = createDataSet("header1,header2\n" //
                                            + "a,x\n"
                                            + "b,y\n"
                                            + "c,z\n");
      final DecisionTreeNode result = GENERATOR.generateTree(dataSet);
      final String actualTree = DecisionTreeOutputter.draw(result);
      final String expectedTree = "\\-- header1\n" //
                                  + "    |   \n"
                                  + "    +-- a\n"
                                  + "    |   \\-- x\n"
                                  + "    |   \n"
                                  + "    +-- b\n"
                                  + "    |   \\-- y\n"
                                  + "    |   \n"
                                  + "    \\-- c\n"
                                  + "        \\-- z\n";

      assertEquals(expectedTree, actualTree);
   }

   @Test
   public void testTwoInputColumns() {
      final DataSet dataSet = createDataSet("header1,header2,header3\n" //
                                            + "a,2,w\n"
                                            + "a,0,x\n"
                                            + "b,2,y\n"
                                            + "b,0,z\n");
      final DecisionTreeNode result = GENERATOR.generateTree(dataSet);
      final String actualTree = DecisionTreeOutputter.draw(result);
      final String expectedTree = "\\-- header1\n" + //
                                  "    |   \n"
                                  + "    +-- a\n"
                                  + "    |   \\-- header2\n"
                                  + "    |       |   \n"
                                  + "    |       +-- 0\n"
                                  + "    |       |   \\-- x\n"
                                  + "    |       |   \n"
                                  + "    |       \\-- 2\n"
                                  + "    |           \\-- w\n"
                                  + "    |   \n"
                                  + "    \\-- b\n"
                                  + "        \\-- header2\n"
                                  + "            |   \n"
                                  + "            +-- 0\n"
                                  + "            |   \\-- z\n"
                                  + "            |   \n"
                                  + "            \\-- 2\n"
                                  + "                \\-- y\n";

      assertEquals(expectedTree, actualTree);
   }

   @Test
   public void testSuccess() {
      final DataSet dataSet = getDataSet(getClass());
      final DecisionTreeNode result = GENERATOR.generateTree(dataSet);
      final String actualTree = DecisionTreeOutputter.draw(result);
      final String expectedTree = "\\-- Income\n"
                                  + "    |   \n"
                                  + "    +-- 10-20k\n"
                                  + "    |   \\-- High\n"
                                  + "    |   \n"
                                  + "    +-- 20-40k\n"
                                  + "    |   \\-- Credit history\n"
                                  + "    |       |   \n"
                                  + "    |       +-- Bad\n"
                                  + "    |       |   \\-- High\n"
                                  + "    |       |   \n"
                                  + "    |       +-- Good\n"
                                  + "    |       |   \\-- Medium\n"
                                  + "    |       |   \n"
                                  + "    |       \\-- Unknown\n"
                                  + "    |           \\-- Medium\n"
                                  + "    |   \n"
                                  + "    +-- 40-60k\n"
                                  + "    |   \\-- Low\n"
                                  + "    |   \n"
                                  + "    +-- <10k\n"
                                  + "    |   \\-- High\n"
                                  + "    |   \n"
                                  + "    \\-- >60\n"
                                  + "        \\-- Low\n";
      assertEquals(expectedTree, actualTree);
      for (final DataSetRow line : dataSet.getValues()) {
         assertEquals(line.getOutcome(), result.getOutcome(line));
      }
   }

   @Test
   public void testOnlyOneOutcome() {
      final DataSet dataSet = createDataSet("header1,header2\n" //
                                            + "a,x\n"
                                            + "b,x\n"
                                            + "a,x\n");
      final DecisionTreeNode result = GENERATOR.generateTree(dataSet);
      final String actualTree = DecisionTreeOutputter.draw(result);
      final String expectedTree = "\\-- x\n";
      assertEquals(expectedTree, actualTree);
   }

   /**
    * Test appropriate exception thrown when data is not suitable for a decision tree.
    * <p>
    * When the input data set contains multiple rows that have the <i>same</i> inputs but <i>different</i> outcomes then
    * the decision tree generator should throw an exception. (This is expected as, in the current implementation, each
    * leaf node of the tree can only represent a single outcome.)
    */
   @Test
   public void testNotSolveable() {
      final DataSet dataSet = createDataSet("header1,header2\n" //
                                            + "a,x\n"
                                            + "b,y\n"
                                            + "a,z\n");
      try {
         GENERATOR.generateTree(dataSet);
         fail();
      } catch (IllegalArgumentException e) {
         assertEquals("The following 2 rows share the same inputs but not the same outcome: [[a, x], [a, z]]", e.getMessage());
      }
   }
}
