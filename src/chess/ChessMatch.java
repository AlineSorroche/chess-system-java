package chess;

import boardgame.Board;

public class ChessMatch {//a classe chasMatch tem que saber a dimensão do tabuleiro, por isso vou especificar 8,8
	
	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8);
	}
	
	public ChessPiece[][] getPieces() {// esse método vai retornar uma matriz de peças de chadrez correspondente a essa partida
       ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
       for (int i=0; i<board.getRows(); i++) {
    	   for (int j=0; j<board.getColumns(); j++) {
    		   mat[i][j] = (ChessPiece) board.piece(i,j);//downcast para interpretar como uma peça de xadrez e não uma peça comum.
    	   }
       }
       return mat;
	}
}
