package chess;

import boardgame.BoardException;

public class ChessException extends BoardException {//fica mais simples do meu programa tratar porque eu irei capturar exceções e tratar tanto do ChessException como do BoardExcpetion
	private static final long serialVersionUID = 1L;
	
	public ChessException(String msg) {
		super(msg);
	}
}
