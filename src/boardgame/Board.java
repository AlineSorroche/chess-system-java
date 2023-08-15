package boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public Piece piece(int row, int column) {
		return pieces[row][column];
	}
	
	public Piece piece(Position position) {//sobrecarga do método Piece
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {//essa matriz de peças foi declarada no meu tabuleiro e instanciada no construtor
		pieces[position.getRow()][position.getColumn()] = piece;//pego a matriz na posição dada e atribuo a ela a posição que informei
	    piece.position = position;//informo que a position não é mais nula e sim recebe a posição. como eu estou no mesmo pacote da classe consigo acessar a position
	}
	
}
