package chess;

import boardgame.Position;

//outro sistema de coordenadas
public class ChessPosition {
	
	private char column;
	private int row;
	public ChessPosition(char column, int row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) { // testa as posi��es
			throw new ChessException("Error instantiating ChessPosition. Valid values are from a1 to h8.");
		}
		this.column = column;
		this.row = row;
	}
	public char getColumn() {
		return column;
	}
	//apago os set pois n�o vou deixar que alterem as colunas e linhas
	
	public int getRow() {
		return row;
	}
	
	//para descobrir a posi��o das linhas na matriz a f�rmula � matrix_row = 8 - chess_row
	//para descobrir a posi��o das colunas na matriz a f�rmula � chess_column - 'a' (pois 'a' - 'a' = 0; 'b' - 'a' = 1; 'c' - 'a' = 2...)
	
	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}
    
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char)('a' - position.getColumn()), 8 - position.getRow());
	}
	
	@Override
	public String toString() {
		return "" + column + row; // esse "" � para o for�ar o compilador a enteder que isso � uma concatena��o de strings
	}
	
}
