package chess; 

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

  // check if the piece can actually moved to (nRow, nCol)
  // I think we need another class for the rule/move logic
  public boolean isValid(int nRank, int nFile){
    /***TO BE COMPLETED***/
    return true; //Added just to avoid error
  }
  
}
