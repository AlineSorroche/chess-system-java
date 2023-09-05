package chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {//a classe chasMatch tem que saber a dimens�o do tabuleiro, por isso vou especificar 8,8
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;//por padr�o j� come�a no nulo
	private ChessPiece promoted;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		check = false; //uma propriedade boolean j� come�a por padr�o com falso por�m posso inicializar no construtor
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
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
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
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {//retornar as posi��es poss�veis
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) { //m�todo recebe posi��o de origem e de destino
	    Position source = sourcePosition.toPosition();
	    Position target = targetPosition.toPosition();
	    validateSourcePosition(source); //uma opera��o que vai validar se existia uma pe�a nessa posi��o de origem
	    validateTargetPosition(source,target);//uma opera��o que vai validar a posi��o de destino
	    Piece capturedPiece = makeMove(source, target); //opera��o respons�vel pelo movimento da pe�a
	    
	    //testar se me pus em xeque
	    if (testCheck(currentPlayer)) {
	    	undoMove(source, target, capturedPiece);
	    	throw new ChessException("You can't put yourself in check");
	    }
	    
	    ChessPiece movedPiece = (ChessPiece)board.piece(target);//pego a pe�a que se moveu
	    
	    //#specialmove promotion
	    promoted = null;
	    if (movedPiece instanceof Pawn) {
	    	if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
	    		promoted = (ChessPiece)board.piece(target);
	    		promoted = replacePromotedPiece("Q");
	    	}
	    }
	    
	    check = (testCheck(opponent(currentPlayer))) ? true : false;
	 	    
	    if (testCheckMate(opponent(currentPlayer))) {//testar se a jogada que eu fiz deixou o meu oponente em xeque mate
	    	checkMate = true;
	    }
	    else {
	    	nextTurn();
	    }	
	    
	    // #specialmove en passant - testar se a pe�a que foi movida era um pe�o e se moveu duas casas
	    if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {//se a pe�a � um pe�o e se ela teve um movimento de duas casas para cima e para baixo, seginifca que ...
	    	enPassantVulnerable = movedPiece;//quer dizer que a pe�a est� vulner�vel na pr�xima rodada
	    }	
	    else {
	    	enPassantVulnerable = null;
	    }	    	
	    
	    return (ChessPiece)capturedPiece;//retorna a pe�a capturada, downcast para ChessPiece porque essa pe�a capturada era do tipo Piece
	}
	
	public ChessPiece replacePromotedPiece(String type) {
		if (promoted == null) {
			throw new IllegalStateException("There is no piece to be promoted");
		}
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
			throw new InvalidParameterException("Invalid type for promotion");
		}
		
		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);
		
		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
	}
	
	private ChessPiece newPiece(String type, Color color) {//esse m�todo auxiliar � pra isntanciar a pe�a espec�fica para mim
		if (type.equals("B")) return new Bishop(board,color);
		if (type.equals("N")) return new Knight(board,color);
		if (type.equals("Q")) return new Queen(board,color);
		return new Rook(board,color);
	}
	
	private Piece makeMove(Position source, Position target) {//realizar um movimento
		ChessPiece p = (ChessPiece)board.removePiece(source);//retirei a pe�a que estava na posi��o de origem
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);//remover a poss�vel pe�a que esteja na posi��o de destino
		board.placePiece(p, target);//vou mover a pe�a de origem para a de destino
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece); // removo a pe�a da lista de pe�as no tabuleiro e...
			capturedPieces.add(capturedPiece);//adiciono ela na lista de pe�as capturadas
			
		}
		
		//#specialmove castling kingside rook
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);//retiro a torre de onde ela est�
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();//tratar o movimento do roque pequeno movendo a torre manualmente
		}
		
		// #specialmove castling queenside rook
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);// retiro a torre de onde ela est�
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();// tratar o movimento do roque grande movendo a torre manualmente
		}
		
		//#specialmove en passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == null) {//testo se o meu pe�o andou na diagonal e n�o capturou pe�a, isso significa que foi um en passant
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				}
				else {
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());//significa que � a pe�a preta
				}
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}	
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {//desfazer a jogada
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);
		
		if(capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
		// #specialmove castling kingside rook
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);// retiro a torre de onde ela est�
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();// desfaz o movimento do roque pequeno movendo a torre manualmente
		}

		// #specialmove castling queenside rook
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);// retiro a torre de onde ela est�
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();// desfaz o movimento do roque grande movendo a torre manualmente
		}
		
		//#specialmove en passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {//testo se o meu pe�o andou na diagonal e n�o capturou pe�a, isso significa que foi um en passant
				ChessPiece pawn = (ChessPiece)board.removePiece(target);//tirei a pe�a do lugar errado dela, pois o undeMove devolve para a posi��o de origem
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(3, target.getColumn());
				}
				else {
					pawnPosition = new Position(4, target.getColumn());//significa que � a pe�a preta
				}
				board.placePiece(pawn, pawnPosition);				
			}
		}	
	}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {//se n�o existir pe�a nessa posi��o de origem
			throw new ChessException("There is no piece on source position");
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {//testar se o jogador atual for diferente da cor, significa que a pe�a � do advers�rio - downscast porque o getColor � uma propriedade do chessPiece s� que o board.piece � de uma classe gen�rica a Piece
			throw new ChessException("The chosen piece is not yours");
		}
		if(!board.piece(position).isThereAnyPossibleMove()) {//se n�o existir movimentos poss�veis para essa pe�a
		throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void validateTargetPosition(Position source,Position target) {
		if (!board.piece(source).possibleMove(target)) { //se pra pe�a de origem a posi��o de destino n�o � um movimento poss�vel, isso quer dizer que eu n�o posso mover para l�
			throw new ChessException("The chosen piece can't move to target position");
		}
	}	
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;//express�o condicional tern�ria: se o jogador atual for igual a color.white, agora ele vai ser color.black, caso contr�rio ele vai ser o color.white -- troca de turno
	}
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;//se color for white retorna black, caso contr�rio retorna white
	}
	
	private ChessPiece king(Color color) {//procurar na lista de pe�as em jogo qual que � o rei desta cor
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());//lambda para filtrar uma lista
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + color + "King on the board");
	}
	
	private boolean testCheck(Color color) { //vou testar se o rei dessa cor est� em xeque
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {//testar pra cada pe�a p na lista de pe�as do oponente vou testar se tem algum movimento poss�vel que leva para pe�a p
			boolean [][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {//se esse elemento for verdadeiro...
				return true;//quer dizer que o rei est� em xeque
			}
		}//se eu esgotar todas as pe�as advers�rias e n�o houver nenhuma pe�a retorno o falso
		return false;
	}	
	
	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {//se essa cor n�o estiver em xeque, significa que tamb�m ele n�o est� em xeque mate
			return false;
		}//testar se todas as pe�as dessa cor n�o tem um movimento poss�vle pra tirar ele do xeque, ent�o est� em xeque mate
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());//criei uma lista que pega todas as pe�as da cor do par�metro
		for (Piece p : list) {//percorrer todas as pe�as pertencentes a minha lista
			boolean[][] mat = p.possibleMoves();//pego os movimentos poss�veis da minha pe�a p
			for (int i=0; i<board.getRows(); i++) {//percorrer as linhas da matriz
				for (int j=0; j<board.getColumns(); j++) {
					if (mat[i][j]) {//pra cada elemento da matriz eu preciso testar o seguinte: essa posi��o da matriz � um elemento poss�vel?
						Position source = ((ChessPiece)p).getChessPosition().toPosition();//pegar a pe�a p e colocar ela na posi��o da matriz que � um movimento poss�vel e da� testar se ainda estou em xeque
						Position target = new Position(i,j);
						Piece capturedPiece = makeMove(source, target);//fa�o o movimento da pe�a p para a posi��o da matriz
						boolean testCheck = testCheck(color);//testo se ainda est� em xeque
						undoMove(source, target, capturedPiece);//desfaz o movimento de teste
						if (!testCheck) {//se n�o estava em xeque significa que esse movimento tirou o meu rei do xeque
							return false;
						}
					}					
				}
			}
		}
		return true;
	}
	//instanciar as pe�as do xadrez informando as coordenadas do xadrez e n�o no sistema da matriz que fica confuso
	private void placeNewPiece(char column, int row, ChessPiece piece) {//m�todo vai receber as coordenadas do xadrez
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);//al�m de colocar a pe�a no tabuleiro eu j� coloco essa pe�a na lista de pe�as do tabuleiro
	}
	
	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));//como passei no construtor do king o chessMath tenho que aqui referenciar a partida
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));
        
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));      
	}
}
