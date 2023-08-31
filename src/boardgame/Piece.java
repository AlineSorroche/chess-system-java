package boardgame;

public abstract class Piece {
	
	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null; //posi��o come�a nula, por�m n�o � necess�rio informar isso aqui
	}

	protected Board getBoard() {//tabuleiro vai ser de uso interno da camada de tabuleiro e n�o acess�vel em outras camadas
		return board;
	}

	public abstract boolean[][] possibleMoves();
	
	public boolean possibleMove(Position position) {//hookmethods � um m�todo que faz um gancho com a subclasse
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for (int i=0; i<mat.length; i++) {
			for (int j=0; j<mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}	
}
