package application;

import chess.ChessPiece;

public class UI {
     
	 public static void printBoard(ChessPiece[][] pieces) {//m�todo est�tico que recebe um chessPiece matriz
		for (int i=0; i<pieces.length; i++) {
			System.out.print((8-i) + " ");//imprimir o n�mero da linha
			for (int j=0; j<pieces.length; j++) {
				printPiece(pieces [i][j]);//imprimir a pe�a
			}
			System.out.println();//quebra de linha
		}
		System.out.println("  a b c d e f g h");
	 }
	 
	 private static void printPiece(ChessPiece piece) {//m�todo para imprimir uma pe�a
		 if (piece == null) {
			 System.out.print("-");
		 }
		 else {
			 System.out.print(piece);
		 }
		 System.out.print(" ");
	 }
}
