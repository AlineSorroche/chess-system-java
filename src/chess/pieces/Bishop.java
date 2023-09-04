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
		return "B"; //vai retornar apenas a letra da pe�a
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean [getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		//noroeste nw
		p.setValues(position.getRow() -1, position.getColumn() - 1);//position � a posi��o da pr�pria pe�a s� que menos 1, estou analisando acima dela e na diagonal esquerda
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//estou dizendo que enquanto a posi��o p existir e n�o tiver uma pe�a l� vou marcar essa posi��o como verdadeira
			mat[p.getRow()][p.getColumn()] = true;//marquei essa posi��o como verdadeiro, indicando que a minha pe�a pode andar at� l�
			p.setValues(p.getRow() - 1, p.getColumn() - 1);//enquanto estiver vazio o programa vai diminuindo uma casa
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//marca como verdadeiro se tem uma pe�a oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//diagonal nordeste ne
		p.setValues(position.getRow() - 1, position.getColumn() + 1);//position � a posi��o da pr�pria pe�a s� que menos 1, estou analisando diagonal direita
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//estou dizendo que enquanto a posi��o p existir e n�o tiver uma pe�a l� vou marcar essa posi��o como verdadeira
			mat[p.getRow()][p.getColumn()] = true;//marquei essa posi��o como verdadeiro, indicando que a minha pe�a pode andar at� l�
			p.setValues(p.getRow() - 1, p.getColumn() + 1);//enquanto estiver vazio o programa vai diminuindo uma casa
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//marca como verdadeiro se tem uma pe�a oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//analisar sudeste /se
		p.setValues(position.getRow() + 1, position.getColumn() + 1);//position � a posi��o da pr�pria pe�a s� que mais 1, analisando diagonal abaixo direita
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//estou dizendo que enquanto a posi��o p existir e n�o tiver uma pe�a l� vou marcar essa posi��o como verdadeira
			mat[p.getRow()][p.getColumn()] = true;//marquei essa posi��o como verdadeiro, indicando que a minha pe�a pode andar at� l�
			p.setValues(p.getRow() + 1,p.getColumn() + 1);//enquanto estiver vazio o programa vai diminuindo uma casa
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//marca como verdadeiro se tem uma pe�a oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//sudoeste / sw 
		p.setValues(position.getRow() +1, position.getColumn() -1);//position � a posi��o da pr�pria pe�a s� que linha abaixo a esquerda
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//estou dizendo que enquanto a posi��o p existir e n�o tiver uma pe�a l� vou marcar essa posi��o como verdadeira
			mat[p.getRow()][p.getColumn()] = true;//marquei essa posi��o como verdadeiro, indicando que a minha pe�a pode andar at� l�
			p.setValues(p.getRow() + 1, p.getColumn() - 1);//enquanto estiver vazio o programa vai diminuindo uma casa
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//marca como verdadeiro se tem uma pe�a oponente
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
	}

}
