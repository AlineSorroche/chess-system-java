package chess.pieces;

import boardgame.Board;
import boardgame.Position;
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
		
		Position p = new Position(0, 0);
		
		//acima da peça
		p.setValues(position.getRow() -1, position.getColumn());//position é a posição da própria peça só que menos 1, estou analisando acima dela
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//estou dizendo que enquanto a posição p existir e não tiver uma peça lá vou marcar essa posição como verdadeira
			mat[p.getRow()][p.getColumn()] = true;//marquei essa posição como verdadeiro, indicando que a minha peça pode andar até lá
			p.setRow(p.getRow() -1);//enquanto estiver vazio o programa vai diminuindo uma casa
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//marca como verdadeiro se tem uma peça oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//analisar a esquerda
		p.setValues(position.getRow(), position.getColumn() -1);//position é a posição da própria peça só que menos 1, estou analisando acima dela
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//estou dizendo que enquanto a posição p existir e não tiver uma peça lá vou marcar essa posição como verdadeira
			mat[p.getRow()][p.getColumn()] = true;//marquei essa posição como verdadeiro, indicando que a minha peça pode andar até lá
			p.setColumn(p.getColumn() -1);//enquanto estiver vazio o programa vai diminuindo uma casa
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//marca como verdadeiro se tem uma peça oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//analisar a direita
		p.setValues(position.getRow(), position.getColumn() +1);//position é a posição da própria peça só que menos 1, estou analisando acima dela
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//estou dizendo que enquanto a posição p existir e não tiver uma peça lá vou marcar essa posição como verdadeira
			mat[p.getRow()][p.getColumn()] = true;//marquei essa posição como verdadeiro, indicando que a minha peça pode andar até lá
			p.setColumn(p.getColumn() +1);//enquanto estiver vazio o programa vai diminuindo uma casa
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//marca como verdadeiro se tem uma peça oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//abaixo
		p.setValues(position.getRow() +1, position.getColumn());//position é a posição da própria peça só que menos 1, estou analisando acima dela
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//estou dizendo que enquanto a posição p existir e não tiver uma peça lá vou marcar essa posição como verdadeira
			mat[p.getRow()][p.getColumn()] = true;//marquei essa posição como verdadeiro, indicando que a minha peça pode andar até lá
			p.setRow(p.getRow() +1);//enquanto estiver vazio o programa vai diminuindo uma casa
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//marca como verdadeiro se tem uma peça oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
	}

}
