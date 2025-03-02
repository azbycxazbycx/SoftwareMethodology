package chess;

import chess.Piece.PieceColor;
import chess.Piece.TypeOfPiece;
import chess.ReturnPlay.Message;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    Square[][] grid;
    boolean isWhiteTurn;
    int turnNum;
    boolean canEnPassant = false;
    boolean isEnPassantHappening = false;
    Message currMessage = null;
    Square enPassantSquare = null; // Current en passant square
    Square tempEnPassantSquare = null; // Temporary en passant square for validation

    // for tracking last move
    private String lastMove;

    // Constructor
    public Board() {
        this.grid = new Square[8][8];
        this.isWhiteTurn = true;
        this.turnNum = 1;
        this.enPassantSquare = null;
        this.tempEnPassantSquare = null;
        initializeBoard();
    }

    /* en passant field part */

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
        // Initialize the board with pieces in their starting positions
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
                this.grid[i][j] = new Square(i + 1, j + 1);
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
        // Implement move interpreter logic here
    }

    public void movePiece(int startRank, int startFile, int endRank, int endFile) {
        if (!isValidMove(startRank, startFile, endRank, endFile)) {
            System.out.println("Not a valid move");
            return;
        }

        Piece movingPiece = grid[startRank - 1][startFile - 1].getPiece();

        // ============================================
        // CASTLING LOGIC
        // ============================================
        if (movingPiece != null && movingPiece.getType() == TypeOfPiece.K && Math.abs(endFile - startFile) == 2) {
            // Move the rook
            int rookFile = (endFile > startFile) ? 8 : 1; // Rook's starting file (h for kingside, a for queenside)
            int newRookFile = (endFile > startFile) ? endFile - 1 : endFile + 1; // Rook's new file (f for kingside, d for queenside)

            // Move the rook to its new position
            grid[endRank - 1][newRookFile - 1].placePiece(grid[startRank - 1][rookFile - 1].getPiece());
            // Remove the rook from its old position
            grid[startRank - 1][rookFile - 1].takePiece();
        }

        // Handle the case where a piece is taken (e.g., during en passant or regular capture)
        Piece takenPiece;
        if (isEnPassantHappening) {
            if (enPassantSquare == null) {
                System.out.println("Something wrong happened with the en passant system, please bugfix");
                return;
            }
            takenPiece = enPassantSquare.getPiece();
            enPassantSquare.takePiece();
        } else {
            takenPiece = grid[endRank - 1][endFile - 1].getPiece();
        }

        // Move the piece to the new square
        grid[endRank - 1][endFile - 1].placePiece(grid[startRank - 1][startFile - 1].getPiece());
        // Remove the piece from the old square
        grid[startRank - 1][startFile - 1].takePiece();

        // Handle pawn promotion
        promotePawn(grid[endRank - 1][endFile - 1]);

        // After making the move, if your king is in check then move is reversed
        if (isKingInCheck()) {
            System.out.println("King is in check, not a valid move");
            grid[startRank - 1][startFile - 1].placePiece(grid[endRank - 1][endFile - 1].getPiece());
            if (isEnPassantHappening) {
                if (enPassantSquare == null) {
                    System.out.println("Something else went wrong #2 with en passant system, please bugfix");
                    return;
                }
                enPassantSquare.placePiece(takenPiece);
            } else {
                grid[endRank - 1][endFile - 1].placePiece(takenPiece);
            }
            return;
        }

        // Handle en passant
        if (canEnPassant) {
            enPassantSquare = grid[endRank - 1][endFile - 1];
        } else {
            setEnPassantSquare(null);
            canEnPassant = false;
        }

        // Reset en passant flag
        isEnPassantHappening = false;

        // Switch turns
        if (isWhiteTurn) {
            isWhiteTurn = false;
        } else {
            isWhiteTurn = true;
            turnNum++;
        }

        // Update the last move
        this.lastMove = String.format("%c%d %c%d",
                (char) ('a' + startFile - 1), startRank,
                (char) ('a' + endFile - 1), endRank);
    }

    // Check if the current position requires a message like "check"
    public Message checkMessage() {
        return null;
    }

    // Reset the board to the starting position
    public void resetBoard() {
        this.isWhiteTurn = true;
        this.turnNum = 1;
        initializeBoard();
    }

    // Convert the board to an ArrayList of ReturnPiece objects
    public ArrayList<ReturnPiece> printPosition() {
        ArrayList<ReturnPiece> boardArrayList = new ArrayList<>();
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                if (grid[rank][file].getPiece() != null) {
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
        Square startSquare = this.grid[startRank - 1][startFile - 1];
        Square endSquare = this.grid[endRank - 1][endFile - 1];
        Piece movingPiece = startSquare.getPiece();
        if (movingPiece == null) {
            System.out.println("No piece to move");
            return false;
        }
        if (startRank == endRank && startFile == endFile) {
            System.out.println("Cannot move to starting square");
            return false;
        }

        // Check if the move is a castling move
        if (movingPiece.getType() == TypeOfPiece.K && Math.abs(endFile - startFile) == 2) {
            return canCastle(startRank, startFile, endRank, endFile);
        }

        // Rest of the validation logic...
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

        // Check if this type of piece can actually move in that way
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
                    if (endSquare.getPiece() != null) {
                        System.out.println("Pawns cannot capture vertically");
                        return false;
                    }
                    if (rankDiff == (1 * direction)) {
                        return true;
                    } else if (rankDiff == (2 * direction) && (startSquare.rank == (direction == 1 ? 2 : 7))) {
                        // First move: can move two squares
                        if (grid[startSquare.rank - 1 + direction][startSquare.file - 1].getPiece() != null) {
                            System.out.println("There is a piece blocking the pawn from moving");
                            return false;
                        }
                        canEnPassant = true;
                        return true;
                    }
                } else if (fileDiff == 1 && rankDiff == (1 * direction)) {
                    // Capture diagonally
                    if (endSquare.equals(grid[enPassantSquare.rank - 1 + direction][enPassantSquare.file - 1])) {
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
                // Allow standard king moves (1 square) and castling moves (2 squares horizontally)
                return (Math.abs(rankDiff) <= 1 && fileDiff <= 1) || (rankDiff == 0 && fileDiff == 2);

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
            if (this.grid[currentRank - 1][currentFile - 1].getPiece() != null) {
                return true; // Path is obstructed
            }
            currentRank += rankStep;
            currentFile += fileStep;
        }

        return false;
    }

    // Check if the king is in check
    private boolean isKingInCheck() {
        PieceColor colorOfKing = isWhiteTurn ? PieceColor.WHITE : PieceColor.BLACK;
        PieceColor enemyColor = isWhiteTurn ? PieceColor.BLACK : PieceColor.WHITE;

        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                Piece pieceOnCurrSquare = grid[rank][file].getPiece();
                if (pieceOnCurrSquare != null && pieceOnCurrSquare.getColor() == enemyColor) {
                    if (isValidPieceMove(grid[rank][file], grid[getKingSquare(colorOfKing).rank - 1][getKingSquare(colorOfKing).file - 1], pieceOnCurrSquare)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Helper method to find the king's square
    private Square getKingSquare(PieceColor color) {
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                Piece piece = grid[rank][file].getPiece();
                if (piece != null && piece.getType() == TypeOfPiece.K && piece.getColor() == color) {
                    return grid[rank][file];
                }
            }
        }
        return null;
    }

    // Check if castling is allowed
    private boolean canCastle(int startRank, int startFile, int endRank, int endFile) {
        Piece movingPiece = grid[startRank - 1][startFile - 1].getPiece();
        if (movingPiece == null || movingPiece.getType() != TypeOfPiece.K) {
            return false; // Only the king can castle
        }

        int direction = endFile > startFile ? 1 : -1; // 1 for kingside, -1 for queenside
        int rookFile = (direction == 1) ? 8 : 1; // Rook's starting file

        // Check if the king and rook are in their starting positions
        Square kingSquare = grid[startRank - 1][startFile - 1];
        Square rookSquare = grid[startRank - 1][rookFile - 1];

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

    // Check if a square is under attack
    private boolean isSquareUnderAttack(int rank, int file, PieceColor color) {
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                Piece piece = grid[r][f].getPiece();
                if (piece != null && piece.getColor() != color) {
                    if (isValidPieceMove(grid[r][f], grid[rank - 1][file - 1], piece)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Handle pawn promotion
    private void promotePawn(Square square) {
        if (square.getPiece() == null || square.getPiece().getType() != TypeOfPiece.P) {
            return; // Not a pawn, no promotion needed
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

            // Replace the pawn with the new piece
            square.placePiece(new Piece(pawn.getColor(), newType));
        }
    }
}
