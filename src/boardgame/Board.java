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
