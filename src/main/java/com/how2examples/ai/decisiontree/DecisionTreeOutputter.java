package com.how2examples.ai.decisiontree;

import java.util.Map.Entry;

/** Returns a tree representation of a decision tree as <code>String</code>. */
public class DecisionTreeOutputter {
   public static String draw(final DecisionTreeNode n) {
      final StringBuilder sb = new StringBuilder();
      recursiveDraw(n, new boolean[0], sb);
      return sb.toString();
   }

   private static void recursiveDraw(final DecisionTreeNode n, final boolean[] space, final StringBuilder sb) {
      drawSpaces(sb, space);
      if (n.getChildren().isEmpty()) {
         // print outcome - leaf node of tree
         sb.append("\\-- " + n.getId());
         sb.append("\n");
      } else {
         final ChoiceNode c = (ChoiceNode) n;
         // print category
         sb.append("\\-- " + n.getId());
         sb.append("\n");
         boolean[] newSpaces = x(space, 1);
         setLastElementToTrue(newSpaces);
         int ctr = 0;
         for (Entry<String, DecisionTreeNode> e : c.getChildren().entrySet()) {
            boolean[] newSpaces2 = x(newSpaces, 1);

            // print gap line
            drawSpaces(sb, newSpaces2);
            sb.append("\n");

            // print possible value for category 
            drawSpaces(sb, newSpaces);
            String symbol = "+";
            if (++ctr == c.getChildren().size()) {
               setLastElementToTrue(newSpaces2);
               symbol = "\\";
            }
            sb.append(symbol + "-- " + e.getKey() + "\n");

            // recursive call to print child nodes
            recursiveDraw(e.getValue(), newSpaces2, sb);
         }
      }
   }

   private static void setLastElementToTrue(boolean[] newSpaces2) {
      newSpaces2[newSpaces2.length - 1] = true;
   }

   private static boolean[] x(final boolean[] currentState, final int increase) { // TODO rename
      final boolean[] newState = new boolean[currentState.length + increase];
      for (int i = 0; i < currentState.length; i++) {
         newState[i] = currentState[i];
      }
      return newState;
   }

   private static void drawSpaces(final StringBuilder sb, final boolean[] space) {
      for (boolean element : space) {
         sb.append(element ? ' ' : '|');
         sb.append("   ");
      }
   }
}
