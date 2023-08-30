package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {//a classe chasMatch tem que saber a dimensão do tabuleiro, por isso vou especificar 8,8
	
	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
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
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) { //método recebe posição de origem e de destino
	    Position source = sourcePosition.toPosition();
	    Position target = targetPosition.toPosition();
	    validateSourcePosition(source); //uma operação que vai validar se existia uma peça nessa posição de origem
	    Piece capturedPiece = makeMove(source, target); //operação responsável pelo movimento da peça
	    return (ChessPiece)capturedPiece;//retorna a peça capturada, downcast para ChessPiece porque essa peça capturada era do tipo Piece
	}
	
	private Piece makeMove(Position source, Position target) {//realizar um movimento
		Piece p = board.removePiece(source);//retirei a peça que estava na posição de origem
		Piece capturedPiece = board.removePiece(target);//remover a possível peça que esteja na posição de destino
		board.placePiece(p, target);//vou mover a peça de origem para a de destino
		return capturedPiece;
	}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {//se não existir essa posição
			throw new ChessException("There is no piece on source position");
		}
	}
	//instanciar as peças do xadrez informando as coordenadas do xadrez e não no sistema da matriz que fica confuso
	private void placeNewPiece(char column, int row, ChessPiece piece) {//método vai receber as coordenadas do xadrez
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
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
