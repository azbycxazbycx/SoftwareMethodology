package chess;

import chess.Piece.PieceColor;
import chess.Piece.TypeOfPiece;
import chess.ReturnPlay.Message;
import java.util.ArrayList;
import java.util.Scanner;

public class Board{
    Square[][] grid;
    boolean isWhiteTurn;
    int turnNum;
    boolean canEnPassant = false;
    boolean isEnPassantHappening = false;
    boolean isCastleMove = false;
    boolean isPromotionMove = false;
    Message currMessage = null;
    Square enPassantSquare = null; // Current en passant square
    Square tempEnPassantSquare = null; // Temporary en passant square for validation
    Square targetKingSquare = null; //Square where enemy king is, used to check for checkmate
    TypeOfPiece promotionType = null; //If the pawn promotes, will promote to this


    
    
    // Constructor
    public Board() {
        this.grid = new Square[8][8];
        this.isWhiteTurn = true;
        this.turnNum = 1;
        this.enPassantSquare = null;
        this.tempEnPassantSquare = null;
        initializeBoard();
    }

    /* en passant field part*/
    
    // Reset the en passant square at the start of each turn
    public void resetEnPassantSquare() {
        this.enPassantSquare = null;
    }

    // Getter and setter for enPassantSquare
    public Square getEnPassantSquare() {
        return this.enPassantSquare;
    }

    public void setEnPassantSquare(Square square) {
        this.enPassantSquare = square;
    }

    // for tempEnPassantSquare
    public Square getTempEnPassantSquare() {
        return this.tempEnPassantSquare;
    }

    public void setTempEnPassantSquare(Square square) {
        this.tempEnPassantSquare = square;
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
        
        if (!isValidMove(startRank, startFile, endRank, endFile)) {
            System.out.println("Not a valid move");
            setMessage(Message.ILLEGAL_MOVE);
            return;
        }
        //System.out.println("AAAAA");

    // ============================================
    // CASTLING CHECK
    // ============================================
        Piece movingPiece = grid[startRank - 1][startFile - 1].getPiece();

    /* 
    // Check if the move is a castling move (king moves two squares horizontally)
    if (movingPiece != null && movingPiece.getType() == TypeOfPiece.K && Math.abs(endFile - startFile) == 2) {
    */
        if (isCastleMove) {
            //canCastle is now called within isValidMove

            /* 
            // Check if castling is allowed
            if (!canCastle(startRank, startFile, endRank, endFile)) {
                System.out.println("Castling is not allowed.");
                return;
            }
            */

            // Move the rook
            int rookFile = (endFile > startFile) ? 8 : 1; // Rook's starting file (h for kingside, a for queenside)
            int newRookFile = (endFile > startFile) ? endFile - 1 : endFile + 1; // Rook's new file (f for kingside, d for queenside)

            // Move the rook to its new position
            grid[endRank - 1][newRookFile - 1].placePiece(grid[startRank - 1][rookFile - 1].getPiece());
            // Remove the rook from its old position
            grid[startRank - 1][rookFile - 1].takePiece();
            isCastleMove = false;

            //Since canCastle already checks if the king is in check we can bypass isKingInCheck
            grid[endRank - 1][endFile - 1].placePiece(movingPiece);
            grid[startRank - 1][startFile - 1].takePiece();

            if (isWhiteTurn) {
                isWhiteTurn = false;
            }
            else {
                isWhiteTurn = true;
                turnNum++;
            }
    
            //Now that we've switched turn order, we can check if the enemy king is in check, and maybe also checkmate
            targetKingSquare = null;
            if (isKingInCheck()) {
                System.out.println(targetKingSquare);
            }
            return;
        }

        //Piece that was on square, just in case we need to walk back the move
        Piece takenPiece;

        //If statement for special case where taken piece isn't where the piece we are moving is landing during en passant
        if (isEnPassantHappening) {
            if(enPassantSquare == null) {
                System.out.println("Something wrong happened with the en passant system, please bugfix");
                return;
            }
            takenPiece = enPassantSquare.getPiece();
            enPassantSquare.takePiece();
        }
        else {
            takenPiece = grid[endRank - 1][endFile - 1].getPiece();
        }
        

        grid[endRank - 1][endFile - 1].placePiece(movingPiece);
        grid[startRank - 1][startFile - 1].takePiece();

    
            
        //After making the move, if your king is in check then move is reversed
        if (isKingInCheck()) {
            System.out.println("King is in check, not a valid move");
            grid[startRank-1][startFile-1].placePiece(grid[endRank - 1][endFile - 1].getPiece());
            if (isEnPassantHappening) {
                if (enPassantSquare == null) {
                    System.out.println("Something else went wrong # 2 with en passant system, please bugfix");
                    return;
                }
                enPassantSquare.placePiece(takenPiece);
            }
            else {
                grid[endRank-1][endFile-1].placePiece(takenPiece);
            }
            isPromotionMove = false;
            promotionType = null;
            return;
        }
        if (canEnPassant) {
            
            enPassantSquare = grid[endRank-1][endFile-1];
        }
        else {
            setEnPassantSquare(null);
            canEnPassant = false;
        }
        
        isEnPassantHappening = false;

        if (isPromotionMove) {
            if (promotionType == null) movingPiece.setType(TypeOfPiece.Q);
            else movingPiece.setType(promotionType);
        }
        isPromotionMove = false;
        promotionType = null;

        if (movingPiece == null) {
            System.out.println("For some reason the code has gotten to this point without error yet movingPiece is null");
            return;
        }
        else {
            movingPiece.hasMoved = true;
        }
    
        if (isWhiteTurn) {
            isWhiteTurn = false;
        }
        else {
            isWhiteTurn = true;
            turnNum++;
        }
        if (getMessage() != Message.DRAW) {
            setMessage(null);
        }
        //Now that we've switched turn order, we can check if the enemy king is in check, and maybe also checkmate
        targetKingSquare = null;
        if (isKingInCheck()) {
            setMessage(Message.CHECK);
            System.out.println(targetKingSquare + " is in check");
        }
        
        
    } 

