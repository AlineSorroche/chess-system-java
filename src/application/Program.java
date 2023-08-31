package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
        //instanciar uma partida e imprimir o tabuleiro
	    Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		
		while (true) {
			try {
				UI.clearScreen(); // limpa a tela a cada vez que voltar no meu while
				UI.printBoard(chessMatch.getPieces());//criamos a classe UI e dentro dela o método printBoard
			    System.out.println();
			    System.out.print("Source: ");
			    ChessPosition source = UI.readChessPosition(sc);
			    
			    System.out.println();
			    System.out.print("Target: ");
			    ChessPosition target = UI.readChessPosition(sc);
			    
			    ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
			}
			catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine(); // para o programa me aguardar digitar
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine(); // para o programa me aguardar digitar enter
			}
		}
	}
}
