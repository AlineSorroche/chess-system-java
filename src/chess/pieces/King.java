package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
	
	private ChessMatch chessMatch;//para a jogada Roque o Rei precisa ter acesso a partida

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}
	
	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {//esse m�todo vai retornar para mim se o Rei pode se mover em determinada posi��o
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p == null || p.getColor() != getColor();//move se ou a casa est� vazia ou se tem uma pe�a advers�ria
	}
	
	private boolean testRookCastling(Position position) {//esse m�todo vai informar se nessa posi��o existe uma torre e se ela est� apta para o Roque
		ChessPiece p = (ChessPiece)getBoard().piece(position);//peguei a pe�a que est� nessa posi��o
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0; //verifico se essa pe�a � diferente de nulo, se � uma Rook, se a cor dessa torre � gual a do rei e se o contador de movimentos � igual a zero.
	}	
		
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean [getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		//testar posi��es acima
		p.setValues(position.getRow() -1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//testar posi��es abaixo
		p.setValues(position.getRow() +1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//testar posi��es a esquerda
		p.setValues(position.getRow(), position.getColumn() -1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//testar posi��es a direita
		p.setValues(position.getRow(), position.getColumn() +1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//testar posi��es a noroeste nw
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//testar posi��es a nordeste ne
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		//testar posi��es a sudoeste sw
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		//testar posi��es a sudeste se
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//#specialmove castling
		if (getMoveCount() == 0 && !chessMatch.getCheck()) { //vou testar o contador e se a partida n�o est� em xeque
			//#specialmove castling kingside rook
			Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
			if (testRookCastling(posT1)) {
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					mat[position.getRow()][position.getColumn() +2] = true;
				}
			}
			//#specialmove castling queenside rook
			Position posT2 = new Position(position.getRow(), position.getColumn() - 4);
			if (testRookCastling(posT2)) {
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}			
		}	
		return mat;
	}
}
