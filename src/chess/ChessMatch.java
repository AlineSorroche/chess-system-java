package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {//a classe chasMatch tem que saber a dimensão do tabuleiro, por isso vou especificar 8,8
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		check = false; //uma propriedade boolean já começa por padrão com falso porém posso inicializar no construtor
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	
	public ChessPiece[][] getPieces() {// esse método vai retornar uma matriz de peças de chadrez correspondente a essa partida
       ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
       for (int i=0; i<board.getRows(); i++) {
    	   for (int j=0; j<board.getColumns(); j++) {
    		   mat[i][j] = (ChessPiece) board.piece(i,j);//downcast para interpretar como uma peça de xadrez e não uma peça comum.
    	   }
       }
       return mat;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {//retornar as posições possíveis
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) { //método recebe posição de origem e de destino
	    Position source = sourcePosition.toPosition();
	    Position target = targetPosition.toPosition();
	    validateSourcePosition(source); //uma operação que vai validar se existia uma peça nessa posição de origem
	    validateTargetPosition(source,target);//uma operação que vai validar a posição de destino
	    Piece capturedPiece = makeMove(source, target); //operação responsável pelo movimento da peça
	    
	    //testar se me pus em xeque
	    if (testCheck(currentPlayer)) {
	    	undoMove(source, target, capturedPiece);
	    	throw new ChessException("You can't put yourself in check");
	    }
	    
	    check = (testCheck(opponent(currentPlayer))) ? true : false;
	    nextTurn();
	    return (ChessPiece)capturedPiece;//retorna a peça capturada, downcast para ChessPiece porque essa peça capturada era do tipo Piece
	}
	
	private Piece makeMove(Position source, Position target) {//realizar um movimento
		Piece p = board.removePiece(source);//retirei a peça que estava na posição de origem
		Piece capturedPiece = board.removePiece(target);//remover a possível peça que esteja na posição de destino
		board.placePiece(p, target);//vou mover a peça de origem para a de destino
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece); // removo a peça da lista de peças no tabuleiro e...
			capturedPieces.add(capturedPiece);//adiciono ela na lista de peças capturadas
			
		}
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {//desfazer a jogada
		Piece p = board.removePiece(target);
		board.placePiece(p, source);
		
		if(capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {//se não existir peça nessa posição de origem
			throw new ChessException("There is no piece on source position");
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {//testar se o jogador atual for diferente da cor, significa que a peça é do adversário - downscast porque o getColor é uma propriedade do chessPiece só que o board.piece é de uma classe genérica a Piece
			throw new ChessException("The chosen piece is not yours");
		}
		if(!board.piece(position).isThereAnyPossibleMove()) {//se não existir movimentos possíveis para essa peça
		throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void validateTargetPosition(Position source,Position target) {
		if (!board.piece(source).possibleMove(target)) { //se pra peça de origem a posição de destino não é um movimento possível, isso quer dizer que eu não posso mover para lá
			throw new ChessException("The chosen piece can't move to target position");
		}
	}	
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;//expressão condicional ternária: se o jogador atual for igual a color.white, agora ele vai ser color.black, caso contrário ele vai ser o color.white -- troca de turno
	}
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;//se color for white retorna black, caso contrário retorna white
	}
	
	private ChessPiece king(Color color) {//procurar na lista de peças em jogo qual que é o rei desta cor
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());//lambda para filtrar uma lista
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + color + "King on the board");
	}
	
	private boolean testCheck(Color color) { //vou testar se o rei dessa cor está em xeque
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {//testar pra cada peça p na lista de peças do oponente vou testar se tem algum movimento possível que leva para peça p
			boolean [][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {//se esse elemento for verdadeiro...
				return true;//quer dizer que o rei está em xeque
			}
		}//se eu esgotar todas as peças adversárias e não houver nenhuma peça retorno o falso
		return false;
	}	
	//instanciar as peças do xadrez informando as coordenadas do xadrez e não no sistema da matriz que fica confuso
	private void placeNewPiece(char column, int row, ChessPiece piece) {//método vai receber as coordenadas do xadrez
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);//além de colocar a peça no tabuleiro eu já coloco essa peça na lista de peças do tabuleiro
	}
	
	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
