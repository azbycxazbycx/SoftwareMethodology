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
			String[] test = move.split("\\s+");
			
			//chessboard.movePiece(2, 4, 4, 4);
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
