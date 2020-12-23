package com.example.ai.problem.queens;

/** Creates configuration details for a chess board of a specified width. */
class QueensConfiguration {
   /**
    * A unique ID for each square on the board.
    * <p>
    * e.g. {@code squareIds[2][2]} returns the ID of the square on the second file of the second rank.
    * </p>
    */
   private final long[][] squareIds;
   /**
    * For each square on the board, contains details of which other squares would be covered by a queen being in the
    * square.
    */
   private final long[][] coverage;

   /**
    * Creates configuration for a chess board of the specified width.
    * <p>
    * <b>NOTE:</b> The maximum board width is 8 as the game-state is represented using a 64-bit long, and a 8x8 board
    * has 64 squares. TODO: The maximum width restriction could be removed if the state was modelled using a BigInteger.
    * </p>
    * 
    * @throws IllegalArgumentException if {@code boardWidth} is less than 0 or greater than 8.
    */
   QueensConfiguration(final int boardWidth) {
      validateBoardWidth(boardWidth);
      this.squareIds = createSquareIds(boardWidth);
      this.coverage = createCoverage(squareIds);
   }

   private void validateBoardWidth(final int boardWidth) {
      if (boardWidth < 1 || boardWidth > 8) {
         throw new IllegalArgumentException("board width: " + boardWidth);
      }
   }

   private static long[][] createSquareIds(final int boardWidth) {
      final long[][] squareIds = new long[boardWidth][boardWidth];
      long ctr = 1;
      for (int file = 0; file < boardWidth; file++) {
         for (int rank = 0; rank < boardWidth; rank++) {
            squareIds[file][rank] = ctr;
            ctr *= 2;
         }
      }
      return squareIds;
   }

   private static long[][] createCoverage(final long[][] squareIds) {
      // Not very efficient way of calculating, for each square,
      // what squares it shares a row, column, or diagonal with.
      final int boardWidth = squareIds.length;
      final long[][] coverage = new long[boardWidth][boardWidth];
      for (int file = 0; file < boardWidth; file++) {
         for (int rank = 0; rank < boardWidth; rank++) {
            long c = squareIds[file][rank];
            for (int i = 0; i < boardWidth; i++) {
               if (i != rank) {
                  c += squareIds[file][i];
               }
               if (i != file) {
                  c += squareIds[i][rank];
               }

               if (i != 0) {
                  // diagonals in all 4 directions from current square
                  c += getSquareId(squareIds, file + i, rank + i);
                  c += getSquareId(squareIds, file - i, rank - i);
                  c += getSquareId(squareIds, file - i, rank + i);
                  c += getSquareId(squareIds, file + i, rank - i);
               }
            }
            coverage[file][rank] = c;
         }
      }
      return coverage;
   }

   /** @return square id of specified coordinates, or 0 if invalid coordinates */
   private static long getSquareId(long[][] squareIds, int file, int rank) {
      if (isValidCoordinate(squareIds, file) && isValidCoordinate(squareIds, rank)) {
         return squareIds[file][rank];
      } else {
         return 0;
      }
   }

   private static boolean isValidCoordinate(long[][] squareIds, int c) {
      return c > -1 && c < squareIds.length;
   }

   int getBoardWidth() {
      return squareIds.length;
   }

   long getSquareId(int file, int rank) {
      return squareIds[file][rank];
   }

   long getCoverage(int file, int rank) {
      return coverage[file][rank];
   }
}
