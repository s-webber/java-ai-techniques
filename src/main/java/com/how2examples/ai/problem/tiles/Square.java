package com.how2examples.ai.problem.tiles;

enum Square {
   TOP_LEFT(0, 0),
   TOP_CENTRE(1, 0),
   TOP_RIGHT(2, 0),
   MIDDLE_LEFT(0, 1),
   CENTRE(1, 1),
   MIDDLE_RIGHT(2, 1),
   BOTTOM_LEFT(0, 2),
   BOTTOM_CENTRE(1, 2),
   BOTTOM_RIGHT(2, 2);

   final int x;
   final int y;

   Square(int x, int y) {
      this.x = x;
      this.y = y;
   }
}
