package chess; 

public class Piece{
  String color; 
  int row; 
  int col; 

  // Constructor
  public Piece(String color, int row, int col){
    this.color = color; 
    this.row = row; 
    this.col = col; 
  }

  public String getColor() {
        return color;
    }

  public void setColor(String color) {
        this.color = color;
    }

  public int getRow() {
        return row;
    }

  public void setRow(int row) {
        this.row = row;
    }

  public int getCol() {
        return col;
    }

  public void setCol(int col) {
        this.col = col;
    }

  // check if the piece can actually moved to (nRow, nCol)
  // I think we need another class for the rule/move logic
  public boolean isValid(int nRow, int nCol){
    /***TO BE COMPLETED***/
  }
  
}
