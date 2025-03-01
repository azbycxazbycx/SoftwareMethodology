package chess;

import java.util.ArrayList;

public class Chess {
		private static Board chessboard;
	
		enum Player { white, black }
		
		/**
		 * Plays the next move for whichever player has the turn.
		 * 
		 * @param move String for next move, e.g. "a2 a3"
		 * 
		 * @return A ReturnPlay instance that contains the result of the move.
		 *         See the section "The Chess class" in the assignment description for details of
		 *         the contents of the returned ReturnPlay instance.
		 */
		public static ReturnPlay play(String move) {
			String[] coords = move.split("\\s+");
			

			// Convert file (letter) to a number (1 to 8)
			int startFile = coords[0].charAt(0) - 'a' + 1;
			int endFile = coords[1].charAt(0) - 'a' + 1;

			// Convert rank (number) to an integer
			int startRank = Character.getNumericValue(coords[0].charAt(1));
			int endRank = Character.getNumericValue(coords[1].charAt(1));

			// System.out.println("Testing input to coordinates");
			// System.out.println("startRank: " + startRank);
			// System.out.println("startFile: " + startFile);
			// System.out.println("endRank: " + endRank);
			// System.out.println("endFile: " + endFile);
			
			chessboard.movePiece(startRank, startFile, endRank, endFile);
			ReturnPlay newReturnPlay = new ReturnPlay();
			newReturnPlay.piecesOnBoard = chessboard.printPosition();
			newReturnPlay.message = chessboard.getMessage();
			/* FILL IN THIS METHOD */
			
			/* FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY */
			/* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */
			return newReturnPlay;
		}
		
		
		/**
		 * This method should reset the game, and start from scratch.
		 */
		public static void start() {
			/* FILL IN THIS METHOD */
			chessboard = new Board();
			System.out.println("Testing method - Should show up in terminal");
			
		}

		//Test method to test printing board
		public static ArrayList<ReturnPiece> getArrayListTest() {

			return chessboard.printPosition();
		}
}
