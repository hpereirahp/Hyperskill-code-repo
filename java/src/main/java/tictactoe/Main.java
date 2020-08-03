package tictactoe;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		// stage 3
		Scanner scanner = new Scanner(System.in);

		String symbols = scanner.nextLine();
		char[][] matrix = new char[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				matrix[i][j] = symbols.charAt(i * 3 + j);
			}
		}
		
		System.out.println("---------");
		for (int i = 0; i < 3; i++) {
			System.out.print("| ");
			for (int j = 0; j < 3; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println("|");
		}
		System.out.println("---------");

		int diff = Math.abs(symbols.replace("X", "").length() - symbols.replace("O", "").length()); 
		boolean hasEmptyCells = symbols.contains("_");
		
		if (diff > 1) {
			System.out.println("Impossible");
		} else {
			int xWins = 0;
			int oWins = 0;
			
			for (int i = 0; i < 3; i++) {
				if (matrix[i][0] == matrix[i][1] && matrix[i][1] == matrix[i][2]) {
					if (matrix[i][0] == 'X') {
						xWins++;
					} else if (matrix[i][0] == 'O') {
						oWins++;
					}
				}
				if (matrix[0][i] == matrix[1][i] && matrix[1][i] == matrix[2][i]) {
					if (matrix[0][i] == 'X') {
						xWins++;
					} else if (matrix[0][i] == 'O') {
						oWins++;
					}
				}
			}
			
			if ((matrix[0][0] == matrix[1][1] && matrix[1][1] == matrix[2][2]) ||
					(matrix[0][2] == matrix[1][1] && matrix[1][1] == matrix[2][0])) {
				if (matrix[1][1] == 'X') {
					xWins++;
				} else if (matrix[1][1] == 'O') {
					oWins++;
				}
			}
			
			if (xWins > 0 && oWins > 0) {
				System.out.println("Impossible");
			} else if (xWins > 0) {
				System.out.println("X wins");
			} else if (oWins > 0) {
				System.out.println("O wins");
			} else if (hasEmptyCells) {
				System.out.println("Game not finished");
			} else {
				System.out.println("Draw");
			}
		}
		
		scanner.close();
		
//		// stage 2
//		Scanner scanner = new Scanner(System.in);
//		
//		String symbols = scanner.nextLine();
//		
//		System.out.println("---------");
//		System.out.printf("| %s %s %s |\n", symbols.charAt(0), symbols.charAt(1), symbols.charAt(2));
//		System.out.printf("| %s %s %s |\n", symbols.charAt(3), symbols.charAt(4), symbols.charAt(5));
//		System.out.printf("| %s %s %s |\n", symbols.charAt(6), symbols.charAt(7), symbols.charAt(8));
//		System.out.println("---------");
//		
//		scanner.close();
		
//		// stage 1
//		System.out.println("X O X");
//		System.out.println("O X O");
//		System.out.println("X X O");
		
	}

}
