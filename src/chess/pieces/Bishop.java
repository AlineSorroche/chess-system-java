package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

	public Bishop(Board board, Color color) {//construtor passa a chamada para a super classe
		super(board, color);
	}
	
	@Override
	public String toString() { 
		return "B"; //vai retornar apenas a letra da peça
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean [getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		//noroeste nw
		p.setValues(position.getRow() -1, position.getColumn() - 1);//position é a posição da própria peça só que menos 1, estou analisando acima dela e na diagonal esquerda
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//estou dizendo que enquanto a posição p existir e não tiver uma peça lá vou marcar essa posição como verdadeira
			mat[p.getRow()][p.getColumn()] = true;//marquei essa posição como verdadeiro, indicando que a minha peça pode andar até lá
			p.setValues(p.getRow() - 1, p.getColumn() - 1);//enquanto estiver vazio o programa vai diminuindo uma casa
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//marca como verdadeiro se tem uma peça oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//diagonal nordeste ne
		p.setValues(position.getRow() - 1, position.getColumn() + 1);//position é a posição da própria peça só que menos 1, estou analisando diagonal direita
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//estou dizendo que enquanto a posição p existir e não tiver uma peça lá vou marcar essa posição como verdadeira
			mat[p.getRow()][p.getColumn()] = true;//marquei essa posição como verdadeiro, indicando que a minha peça pode andar até lá
			p.setValues(p.getRow() - 1, p.getColumn() + 1);//enquanto estiver vazio o programa vai diminuindo uma casa
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//marca como verdadeiro se tem uma peça oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//analisar sudeste /se
		p.setValues(position.getRow() + 1, position.getColumn() + 1);//position é a posição da própria peça só que mais 1, analisando diagonal abaixo direita
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//estou dizendo que enquanto a posição p existir e não tiver uma peça lá vou marcar essa posição como verdadeira
			mat[p.getRow()][p.getColumn()] = true;//marquei essa posição como verdadeiro, indicando que a minha peça pode andar até lá
			p.setValues(p.getRow() + 1,p.getColumn() + 1);//enquanto estiver vazio o programa vai diminuindo uma casa
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//marca como verdadeiro se tem uma peça oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//sudoeste / sw 
		p.setValues(position.getRow() +1, position.getColumn() -1);//position é a posição da própria peça só que linha abaixo a esquerda
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//estou dizendo que enquanto a posição p existir e não tiver uma peça lá vou marcar essa posição como verdadeira
			mat[p.getRow()][p.getColumn()] = true;//marquei essa posição como verdadeiro, indicando que a minha peça pode andar até lá
			p.setValues(p.getRow() + 1, p.getColumn() - 1);//enquanto estiver vazio o programa vai diminuindo uma casa
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//marca como verdadeiro se tem uma peça oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
	}

}
