package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean [getBoard().getRows()][getBoard().getColumns()];
			
		Position p = new Position(0, 0);
			
		//pawn white uma linha a menos na matriz(porém no tabuleiro acima) (movimento reto acima, uma casa por vez)
		if (getColor () == Color.WHITE) {//se a cor desse peão for igual a cor branca
			p.setValues(position.getRow() -1, position.getColumn());//uma posição acima
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//se a posição existe e não tem uma peça naquela posição, significa que o peão pode se mover
				mat[p.getRow()][p.getColumn()] = true;
			}
			//verificando as duas posições acima (caso seja primeira jogada)
			p.setValues(position.getRow() -2, position.getColumn());//uma posição acima
			Position p2 = new Position(position.getRow() -1, position.getColumn());//para poder testar se na posição acima tem alguém
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {//se a posição existe e não tem uma peça naquela posição, testo junto se é a primeira rodada, pois nessa jogada o peão pode mover duas casas
				mat[p.getRow()][p.getColumn()] = true;
			}
			//verificando a diagonal noroeste(se tiver oponente posso me mover para lá, andando apenas uma casa)
			p.setValues(position.getRow() - 1, position.getColumn() - 1);//uma posição acima
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//se a posição existe e tem uma peça do oponente lá eu posso mover
				mat[p.getRow()][p.getColumn()] = true;
			}
			//verificando a diagonal nordeste(se tiver oponente posso me mover para lá, andando apenas uma casa)
			p.setValues(position.getRow() - 1, position.getColumn() + 1);//uma posição acima
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//se a posição existe e tem uma peça do oponente lá eu posso mover
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		else {//peças Black onde andam para baixo no jogo, porém na matriz é +
			p.setValues(position.getRow() + 1, position.getColumn());//uma posição acima
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//se a posição existe e não tem uma peça naquela posição, significa que o peão pode se mover
				mat[p.getRow()][p.getColumn()] = true;
			}
			//verificando as duas posições acima (caso seja primeira jogada)
			p.setValues(position.getRow() +2, position.getColumn());//uma posição acima
			Position p2 = new Position(position.getRow() + 1, position.getColumn());//para poder testar se na posição acima tem alguém
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {//se a posição existe e não tem uma peça naquela posição, testo junto se é a primeira rodada, pois nessa jogada o peão pode mover duas casas
				mat[p.getRow()][p.getColumn()] = true;
			}
			//verificando a diagonal noroeste(se tiver oponente posso me mover para lá, andando apenas uma casa)
			p.setValues(position.getRow() + 1, position.getColumn() - 1);//uma posição acima
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//se a posição existe e tem uma peça do oponente lá eu posso mover
				mat[p.getRow()][p.getColumn()] = true;
			}
			//verificando a diagonal nordeste(se tiver oponente posso me mover para lá, andando apenas uma casa)
			p.setValues(position.getRow() + 1, position.getColumn() + 1);//uma posição acima
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//se a posição existe e tem uma peça do oponente lá eu posso mover
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}

}
