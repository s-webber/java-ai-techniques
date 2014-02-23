package com.how2examples.ai.problem.noughtsandcrosses;

enum Symbol {
   NOUGHT('o'),
   CROSS('x');

   final char id;

   Symbol(char id) {
      this.id = id;
   }

   Symbol getOpponent() {
      return this == NOUGHT ? CROSS : NOUGHT;
   }
}
