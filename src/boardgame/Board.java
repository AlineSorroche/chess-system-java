package boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {//programação defensiva...
		if (rows < 1 || columns < 1) { // essa condição testa se o tabuleiro tem os campos necessários
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}
     //programação defensiva ...
	//retirei as condições setRows e setColumns porque uma vez criado o tabuleiro não teria lógica alterar o seu tamanho

	public int getColumns() {
		return columns;
	}
	

	public Piece piece(int row, int column) {//programação defensiva...
		if (!positionExists(row, column)) { //testa se a posição não existe primeiro
			throw new BoardException("Position not on the board");
		}
		return pieces[row][column];
	}
	
	public Piece piece(Position position) {//sobrecarga do método Piece
		if (!positionExists(position)) { //Programação defensiva ... testa se a posição não existe primeiro
			throw new BoardException("Position not on the board");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {//essa matriz de peças foi declarada no meu tabuleiro e instanciada no construtor
		if (thereIsAPiece(position)){ //Programação defensiva ... antes de colocar uma peça nessa posição eu tenho que testar se já existe uma peça nessa posição
		    throw new BoardException("There is already a piece on position " + position);
		}	
		pieces[position.getRow()][position.getColumn()] = piece;//pego a matriz na posição dada e atribuo a ela a posição que informei
	    piece.position = position;//informo que a position não é mais nula e sim recebe a posição. como eu estou no mesmo pacote da classe consigo acessar a position
	}
	
	public Piece removePiece(Position position) { //remover uma peça de uma dada posição do tabuleiro
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		if (piece(position) == null) {//se a peça do tabuleiro nessa posição é igual a nulo, se for verdade quer dizer que não tem nenhuma peça nessa posição
			return null;
		}//retirar a peça do tabuleiro
		Piece aux = piece(position);//variável vai receber a peça que tiver no tabuleiro nessa posição
		aux.position = null;//essa peça foi retirada do tabuleiro e não tem mais
		pieces[position.getRow()][position.getColumn()] = null;//acesso a matriz de peças piece e digo que na posição onde estou removendo a peça agora vai ser nulo
		return aux;//retorna a variável que contém a peça que foi retirada
	}
	
	private boolean positionExists(int row, int column) { //dentro da classe vai ter uma hora que vai ser mais fácil testar pela linha e coluna do que pela posição
		return row >= 0 && row < rows && column >= 0 && column < columns;//a linha tem que ser maior ou igual a zero e menor do que a altura do tabuleiro e também a coluna. Valida a posição
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());		
	}
	
	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) { //Programação defensiva ... testa se a posição não existe primeiro
			throw new BoardException("Position not on the board");
		}
		return piece(position) != null;
	}
}
