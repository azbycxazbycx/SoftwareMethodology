package chess;

import chess.Piece.PieceColor;
import chess.Piece.TypeOfPiece;

public class Board{
  Piece[][] grid;
  boolean isWhiteTurn;
  int turnNum;

  // Constructor
  public Board(){
    this.grid = new Piece[8][8];
    initializeBoard();
    this.isWhiteTurn = true;
    this.turnNum = 1;
  }

  private void initializeBoard() {
    
    this.grid[0] = new Piece[] {
        new Piece(PieceColor.WHITE, TypeOfPiece.R, 1, 1),
        new Piece(PieceColor.WHITE, TypeOfPiece.N, 1, 2),
        new Piece(PieceColor.WHITE, TypeOfPiece.B, 1, 3),
        new Piece(PieceColor.WHITE, TypeOfPiece.Q, 1, 4),
        new Piece(PieceColor.WHITE, TypeOfPiece.K, 1, 5),
        new Piece(PieceColor.WHITE, TypeOfPiece.B, 1, 6),
        new Piece(PieceColor.WHITE, TypeOfPiece.N, 1, 7),
        new Piece(PieceColor.WHITE, TypeOfPiece.R, 1, 8)
    };

    this.grid[1] = new Piece[] {
        new Piece(PieceColor.WHITE, TypeOfPiece.P, 2, 1),
        new Piece(PieceColor.WHITE, TypeOfPiece.P, 2, 2),
        new Piece(PieceColor.WHITE, TypeOfPiece.P, 2, 3),
        new Piece(PieceColor.WHITE, TypeOfPiece.P, 2, 4),
        new Piece(PieceColor.WHITE, TypeOfPiece.P, 2, 5),
        new Piece(PieceColor.WHITE, TypeOfPiece.P, 2, 6),
        new Piece(PieceColor.WHITE, TypeOfPiece.P, 2, 7),
        new Piece(PieceColor.WHITE, TypeOfPiece.P, 2, 8)
    };
    
    for (int i = 2; i < 6; i++) {
        for (int j = 0; j < 8; j++) {
            this.grid[i][j] = new Piece(PieceColor.BLANK, TypeOfPiece.None, i+1, j+1);
        }
    }
    
    this.grid[6] = new Piece[] {
        new Piece(PieceColor.BLACK, TypeOfPiece.P, 7, 1),
        new Piece(PieceColor.BLACK, TypeOfPiece.P, 7, 2),
        new Piece(PieceColor.BLACK, TypeOfPiece.P, 7, 3),
        new Piece(PieceColor.BLACK, TypeOfPiece.P, 7, 4),
        new Piece(PieceColor.BLACK, TypeOfPiece.P, 7, 5),
        new Piece(PieceColor.BLACK, TypeOfPiece.P, 7, 6),
        new Piece(PieceColor.BLACK, TypeOfPiece.P, 7, 7),
        new Piece(PieceColor.BLACK, TypeOfPiece.P, 7, 8)
    };
    this.grid[7] = new Piece[] {
        new Piece(PieceColor.BLACK, TypeOfPiece.R, 8, 1),
        new Piece(PieceColor.BLACK, TypeOfPiece.N, 8, 2),
        new Piece(PieceColor.BLACK, TypeOfPiece.B, 8, 3),
        new Piece(PieceColor.BLACK, TypeOfPiece.Q, 8, 4),
        new Piece(PieceColor.BLACK, TypeOfPiece.K, 8, 5),
        new Piece(PieceColor.BLACK, TypeOfPiece.B, 8, 6),
        new Piece(PieceColor.BLACK, TypeOfPiece.N, 8, 7),
        new Piece(PieceColor.BLACK, TypeOfPiece.R, 8, 8)
    };
}

//I'm not sure how we are going to figure out all the steps when we move a piece 
//but here's a basic starting point
public void movePiece(int startRank, int startFile, int endRank, int endFile) {
    if (startRank < 1 || startRank > 8 || endRank < 1 || endFile > 8) {
        System.out.println("Error: invalid move coordinates");
    }
    else if (grid[startRank - 1][startFile - 1].getType() == TypeOfPiece.None) {
        System.out.println("Error: No piece detected on rank " + startRank + " file " + startFile);
    }
    //This doesn't diffrerentiate between white or black pieces so this function won't work
    //If you want to capture an enemy piece at the moment
    else if (grid[endRank - 1][endFile - 1].getType() != TypeOfPiece.None) {
        System.out.println("Error: cannot move to already occupied square");
    }
    else if (grid[startRank - 1][startFile - 1].getColor() == PieceColor.WHITE && !isWhiteTurn) {
        System.out.println("Error: can't move white piece on black's turn");
    }
    else if (grid[startRank - 1][startFile - 1].getColor() == PieceColor.BLACK && isWhiteTurn) {
        System.out.println("Error: can't move black piece on white's turn");
    }
    else {
        grid[endRank - 1][endFile - 1].setColor(grid[startRank - 1][startFile - 1].getColor());
        grid[endRank - 1][endFile - 1].setType(grid[startRank - 1][startFile - 1].getType());

        grid[startRank - 1][startFile - 1].setBlank();
        
    }
    if (isWhiteTurn) {
        isWhiteTurn = false;
    }
    else {
        isWhiteTurn = true;
        turnNum++;
    }
} 

//Not sure if we need this but it'll basically just reset everything to the start position
public void resetBoard() {
    this.isWhiteTurn = true;
    this.turnNum = 1;
    initializeBoard();
}

  
  
}