    //Will check if current position requires a message like "check"
    public Message checkMessage() {
        //TODO
        return null;
    }

    //Not sure if we need this but it'll basically just reset everything to the start position
    public void resetBoard() {
        this.isWhiteTurn = true;
        this.turnNum = 1;
        canEnPassant = false;
        isEnPassantHappening = false;
        isCastleMove = false;
        isPromotionMove = false;
        currMessage = null;
        enPassantSquare = null; // Current en passant square
        tempEnPassantSquare = null; // Temporary en passant square for validation
        targetKingSquare = null; //Square where enemy king is, used to check for checkmate
        initializeBoard();
    }

    //It seems like they ultimately want us to return an arraylist from the returnplay class, so this'll convert the board
    public ArrayList<ReturnPiece> printPosition() {
        ArrayList<ReturnPiece> boardArrayList = new ArrayList<>();
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                if (grid[rank][file].getPiece() != null) {
                    //System.out.println(grid[rank][file]);
                    boardArrayList.add(grid[rank][file].toReturnPiece());
                }
            }
        }
        return boardArrayList;
    }

    public void setMessage(Message message) {
        this.currMessage = message;
    }

    public Message getMessage() {
        return this.currMessage;
    }

    public void resign() {
        if (isWhiteTurn) {
            setMessage(Message.RESIGN_WHITE_WINS);
        }
        else {
            setMessage(Message.RESIGN_BLACK_WINS);
        }
    }

    public boolean isValidMove(int startRank, int startFile, int endRank, int endFile) {
        if (startRank < 1 || startRank > 8 || endRank < 1 || endRank > 8) {
            System.out.println("Error: Move not in range of board");
            return false;
        }
        Square startSquare = this.grid[startRank-1][startFile-1];
        Square endSquare = this.grid[endRank-1][endFile-1];
        Piece movingPiece = startSquare.getPiece();

        /* 
        System.out.println("__________");
        System.out.println(startSquare);
        System.out.println(endSquare);
        System.out.println(movingPiece);
        System.out.println("__________");
        */
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

        /* 
        System.out.println("$$$$$$$$$");
        System.out.println(startSquare);
        System.out.println(endSquare);
        System.out.println(piece);
        System.out.println("$$$$$$$$$");
        */
        
        switch (piece.getType()) {
            case P: // Pawn
                int direction = (piece.getColor() == PieceColor.WHITE) ? 1 : -1;
                if (startSquare.file == endSquare.file) {
                    // Move forward
                    if (endSquare.getPiece() != null) {
                        //System.out.println("Pawns cannot capture vertically");
                        return false;
                    }
                    if (rankDiff == (1 * direction)) {
                        if (endSquare.rank == 1 || endSquare.rank == 8) {
                            isPromotionMove = true;
                        }
                        return true;
                    } else if (rankDiff == (2*direction) && (startSquare.rank == (direction == 1 ? 2 : 7))) {
                        // First move: can move two squares

                        //Checks to see that there isn't another piece blocking the pawn inbetween moving 2 spaces
                        if (grid[startSquare.rank-1+direction][startSquare.file-1].getPiece() != null) {
                            System.out.println("There is a piece blocking the pawn from moving");
                            return false;
                        }
                        canEnPassant = true;
                        return true;
                    }
                } else if (fileDiff == 1 && rankDiff == (1*direction)) {
                    // Capture diagonally
                    if (endSquare.equals(grid[enPassantSquare.rank-1 + direction][enPassantSquare.file-1])) {
                        isEnPassantHappening = true;
                        return true;
                    }
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
                if (Math.abs(endSquare.file - startSquare.file) == 2 && Math.abs(endSquare.rank - startSquare.rank) == 0) {
                    isCastleMove = true;
                    return canCastle(startSquare.rank, startSquare.file, endSquare.rank, endSquare.file);
                }
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
                        //System.out.println(grid[rank][file]);  
                        switch(pieceOnCurrSquare.type) {
                            case P:
                                if (pawnCheck(rank, file, colorOfKing, enemyColor)) return true;
                                break;
                            case N:
                                if (knightCheck(rank, file, colorOfKing, enemyColor)) return true;
                                break;
                            case B:
                                if (bishopCheck(rank, file, colorOfKing, enemyColor)) return true;
                                break;
                            case R:
                                if (rookCheck(rank, file, colorOfKing, enemyColor)) return true;
                                break;
                            case Q:
                                if (bishopCheck(rank, file, colorOfKing, enemyColor) || rookCheck(rank, file, colorOfKing, enemyColor)) return true;
                                break;
                            case K:
                                break;
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
                    targetKingSquare = grid[rank + direction][file-1];
                    //System.out.println(grid[rank + direction][file-1]);
                    return true;
                }
            }
        }
        if (file + 1 < 8) {
            Piece targetPiece = grid[rank + direction][file+1].getPiece();
            if (targetPiece != null) {
                if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                    targetKingSquare = grid[rank + direction][file+1];
                    //System.out.println(grid[rank + direction][file-1]);
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
                        targetKingSquare = grid[rank-1][file-2];
                        //System.out.println(grid[rank-1][file-2]);
                        return true;
                    }
                }
            }
            if (rank + 1 < 8) {
                Piece targetPiece = grid[rank+1][file-2].getPiece();
                if (targetPiece != null) {
                    if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                        targetKingSquare = grid[rank+1][file-2];
                        //System.out.println(grid[rank+1][file-2]);
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
                        targetKingSquare = grid[rank-1][file+2];
                        //System.out.println(grid[rank-1][file+2]);
                        return true;
                    }
                }
            }
            if (rank + 1 < 8) {
                Piece targetPiece = grid[rank+1][file+2].getPiece();
                if (targetPiece != null) {
                    if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                        targetKingSquare = grid[rank+1][file+2];
                        //System.out.println(grid[rank+1][file+2]);
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
                        targetKingSquare = grid[rank-2][file-1];
                        //System.out.println(grid[rank-2][file-1]);
                        return true;
                    }
                }
            }
            if (file + 1 < 8) {
                Piece targetPiece = grid[rank-2][file+1].getPiece();
                if (targetPiece != null) {
                    if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                        targetKingSquare = grid[rank-2][file+1];
                        //System.out.println(grid[rank-2][file+1]);
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
                        targetKingSquare = grid[rank+2][file-1];
                        //System.out.println(grid[rank+2][file-1]);
                        return true;
                    }
                }
            }
            if (file + 1 < 8) {
                Piece targetPiece = grid[rank+2][file+1].getPiece();
                if (targetPiece != null) {
                    if (targetPiece.getColor() == colorOfKing && targetPiece.getType() == TypeOfPiece.K) {
                        targetKingSquare = grid[rank+2][file+1];
                        //System.out.println(grid[rank+2][file+1]);
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
                    targetKingSquare = grid[currRank][currFile];
                    //System.out.println(grid[currRank][currFile]);
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
                    targetKingSquare = grid[currRank][currFile];
                    //System.out.println(grid[currRank][currFile]);
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
                    targetKingSquare = grid[currRank][currFile];
                    //System.out.println(grid[currRank][currFile]);
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
                    targetKingSquare = grid[currRank][currFile];
                    //System.out.println(grid[currRank][currFile]);
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
                    targetKingSquare = grid[currRank][currFile];
                    //System.out.println(grid[currRank][currFile]);
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
                    targetKingSquare = grid[currRank][currFile];
                    //System.out.println(grid[currRank][currFile]);
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
                    targetKingSquare = grid[currRank][currFile];
                    //System.out.println(grid[currRank][currFile]);
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
                    targetKingSquare = grid[currRank][currFile];
                    //System.out.println(grid[currRank][currFile]);
                    return true;
                }else {
                    break;
                }
            }
            currFile--;
        }
        return false;
    }

    /* 
    //For Pawn Promotion
    private void promotePawn(Square square) {
        if (square.getPiece() == null || square.getPiece().getType() != TypeOfPiece.P) {
            return; // Not a pawn, then no promotion needed
        }

        Piece pawn = square.getPiece();
        int promotionRank = (pawn.getColor() == PieceColor.WHITE) ? 8 : 1;

        if (square.rank == promotionRank) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Pawn promotion! Choose a piece (Q, R, B, N):");
            String input = scanner.nextLine().toUpperCase();

            TypeOfPiece newType;
            switch (input) {
                case "Q":
                    newType = TypeOfPiece.Q;
                    break;
                case "R":
                    newType = TypeOfPiece.R;
                    break;
                case "B":
                    newType = TypeOfPiece.B;
                    break;
                case "N":
                    newType = TypeOfPiece.N;
                    break;
                default:
                    System.out.println("Invalid choice. Promoting to Queen by default.");
                    newType = TypeOfPiece.Q;
                    break;
            }
            scanner.close();

            // Replace the pawn with the new piece
            square.placePiece(new Piece(pawn.getColor(), newType));
        }
    }
    */
    private boolean isKingInCheckmate() {
        
        if (targetKingSquare == null) return false;
        
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
        if (targetKingSquare.getPiece().getType() != TypeOfPiece.K) return false;
        if (targetKingSquare.getPiece().getColor() != colorOfKing) return false;

        /* 
        for (int rankDiff = -1; rankDiff <= 1; rankDiff++) {
            int currRank = targetKingSquare.rank + rankDiff;
            if (currRank <= 0 || currRank > 8) {
                continue;
            }
            for (int fileDiff = -1; fileDiff <= 1; fileDiff++) {
                int currFile = targetKingSquare.file + fileDiff;
                if ((currFile <= 0 || currFile > 8) || (rankDiff == 0 || fileDiff == 0)) {
                    continue;
                }
                if (grid[currRank-1][currFile-1].getPiece() != null) {
                    if (grid[currRank-1][currFile-1].getPiece().getColor() == colorOfKing) {
                        continue;
                    }
                }
                if (!isSquareUnderAttack(currRank, currFile, colorOfKing)) continue;
            }
        }
        */
    }

    // check castling condition
    private boolean canCastle(int startRank, int startFile, int endRank, int endFile) {
        Piece movingPiece = grid[startRank - 1][startFile - 1].getPiece();
        if (movingPiece == null || movingPiece.getType() != TypeOfPiece.K) {
            return false; // Only the king can castle
        }
        if (movingPiece.hasMoved) {
            return false;
        }

        int direction = endFile > startFile ? 1 : -1; // 1 for kingside, -1 for queenside
        int rookFile = (direction == 1) ? 8 : 1; // Rook's starting file

        // Check if the king and rook are in their starting positions
        Square kingSquare = grid[startRank - 1][startFile - 1];
        Square rookSquare = grid[startRank - 1][rookFile - 1];
        if (rookSquare.getPiece().hasMoved) {
            return false;
        }

        if (kingSquare.getPiece() == null || rookSquare.getPiece() == null ||
            kingSquare.getPiece().getType() != TypeOfPiece.K ||
            rookSquare.getPiece().getType() != TypeOfPiece.R) {
            return false; // King or rook not in starting position
        }

        // Check if the squares between the king and rook are empty
        for (int file = startFile + direction; file != rookFile; file += direction) {
            if (grid[startRank - 1][file - 1].getPiece() != null) {
                return false;
            }
        }

        // Check if the king is not in check and does not move through a square under attack
        for (int file = startFile; file != endFile + direction; file += direction) {
            if (isSquareUnderAttack(startRank, file, movingPiece.getColor())) {
                return false;
            }
        }

        return true;
    }

    private boolean isSquareUnderAttack(int rank, int file, PieceColor color) {
    // Check if the square is under attack by any enemy piece
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                Piece piece = grid[r][f].getPiece();
                if (piece != null && piece.getColor() != color) {
                    if (isValidPieceMove(grid[r][f], grid[rank - 1][file - 1], piece) && !isPathObstructed(grid[r][f], grid[rank-1][file-1],piece)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setPromotionType(TypeOfPiece type) {
        this.promotionType = type;
    }

    public TypeOfPiece getPromotionType() {
        return this.promotionType;
    }
    
}
