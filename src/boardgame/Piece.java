package boardgame;

public class Piece {
	
	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null; //posição começa nula, porém não é necessário informar isso aqui
	}

	protected Board getBoard() {//tabuleiro vai ser de uso interno da camada de tabuleiro e não acessível em outras camadas
		return board;
	}

	
}
