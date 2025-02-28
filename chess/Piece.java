package chess;

import chess.ReturnPiece.PieceType;
import chess.ReturnPiece.PieceType;

public class Piece{
  enum PieceColor {WHITE, BLACK, BLANK}
  enum TypeOfPiece {P, R, N, B, Q, K, None};

  PieceColor color; 
  TypeOfPiece type;

  // Constructor
  public Piece(PieceColor color, TypeOfPiece type){
    this.color = color; 
    this.type = type;
  }

  public PieceColor getColor() {
    return this.color;
  }

  public void setColor(PieceColor color) {
    this.color = color;
  }


  public TypeOfPiece getType() {
    return this.type;
  }

  public void setType(TypeOfPiece type) {
    this.type = type;
  }

  public void setBlank() {
    this.color = PieceColor.BLANK; 
    this.type = TypeOfPiece.None;
  }

  public PieceType toPieceType() {
    if (this.color == PieceColor.WHITE) {
      switch(this.type) {
        case P:
          return PieceType.WP;
        case N:
          return PieceType.WN;
        case B:
          return PieceType.WB;
        case R:
          return PieceType.WR;
        case Q:
          return PieceType.WQ;
        case K:
          return PieceType.WK;
        default:
          return null;
      }
    }
    else if (this.color == PieceColor.BLACK) {
      switch(this.type) {
        case P:
          return PieceType.BP;
        case N:
          return PieceType.BN;
        case B:
          return PieceType.BB;
        case R:
          return PieceType.BR;
        case Q:
          return PieceType.BQ;
        case K:
          return PieceType.BK;
        default:
          return null;
      }
    }
    return null;
  }

  public String toString() {
    String color = "Blank";
    if (this.color == PieceColor.WHITE) {
      color = "White";
    }
    else if (this.color == PieceColor.BLACK) {
      color = "Black";
    }
    switch(this.type) {
      case P:
        return color + "pawn";
      case N:
        return color + "knight";
      case B:
        return color + "bishop";
      case R:
        return color + "rook";
      case Q:
        return color + "queen";
      case K:
        return color + "king";
      default:
        return "Nothing";
    }
  }
  
}
