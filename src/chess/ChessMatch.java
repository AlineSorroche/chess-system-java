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
	    validateTargetPosition(source,target);//uma operação que vai validar a posição de destino
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
		if (!board.thereIsAPiece(position)) {//se não existir peça nessa posição de origem
			throw new ChessException("There is no piece on source position");
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
