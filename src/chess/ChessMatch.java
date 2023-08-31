package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {//a classe chasMatch tem que saber a dimens�o do tabuleiro, por isso vou especificar 8,8
	
	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
	}
	
	public ChessPiece[][] getPieces() {// esse m�todo vai retornar uma matriz de pe�as de chadrez correspondente a essa partida
       ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
       for (int i=0; i<board.getRows(); i++) {
    	   for (int j=0; j<board.getColumns(); j++) {
    		   mat[i][j] = (ChessPiece) board.piece(i,j);//downcast para interpretar como uma pe�a de xadrez e n�o uma pe�a comum.
    	   }
       }
       return mat;
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) { //m�todo recebe posi��o de origem e de destino
	    Position source = sourcePosition.toPosition();
	    Position target = targetPosition.toPosition();
	    validateSourcePosition(source); //uma opera��o que vai validar se existia uma pe�a nessa posi��o de origem
	    Piece capturedPiece = makeMove(source, target); //opera��o respons�vel pelo movimento da pe�a
	    return (ChessPiece)capturedPiece;//retorna a pe�a capturada, downcast para ChessPiece porque essa pe�a capturada era do tipo Piece
	}
	
	private Piece makeMove(Position source, Position target) {//realizar um movimento
		Piece p = board.removePiece(source);//retirei a pe�a que estava na posi��o de origem
		Piece capturedPiece = board.removePiece(target);//remover a poss�vel pe�a que esteja na posi��o de destino
		board.placePiece(p, target);//vou mover a pe�a de origem para a de destino
		return capturedPiece;
	}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {//se n�o existir pe�a nessa posi��o de origem
			throw new ChessException("There is no piece on source position");
		}
		if(!board.piece(position).isThereAnyPossibleMove()) {//se n�o existir movimentos poss�veis para essa pe�a
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	//instanciar as pe�as do xadrez informando as coordenadas do xadrez e n�o no sistema da matriz que fica confuso
	private void placeNewPiece(char column, int row, ChessPiece piece) {//m�todo vai receber as coordenadas do xadrez
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
