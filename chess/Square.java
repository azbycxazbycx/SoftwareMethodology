package chess;

import chess.ReturnPiece.PieceFile;
import chess.ReturnPiece.PieceType;

public class Square {
    int rank;
    int file;
    Piece piece;
    public Square(int rank, int file) {
        this.rank = rank;
        this.file = file;
        this.piece = null;
    }
    public Square(int rank, int file, Piece piece) {
        this.rank = rank;
        this.file = file;
        this.piece = piece;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void placePiece(Piece piece) {
        this.piece = piece;
    }

    public void takePiece() {
        this.piece = null;
    }

    public PieceFile getFile() {
        if (this.file == 1) {
            return PieceFile.a;
        }
        else if (this.file == 2) {
            return PieceFile.b;
        }
        else if (this.file == 3) {
            return PieceFile.c;
        }
        else if (this.file == 4) {
            return PieceFile.d;
        }
        else if (this.file == 5) {
            return PieceFile.e;
        }
        else if (this.file == 6) {
            return PieceFile.f;
        }
        else if (this.file == 7) {
            return PieceFile.g;
        }
        else {
            return PieceFile.h;
        }

    }

    public ReturnPiece toReturnPiece() {
        if (this.piece == null) {
            return null;
        }
        ReturnPiece newReturnPiece = new ReturnPiece();
        newReturnPiece.pieceFile = getFile();
        newReturnPiece.pieceRank = this.rank;
        newReturnPiece.pieceType = this.piece.toPieceType();
        return newReturnPiece;
    }

    public String toString() {
        if (piece == null) {
            return "Empty square at rank " + rank + " file " + file;
        }
        return piece.toString() + " on square at rank " + rank + " file " + file;
    }
}
