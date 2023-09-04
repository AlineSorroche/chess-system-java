package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece{
	
	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	
	public ChessPosition getChessPosition() {//pegar a posi��o que foi herdada da Piece e converter em ChessPosition
		return ChessPosition.fromPosition(position);
	}
	
	
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p.getColor() != color;//testando se a cor da pe�a dessa posi��o � diferente da minha pe�a.
	}

	
	

}
