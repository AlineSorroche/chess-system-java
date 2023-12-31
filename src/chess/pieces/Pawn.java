package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	private ChessMatch chessMatch;//declarar uma depend�ncia para a classe ChessMatch
	
	
	public Pawn(Board board, Color color, ChessMatch chessMatch) {//feita associa��o entre os objetos das classes
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean [getBoard().getRows()][getBoard().getColumns()];
			
		Position p = new Position(0, 0);
			
		//pawn white uma linha a menos na matriz(por�m no tabuleiro acima) (movimento reto acima, uma casa por vez)
		if (getColor () == Color.WHITE) {//se a cor desse pe�o for igual a cor branca
			p.setValues(position.getRow() -1, position.getColumn());//uma posi��o acima
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//se a posi��o existe e n�o tem uma pe�a naquela posi��o, significa que o pe�o pode se mover
				mat[p.getRow()][p.getColumn()] = true;
			}
			//verificando as duas posi��es acima (caso seja primeira jogada)
			p.setValues(position.getRow() -2, position.getColumn());//uma posi��o acima
			Position p2 = new Position(position.getRow() -1, position.getColumn());//para poder testar se na posi��o acima tem algu�m
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {//se a posi��o existe e n�o tem uma pe�a naquela posi��o, testo junto se � a primeira rodada, pois nessa jogada o pe�o pode mover duas casas
				mat[p.getRow()][p.getColumn()] = true;
			}
			//verificando a diagonal noroeste(se tiver oponente posso me mover para l�, andando apenas uma casa)
			p.setValues(position.getRow() - 1, position.getColumn() - 1);//uma posi��o acima
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//se a posi��o existe e tem uma pe�a do oponente l� eu posso mover
				mat[p.getRow()][p.getColumn()] = true;
			}
			//verificando a diagonal nordeste(se tiver oponente posso me mover para l�, andando apenas uma casa)
			p.setValues(position.getRow() - 1, position.getColumn() + 1);//uma posi��o acima
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//se a posi��o existe e tem uma pe�a do oponente l� eu posso mover
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// # specialmove en passant white
			if (position.getRow() == 3) {
				Position left = new Position(position.getRow(), position.getColumn() -1);//teste abaixo: se a posi��o existe, se tem uma pe�a l� que � oponente e se essa pe�a que est� l� � vulner�vel a tomar o en passant
				if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					mat [left.getRow() - 1][left.getColumn()] = true;
				}
				Position right = new Position(position.getRow(), position.getColumn() +1);//teste abaixo: se a posi��o existe, se tem uma pe�a l� que � oponente e se essa pe�a que est� l� � vulner�vel a tomar o en passant
				if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					mat [right.getRow() - 1][right.getColumn()] = true;
				}	
			}			
		}
		else {//pe�as Black onde andam para baixo no jogo, por�m na matriz � +
			p.setValues(position.getRow() + 1, position.getColumn());//uma posi��o acima
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//se a posi��o existe e n�o tem uma pe�a naquela posi��o, significa que o pe�o pode se mover
				mat[p.getRow()][p.getColumn()] = true;
			}
			//verificando as duas posi��es acima (caso seja primeira jogada)
			p.setValues(position.getRow() +2, position.getColumn());//uma posi��o acima
			Position p2 = new Position(position.getRow() + 1, position.getColumn());//para poder testar se na posi��o acima tem algu�m
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {//se a posi��o existe e n�o tem uma pe�a naquela posi��o, testo junto se � a primeira rodada, pois nessa jogada o pe�o pode mover duas casas
				mat[p.getRow()][p.getColumn()] = true;
			}
			//verificando a diagonal noroeste(se tiver oponente posso me mover para l�, andando apenas uma casa)
			p.setValues(position.getRow() + 1, position.getColumn() - 1);//uma posi��o acima
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//se a posi��o existe e tem uma pe�a do oponente l� eu posso mover
				mat[p.getRow()][p.getColumn()] = true;
			}
			//verificando a diagonal nordeste(se tiver oponente posso me mover para l�, andando apenas uma casa)
			p.setValues(position.getRow() + 1, position.getColumn() + 1);//uma posi��o acima
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//se a posi��o existe e tem uma pe�a do oponente l� eu posso mover
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// # specialmove en passant black
			if (position.getRow() == 4) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);// teste abaixo: se a posi��o existe, se tem uma pe�a l� que � oponente e se essa pe�a que est� l� � vulner�vel a tomar o en passant 
				if (getBoard().positionExists(left) && isThereOpponentPiece(left)&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow() + 1][left.getColumn()] = true;
				}
				Position right = new Position(position.getRow(), position.getColumn() + 1);// teste abaixo: se a posi��o existe, se tem uma pe�a l� que � oponente e se essa pe�a que est� l� � vulner�vel a tomar o en passant
				if (getBoard().positionExists(right) && isThereOpponentPiece(right)
						&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					mat[right.getRow() + 1][right.getColumn()] = true;
				}
			}	
		}
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}

}
