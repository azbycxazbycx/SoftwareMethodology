package chess;

import java.util.ArrayList;

import chess.Piece.TypeOfPiece;
import chess.ReturnPlay.Message;

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
			String[] coords = move.strip().split("\\s+");
			ReturnPlay newReturnPlay = new ReturnPlay();
			boolean skipMove = false;
			if (coords.length > 3) {
				chessboard.setMessage(Message.ILLEGAL_MOVE);
				skipMove = true;
			}
			else if (coords.length == 3) {
				if (coords[2].equals("draw?")) {
					chessboard.setMessage(Message.DRAW);
				}
				else if (coords[2].equals("N")) {
					chessboard.setPromotionType(TypeOfPiece.N);
				}
				else if (coords[2].equals("B")) {
					chessboard.setPromotionType(TypeOfPiece.B);
				}
				else if (coords[2].equals("R")) {
					chessboard.setPromotionType(TypeOfPiece.R);
				}
				else if (coords[2].equals("Q")) {
					chessboard.setPromotionType(TypeOfPiece.Q);
				}
				else {
					skipMove = true;
					chessboard.setMessage(Message.ILLEGAL_MOVE);
				}

			}
			else if (coords.length == 1) {
				if (coords[0].equals("resign")) {
					chessboard.resign();
					skipMove = true;
				}
				else {
					chessboard.setMessage(Message.ILLEGAL_MOVE);
					skipMove = true;
				}
			}
			else if (coords.length < 1) {
				chessboard.setMessage(Message.ILLEGAL_MOVE);
				skipMove = true;
			}

			if (!skipMove) {
				
				if (coords[0].length() != 2 || coords[1].length() != 2) {
					System.out.println("coords[0].length() = " + coords[0].length());
					System.out.println("coords[1].length() = " + coords[1].length());
					chessboard.setMessage(Message.ILLEGAL_MOVE);
					newReturnPlay.piecesOnBoard = chessboard.printPosition();
					newReturnPlay.message = chessboard.getMessage();
					return newReturnPlay;
				}
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
				if (startFile > 0 && startFile <= 8 && startRank > 0 && startRank <= 8 
				&& endRank > 0 && endRank <= 8 && endFile > 0 && endFile <= 8) {
					chessboard.movePiece(startRank, startFile, endRank, endFile);
				}
				else {
					System.out.println("startRank = " + startRank);
					System.out.println("startFile = " + startFile);
					System.out.println("endRank = " + endRank);
					System.out.println("endFile = " + endFile);
					System.out.println("{}OOO");
					chessboard.setMessage(Message.ILLEGAL_MOVE);
				}
				
			}
			
			
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
