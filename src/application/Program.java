package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
		List<ChessPiece> captured = new ArrayList<>();
		
		while (!chessMatch.getCheckMate()) {//enquanto minha partida não estiver em xeque mate
			try {
				UI.clearScreen(); // limpa a tela a cada vez que voltar no meu while
				UI.printMatch(chessMatch, captured);//criamos a classe UI e dentro dela o método printBoard
			    System.out.println();
			    System.out.print("Source: ");
			    ChessPosition source = UI.readChessPosition(sc);
			    
			    boolean[][] possibleMoves = chessMatch.possibleMoves(source);
			    UI.clearScreen();
			    UI.printBoard(chessMatch.getPieces(), possibleMoves);
			    
			    System.out.println();
			    System.out.print("Target: ");
			    ChessPosition target = UI.readChessPosition(sc);
			    
			    ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
			    
			    if (capturedPiece != null) {
			    	captured.add(capturedPiece);
			    }
			    
			    if (chessMatch.getPromoted() != null) {
			    	System.out.print("Enter piece for promotion (B/N/R/Q): ");
			    	String type = sc.nextLine();
			    	chessMatch.replacePromotedPiece(type);
			    }
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
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
	}
}
