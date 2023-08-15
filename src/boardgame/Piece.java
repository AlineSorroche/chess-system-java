package boardgame;

public class Piece {
	
	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null; //posi��o come�a nula, por�m n�o � necess�rio informar isso aqui
	}

	protected Board getBoard() {//tabuleiro vai ser de uso interno da camada de tabuleiro e n�o acess�vel em outras camadas
		return board;
	}

	
}
