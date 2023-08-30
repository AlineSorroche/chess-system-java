package application;

import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
        //instanciar uma partida e imprimir o tabuleiro
	    Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		
		while (true) {
			UI.printBoard(chessMatch.getPieces());//criamos a classe UI e dentro dela o método printBoard
		    System.out.println();
		    System.out.print("Source: ");
		    ChessPosition source = UI.readChessPosition(sc);
		    
		    System.out.println();
		    System.out.print("Target: ");
		    ChessPosition target = UI.readChessPosition(sc);
		    
		    ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
		}
	}
}
