package chess;

public class MoveValidator {
    private Board board;

    public MoveValidator(Board board) {
        this.board = board;
    }

    /**
     * Validates a move from (startRank, startFile) to (endRank, endFile).
     *
     * @param startRank The starting rank is ranging from  (1-8).
     * @param startFile The starting file (1-8).
     * @param endRank   The ending rank (1-8).
     * @param endFile   The ending file (1-8).
     * 
     * Return True if the move is valid, false otherwise.
     */

    public boolean isValidMove(int startRank, int startFile, int endRank, int endFile) {
        Square startSquare = board.getSquare(startRank, startFile);
        Square endSquare = board.getSquare(endRank, endFile);
        Piece movingPiece = startSquare.getPiece();

        // Check if there's a piece to move
        if (movingPiece == null) {
            return false;
        }

        // Check if the destination is the same as the starting position
        if (startRank == endRank && startFile == endFile) {
            return false;
        }

        // Check if the move is valid for the piece type
        if (!isValidPieceMove(movingPiece, startRank, startFile, endRank, endFile)) {
            return false;
        }

        // Check for obstructions (for rook, bishop, queen)
        if (isPathObstructed(startRank, startFile, endRank, endFile)) {
            return false;
        }

        // Check for capturing (cannot capture player's own piece)
        Piece destinationPiece = endSquare.getPiece();
        if (destinationPiece != null && destinationPiece.getColor() == movingPiece.getColor()) {
            return false;
        }

        // Handle special moves
        if (movingPiece.getType() == Piece.TypeOfPiece.P) {
            // En passant
            if (isEnPassant(startRank, startFile, endRank, endFile)) {
                return true;
            }
            // Pawn promotion
            if ((movingPiece.getColor() == Piece.PieceColor.WHITE && endRank == 8) ||
                (movingPiece.getColor() == Piece.PieceColor.BLACK && endRank == 1)) {
                // Promote the pawn 
                /**
                 * Need further implementation
                 */
                return true;
            }
        } else if (movingPiece.getType() == Piece.TypeOfPiece.K) {
            // Castling
            if (isCastling(startRank, startFile, endRank, endFile)) {
                return true;
            }
        }

        // Simulate the move and check if it leaves the king in check
        Board simulatedBoard = board.copy();
        simulatedBoard.movePiece(startRank, startFile, endRank, endFile);
        if (isKingInCheck(simulatedBoard, movingPiece.getColor())) {
            return false;
        }

        return true;
    }

    //Checks if the move is valid for the piece type.
    private boolean isValidPieceMove(Piece piece, int startRank, int startFile, int endRank, int endFile) {
        int rankDiff = Math.abs(endRank - startRank);
        int fileDiff = Math.abs(endFile - startFile);

        switch (piece.getType()) {
            case P: // Pawn
                int direction = (piece.getColor() == Piece.PieceColor.WHITE) ? 1 : -1;
                if (startFile == endFile) {
                    // Move forward
                    if (rankDiff == 1) {
                        return true;
                    } else if (rankDiff == 2 && (startRank == 2 || startRank == 7)) {
                        // First move: can move two squares
                        return true;
                    }
                } else if (fileDiff == 1 && rankDiff == 1) {
                    // Capture diagonally
                    return board.getSquare(endRank, endFile).getPiece() != null;
                }
                return false;

            case R: // Rook
                return (rankDiff == 0 || fileDiff == 0);

            case N: // Knight
                return (rankDiff == 2 && fileDiff == 1) || (rankDiff == 1 && fileDiff == 2);

            case B: // Bishop
                return (rankDiff == fileDiff);

            case Q: // Queen
                return (rankDiff == 0 || fileDiff == 0 || rankDiff == fileDiff);

            case K: // King
                return (rankDiff <= 1 && fileDiff <= 1);

            default:
                return false;
        }
    }

    // Checks if the path between two squares is obstructed.
    private boolean isPathObstructed(int startRank, int startFile, int endRank, int endFile) {
        int rankStep = Integer.compare(endRank, startRank);
        int fileStep = Integer.compare(endFile, startFile);

        int currentRank = startRank + rankStep;
        int currentFile = startFile + fileStep;

        while (currentRank != endRank || currentFile != endFile) {
            if (board.getSquare(currentRank, currentFile).getPiece() != null) {
                return true; // Path is obstructed
            }
            currentRank += rankStep;
            currentFile += fileStep;
        }

        return false;
    }

    /**
     * Checks if the move is an en passant capture.
     */
    private boolean isEnPassant(int startRank, int startFile, int endRank, int endFile) {
        // en passant logic (need to track the last move)
        /**
        * Need further implementation
        */
        return false;
    }

    /**
     * Checks if the move is a castling move.
     */
    private boolean isCastling(int startRank, int startFile, int endRank, int endFile) {
        // castling logic (need to track if the king/rook has moved)
        /**
        * Need further implementation
        */
        return false;
    }

    /**
     * Checks if the king of the given color is in check.
     */
    private boolean isKingInCheck(Board board, Piece.PieceColor color) {
        // Find the king's position
        int kingRank = -1, kingFile = -1;
        for (int rank = 1; rank <= 8; rank++) {
            for (int file = 1; file <= 8; file++) {
                Piece piece = board.getSquare(rank, file).getPiece();
                if (piece != null && piece.getType() == Piece.TypeOfPiece.K && piece.getColor() == color) {
                    kingRank = rank;
                    kingFile = file;
                    break;
                }
            }
        }

        if (kingRank == -1 || kingFile == -1) {
            return false; // King not found (which shouldnt be happening)
        }

        // Check if any opponent's piece can attack the king
        for (int rank = 1; rank <= 8; rank++) {
            for (int file = 1; file <= 8; file++) {
                Piece piece = board.getSquare(rank, file).getPiece();
                if (piece != null && piece.getColor() != color) {
                    if (isValidPieceMove(piece, rank, file, kingRank, kingFile) &&
                        !isPathObstructed(rank, file, kingRank, kingFile)) {
                        return true; // King is in check
                    }
                }
            }
        }

        return false;
    }
}
