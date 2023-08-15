package application;

import chess.ChessMatch;

public class Program {

	public static void main(String[] args) {
        //instanciar uma partida e imprimir o tabuleiro
		ChessMatch chessMatch = new ChessMatch();
		UI.printBoard(chessMatch.getPieces());//criamos a classe UI e dentro dela o método printBoard
	}

}
