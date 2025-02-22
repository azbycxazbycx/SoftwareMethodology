package chess;

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
}
