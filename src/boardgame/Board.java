package boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {//programa��o defensiva...
		if (rows < 1 || columns < 1) { // essa condi��o testa se o tabuleiro tem os campos necess�rios
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}
     //programa��o defensiva ...
	//retirei as condi��es setRows e setColumns porque uma vez criado o tabuleiro n�o teria l�gica alterar o seu tamanho

	public int getColumns() {
		return columns;
	}
	

	public Piece piece(int row, int column) {//programa��o defensiva...
		if (!positionExists(row, column)) { //testa se a posi��o n�o existe primeiro
			throw new BoardException("Position not on the board");
		}
		return pieces[row][column];
	}
	
	public Piece piece(Position position) {//sobrecarga do m�todo Piece
		if (!positionExists(position)) { //Programa��o defensiva ... testa se a posi��o n�o existe primeiro
			throw new BoardException("Position not on the board");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {//essa matriz de pe�as foi declarada no meu tabuleiro e instanciada no construtor
		if (thereIsAPiece(position)){ //Programa��o defensiva ... antes de colocar uma pe�a nessa posi��o eu tenho que testar se j� existe uma pe�a nessa posi��o
		    throw new BoardException("There is already a piece on position " + position);
		}	
		pieces[position.getRow()][position.getColumn()] = piece;//pego a matriz na posi��o dada e atribuo a ela a posi��o que informei
	    piece.position = position;//informo que a position n�o � mais nula e sim recebe a posi��o. como eu estou no mesmo pacote da classe consigo acessar a position
	}
	
	public Piece removePiece(Position position) { //remover uma pe�a de uma dada posi��o do tabuleiro
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		if (piece(position) == null) {//se a pe�a do tabuleiro nessa posi��o � igual a nulo, se for verdade quer dizer que n�o tem nenhuma pe�a nessa posi��o
			return null;
		}//retirar a pe�a do tabuleiro
		Piece aux = piece(position);//vari�vel vai receber a pe�a que tiver no tabuleiro nessa posi��o
		aux.position = null;//essa pe�a foi retirada do tabuleiro e n�o tem mais
		pieces[position.getRow()][position.getColumn()] = null;//acesso a matriz de pe�as piece e digo que na posi��o onde estou removendo a pe�a agora vai ser nulo
		return aux;//retorna a vari�vel que cont�m a pe�a que foi retirada
	}
	
	private boolean positionExists(int row, int column) { //dentro da classe vai ter uma hora que vai ser mais f�cil testar pela linha e coluna do que pela posi��o
		return row >= 0 && row < rows && column >= 0 && column < columns;//a linha tem que ser maior ou igual a zero e menor do que a altura do tabuleiro e tamb�m a coluna. Valida a posi��o
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());		
	}
	
	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) { //Programa��o defensiva ... testa se a posi��o n�o existe primeiro
			throw new BoardException("Position not on the board");
		}
		return piece(position) != null;
	}
}
