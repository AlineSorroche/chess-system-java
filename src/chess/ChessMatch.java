package chess;

import boardgame.Board;

public class ChessMatch {//a classe chasMatch tem que saber a dimens�o do tabuleiro, por isso vou especificar 8,8
	
	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8);
	}
	
	public ChessPiece[][] getPieces() {// esse m�todo vai retornar uma matriz de pe�as de chadrez correspondente a essa partida
       ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
       for (int i=0; i<board.getRows(); i++) {
    	   for (int j=0; j<board.getColumns(); j++) {
    		   mat[i][j] = (ChessPiece) board.piece(i,j);//downcast para interpretar como uma pe�a de xadrez e n�o uma pe�a comum.
    	   }
       }
       return mat;
	}
}
