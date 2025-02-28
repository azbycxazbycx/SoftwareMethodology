package chess;

import chess.Piece.PieceColor;
import chess.Piece.TypeOfPiece;
import chess.ReturnPlay.Message;
import java.util.ArrayList;

public class Board{
    Square[][] grid;
    boolean isWhiteTurn;
    int turnNum;

    // Constructor
    public Board(){
        this.grid = new Square[8][8];
        initializeBoard();
        this.isWhiteTurn = true;
        this.turnNum = 1;
    }

    private void initializeBoard() {
        this.grid[0] = new Square[] {
            new Square(1, 1, new Piece(PieceColor.WHITE, TypeOfPiece.R)),
            new Square(1, 2, new Piece(PieceColor.WHITE, TypeOfPiece.N)),
            new Square(1, 3, new Piece(PieceColor.WHITE, TypeOfPiece.B)),
            new Square(1, 4, new Piece(PieceColor.WHITE, TypeOfPiece.Q)),
            new Square(1, 5, new Piece(PieceColor.WHITE, TypeOfPiece.K)),
            new Square(1, 6, new Piece(PieceColor.WHITE, TypeOfPiece.B)),
            new Square(1, 7, new Piece(PieceColor.WHITE, TypeOfPiece.N)),
            new Square(1, 8, new Piece(PieceColor.WHITE, TypeOfPiece.R))
        };

        this.grid[1] = new Square[] {
            new Square(2, 1, new Piece(PieceColor.WHITE, TypeOfPiece.P)),
            new Square(2, 2, new Piece(PieceColor.WHITE, TypeOfPiece.P)),
            new Square(2, 3, new Piece(PieceColor.WHITE, TypeOfPiece.P)),
            new Square(2, 4, new Piece(PieceColor.WHITE, TypeOfPiece.P)),
            new Square(2, 5, new Piece(PieceColor.WHITE, TypeOfPiece.P)),
            new Square(2, 6, new Piece(PieceColor.WHITE, TypeOfPiece.P)),
            new Square(2, 7, new Piece(PieceColor.WHITE, TypeOfPiece.P)),
            new Square(2, 8, new Piece(PieceColor.WHITE, TypeOfPiece.P)),
        };
    
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                this.grid[i][j] = new Square(i+1, j+1);
            }
        }
    
        this.grid[6] = new Square[] {
            new Square(7, 1, new Piece(PieceColor.BLACK, TypeOfPiece.P)),
            new Square(7, 2, new Piece(PieceColor.BLACK, TypeOfPiece.P)),
            new Square(7, 3, new Piece(PieceColor.BLACK, TypeOfPiece.P)),
            new Square(7, 4, new Piece(PieceColor.BLACK, TypeOfPiece.P)),
            new Square(7, 5, new Piece(PieceColor.BLACK, TypeOfPiece.P)),
            new Square(7, 6, new Piece(PieceColor.BLACK, TypeOfPiece.P)),
            new Square(7, 7, new Piece(PieceColor.BLACK, TypeOfPiece.P)),
            new Square(7, 8, new Piece(PieceColor.BLACK, TypeOfPiece.P))
        };
        this.grid[7] = new Square[] {
            new Square(8, 1, new Piece(PieceColor.BLACK, TypeOfPiece.R)),
            new Square(8, 2, new Piece(PieceColor.BLACK, TypeOfPiece.N)),
            new Square(8, 3, new Piece(PieceColor.BLACK, TypeOfPiece.B)),
            new Square(8, 4, new Piece(PieceColor.BLACK, TypeOfPiece.Q)),
            new Square(8, 5, new Piece(PieceColor.BLACK, TypeOfPiece.K)),
            new Square(8, 6, new Piece(PieceColor.BLACK, TypeOfPiece.B)),
            new Square(8, 7, new Piece(PieceColor.BLACK, TypeOfPiece.N)),
            new Square(8, 8, new Piece(PieceColor.BLACK, TypeOfPiece.R))
        };
    }

    
    
    

    //I'm not sure how we are going to figure out all the steps when we move a piece 
    //but here's a basic starting point
    public void movePiece(int startRank, int startFile, int endRank, int endFile) {
        if (startRank < 1 || startRank > 8 || endRank < 1 || endFile > 8) {
            System.out.println("Error: invalid move coordinates");
        }
        else if (grid[startRank - 1][startFile - 1].getPiece() == null) {
            System.out.println("Error: No piece detected on rank " + startRank + " file " + startFile);
        }
        //This doesn't diffrerentiate between white or black pieces so this function won't work
        //If you want to capture an enemy piece at the moment
        else if (grid[endRank - 1][endFile - 1].getPiece() != null) {
            System.out.println("Error: cannot move to already occupied square");
        }
        else if (grid[startRank - 1][startFile - 1].getPiece().getColor() == PieceColor.WHITE && !isWhiteTurn) {
            System.out.println("Error: can't move white piece on black's turn");
        }
        else if (grid[startRank - 1][startFile - 1].getPiece().getColor() == PieceColor.BLACK && isWhiteTurn) {
            System.out.println("Error: can't move black piece on white's turn");
        }
        else {
            grid[endRank - 1][endFile - 1].placePiece(grid[startRank - 1][startFile - 1].getPiece());

            grid[startRank - 1][startFile - 1].takePiece();
        
        }
        if (isWhiteTurn) {
            isWhiteTurn = false;
        }
        else {
            isWhiteTurn = true;
            turnNum++;
        }
    } 

    //Will check if current position requires a message like "check"
    public Message checkMessage() {
        return null;
    }

    //Not sure if we need this but it'll basically just reset everything to the start position
    public void resetBoard() {
        this.isWhiteTurn = true;
        this.turnNum = 1;
        initializeBoard();
    }

    //It seems like they ultimately want us to return an arraylist from the returnplay class, so this'll convert the board
    public ArrayList<ReturnPiece> printPosition() {
        return null;
    }

  
    public boolean isValidMove(int startRank, int startFile, int endRank, int endFile) {
        if (startRank < 1 || startRank > 8 || endRank < 1 || endRank > 8) {
            System.out.println("Error: Move not in range of board");
            return false;
        }
        Square startSquare = this.grid[startRank-1][startFile-1];
        Square endSquare = this.grid[endRank-1][endFile-1];
        Piece movingPiece = startSquare.getPiece();

        if (movingPiece == null) {
            System.out.println("No piece to move");
            return false;
        }
        if (startRank == endRank && startFile == endFile) {
            System.out.println("Cannot move to starting square");
            return false;
        }
        if ((movingPiece.getColor() == PieceColor.WHITE) != isWhiteTurn) {
            System.out.println("Cannot move opponent's piece");
            return false;
        }
        if (endSquare.getPiece().getColor() == movingPiece.getColor()) {
            System.out.println("Cannot move piece into another friendly piece");
            return false;
        }
        if (!isValidPieceMove(startSquare, endSquare, movingPiece)) {
            return false;
        }
        
        if (isPathObstructed(startSquare, endSquare, movingPiece)) {
            return false;
        }
        return true;
    }

    private boolean isValidPieceMove(Square startSquare, Square endSquare, Piece piece) {
        int rankDiff = endSquare.rank - startSquare.rank;
        int fileDiff = Math.abs(endSquare.file - startSquare.file);

        switch (piece.getType()) {
            case P: // Pawn
                int direction = (piece.getColor() == Piece.PieceColor.WHITE) ? 1 : -1;
                if (startSquare.file == endSquare.file) {
                    // Move forward
                    if (rankDiff == (1 * direction)) {
                        return true;
                    } else if (rankDiff == (2*direction) && (startSquare.rank == (direction == 1 ? 2 : 7))) {
                        // First move: can move two squares
                        return true;
                    }
                } else if (fileDiff == 1 && rankDiff == (1*direction)) {
                    // Capture diagonally
                    return endSquare.getPiece() != null;
                }
                return false;

            case R: // Rook
                return (rankDiff == 0 || fileDiff == 0);

            case N: // Knight
                return (Math.abs(rankDiff) == 2 && fileDiff == 1) || (Math.abs(rankDiff) == 1 && fileDiff == 2);

            case B: // Bishop
                return (Math.abs(rankDiff) == fileDiff);

            case Q: // Queen
                return (rankDiff == 0 || fileDiff == 0 || Math.abs(rankDiff) == fileDiff);

            case K: // King
                return (Math.abs(rankDiff) <= 1 && fileDiff <= 1);

            default:
                return false;
        }
    }

    private boolean isPathObstructed(Square startSquare, Square endSquare, Piece piece) {
        if (piece.getType() != TypeOfPiece.B && piece.getType() != TypeOfPiece.R && piece.getType() != TypeOfPiece.Q) {
            return false;
        }

        int rankStep = Integer.compare(endSquare.rank, startSquare.rank);
        int fileStep = Integer.compare(endSquare.file, startSquare.file);

        int currentRank = startSquare.rank + rankStep;
        int currentFile = startSquare.file + fileStep;

        while (currentRank != endSquare.rank || currentFile != endSquare.file) {
            if (this.grid[currentRank-1][currentFile-1].getPiece() != null) {
                return true; // Path is obstructed
            }
            currentRank += rankStep;
            currentFile += fileStep;
        }

        return false;
    }
}
