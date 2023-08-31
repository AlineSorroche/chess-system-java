package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {//construtor passa a chamada para a super classe
		super(board, color);
	}
	
	@Override
	public String toString() { 
		return "R"; //vai retornar apenas a letra da peça
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean [getBoard().getRows()][getBoard().getColumns()];
		return mat;
	}

}
