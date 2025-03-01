package chess;

import chess.Piece.PieceColor;
import chess.Piece.TypeOfPiece;
import chess.ReturnPlay.Message;
import java.util.ArrayList;

public class Board{
    Square[][] grid;
    boolean isWhiteTurn;
    int turnNum;
    Piece canEnPassant = null;
    Message currMessage = null;

    // for tracking last move
    private String lastMove;
    
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

    public void moveInterpreter(String move) {

    }
    
    

    //I'm not sure how we are going to figure out all the steps when we move a piece 
    //but here's a basic starting point
    public void movePiece(int startRank, int startFile, int endRank, int endFile) {
        
        if (!isValidMove(startRank, startFile, endRank, endFile)) {
            System.out.println("Not a valid move");
            return;
        }
        System.out.println("AAAAA");
        //Piece that was on square, just in case we need to walk back the move
        Piece takenPiece = grid[endRank - 1][endFile - 1].getPiece();

        grid[endRank - 1][endFile - 1].placePiece(grid[startRank - 1][startFile - 1].getPiece());
        grid[startRank - 1][startFile - 1].takePiece();
            
        //After making the move, if your king is in check then move is reversed
        if (isKingInCheck()) {
            System.out.println("King is in check, not a valid move");
            grid[startRank-1][startFile-1].placePiece(grid[endRank - 1][endFile - 1].getPiece());
            grid[endRank-1][endFile-1].placePiece(takenPiece);
            return;
        }
    
        if (isWhiteTurn) {
            isWhiteTurn = false;
        }
        else {
            isWhiteTurn = true;
            turnNum++;
        }

        // Update the last move
        this.lastMove = String.format("%c%d %c%d", 
        (char) ('a' + startFile - 1), startRank, 
        (char) ('a' + endFile - 1), endRank);
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
        ArrayList<ReturnPiece> boardArrayList = new ArrayList<>();
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                if (grid[rank][file].getPiece() != null) {
                    System.out.println(grid[rank][file]);
                    boardArrayList.add(grid[rank][file].toReturnPiece());
                }
            }
        }
        return boardArrayList;
    }

    public Message getMessage() {
        return this.currMessage;
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
        
        if (endSquare.getPiece() != null) {
            if (endSquare.getPiece().getColor() == movingPiece.getColor()) {
                System.out.println("Cannot move piece into another friendly piece");
                return false;
            }
        }   

        //Checks if this type of piece can actually move in that way
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
                int direction = (piece.getColor() == PieceColor.WHITE) ? 1 : -1;
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

    //Called after moving piece, but before turn order is changed
    private boolean isKingInCheck() {
        PieceColor colorOfKing;
        PieceColor enemyColor;
        if (isWhiteTurn) {
            colorOfKing = PieceColor.WHITE;
            enemyColor = PieceColor.BLACK;
        }
        else {
            colorOfKing = PieceColor.BLACK;
            enemyColor = PieceColor.WHITE;
        }
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                Piece pieceOnCurrSquare = grid[rank][file].getPiece();
                if (pieceOnCurrSquare != null) {
                    if (pieceOnCurrSquare.getColor() == enemyColor) {
                        System.out.println(grid[rank][file]);
                        switch(pieceOnCurrSquare.getType()) {
                            case P:
                                if (pawnCheck(rank, file, colorOfKing, enemyColor)) return true;
                            case N:
                                if (knightCheck(rank, file, colorOfKing, enemyColor)) return true;
                            case B:
                                if (bishopCheck(rank, file, colorOfKing, enemyColor)) return true;
                            case R:
                                if (rookCheck(rank, file, colorOfKing, enemyColor)) return true;
                            case Q:
                                if (bishopCheck(rank, file, colorOfKing, enemyColor) || rookCheck(rank, file, colorOfKing, enemyColor)) return true;
                            default:
                                System.out.println("Error: piece with no type");
                                continue;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean pawnCheck(int rank, int file, PieceColor colorOfKing, PieceColor enemyColor) {
        int direction = (enemyColor == PieceColor.WHITE) ? 1 : -1;
        if (file - 1 >= 0) {
            Piece targetPiece = grid[rank + direction][file-1].getPiece();
            if (targetPiece != null) {
                if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                    System.out.println(grid[rank + direction][file-1]);
                    return true;
                }
            }
        }
        if (file + 1 < 8) {
            Piece targetPiece = grid[rank + direction][file+1].getPiece();
            if (targetPiece != null) {
                if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                    System.out.println(grid[rank + direction][file-1]);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean knightCheck(int rank, int file, PieceColor colorOfKing, PieceColor enemyColor) {
        if (file - 2 >= 0) {
            if (rank - 1 >= 0) {
                Piece targetPiece = grid[rank-1][file-2].getPiece();
                if (targetPiece != null) {
                    if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                        System.out.println(grid[rank-1][file-2]);
                        return true;
                    }
                }
            }
            if (rank + 1 < 8) {
                Piece targetPiece = grid[rank+1][file-2].getPiece();
                if (targetPiece != null) {
                    if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                        System.out.println(grid[rank+1][file-2]);
                        return true;
                    }
                }

            }
        }
        if (file + 2 < 8) {
            if (rank - 1 >= 0) {
                Piece targetPiece = grid[rank-1][file+2].getPiece();
                if (targetPiece != null) {
                    if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                        System.out.println(grid[rank-1][file+2]);
                        return true;
                    }
                }
            }
            if (rank + 1 < 8) {
                Piece targetPiece = grid[rank+1][file+2].getPiece();
                if (targetPiece != null) {
                    if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                        System.out.println(grid[rank+1][file+2]);
                        return true;
                    }
                }
            }
        }
        if (rank - 2 >= 0) {
            if (file - 1 >= 0) {
                Piece targetPiece = grid[rank-2][file-1].getPiece();
                if (targetPiece != null) {
                    if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                        System.out.println(grid[rank-2][file-1]);
                        return true;
                    }
                }
            }
            if (file + 1 < 8) {
                Piece targetPiece = grid[rank-2][file+1].getPiece();
                if (targetPiece != null) {
                    if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                        System.out.println(grid[rank-2][file+1]);
                        return true;
                    }
                }
            }
        }
        if (rank + 2 < 8) {
            if (file - 1 >= 0) {
                Piece targetPiece = grid[rank+2][file-1].getPiece();
                if (targetPiece != null) {
                    if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                        System.out.println(grid[rank+2][file-1]);
                        return true;
                    }
                }
            }
            if (file + 1 < 8) {
                Piece targetPiece = grid[rank+2][file+1].getPiece();
                if (targetPiece != null) {
                    if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                        System.out.println(grid[rank+2][file+1]);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean bishopCheck(int rank, int file, PieceColor colorOfKing, PieceColor enemyColor) {
        int currRank = rank + 1;
        int currFile = file + 1;
        while (currRank < 8 && currFile < 8) {
            Piece targetPiece = grid[currRank][currFile].getPiece();
            if (targetPiece != null) {
                if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                    System.out.println(grid[currRank][currFile]);
                    return true;
                }else {
                break;
                }
            }
            currRank++;
            currFile++;
        }

        currRank = rank + 1;
        currFile = file - 1;
        while (currRank < 8 && currFile >= 0) {
            Piece targetPiece = grid[currRank][currFile].getPiece();
            if (targetPiece != null) {
                if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                    System.out.println(grid[currRank][currFile]);
                    return true;
                }else {
                    break;
                }
            }
            currRank++;
            currFile--;
        }

        currRank = rank - 1;
        currFile = file + 1;
        while (currRank >= 0 && currFile < 8) {
            Piece targetPiece = grid[currRank][currFile].getPiece();
            if (targetPiece != null) {
                if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                    System.out.println(grid[currRank][currFile]);
                    return true;
                }else {
                    break;
                }
            }
            currRank--;
            currFile++;
        }

        currRank = rank - 1;
        currFile = file - 1;
        while (currRank >= 0 && currFile >= 0) {
            Piece targetPiece = grid[currRank][currFile].getPiece();
            if (targetPiece != null) {
                if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                    System.out.println(grid[currRank][currFile]);
                    return true;
                }else {
                    break;
                }
            }
            currRank--;
            currFile--;
        }
        return false;
    }

    private boolean rookCheck(int rank, int file, PieceColor colorOfKing, PieceColor enemyColor) {
        int currRank = rank + 1;
        int currFile = file;
        while (currRank < 8) {
            Piece targetPiece = grid[currRank][currFile].getPiece();
            if (targetPiece != null) {
                if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                    System.out.println(grid[currRank][currFile]);
                    return true;
                }else {
                break;
                }
            }
            currRank++;
        }

        currRank = rank - 1;
        currFile = file;
        while (currRank >= 0) {
            Piece targetPiece = grid[currRank][currFile].getPiece();
            if (targetPiece != null) {
                if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                    System.out.println(grid[currRank][currFile]);
                    return true;
                }else {
                    break;
                }
            }
            currRank--;
        }

        currRank = rank;
        currFile = file + 1;
        while (currFile < 8) {
            Piece targetPiece = grid[currRank][currFile].getPiece();
            if (targetPiece != null) {
                if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                    System.out.println(grid[currRank][currFile]);
                    return true;
                }else {
                    break;
                }
            }
            currFile++;
        }

        currRank = rank;
        currFile = file - 1;
        while (currFile >= 0) {
            Piece targetPiece = grid[currRank][currFile].getPiece();
            if (targetPiece != null) {
                if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                    System.out.println(grid[currRank][currFile]);
                    return true;
                }else {
                    break;
                }
            }
            currFile--;
        }
        return false;
    }
}
