package tictactoe.ai;

import java.util.Scanner;

public class Main {
	
	static final Scanner scanner = new Scanner(System.in);
	static char[][] matrix = new char[3][3];
	static int count = 0;
	static String results = "";
	
	public static void printBoard() {
		System.out.println("---------");
		for (int i = 0; i < 3; i++) {
			System.out.print("| ");
			for (int j = 0; j < 3; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println("|");
		}
		System.out.println("---------");
	}
	
	public static boolean verifyCoordinatesFormat(String coordinates) {
		return verifyCoordinatesAreNumbers(coordinates) && 
				verifyCoordinatesWithinBounds(coordinates);
	}
	
	private static boolean verifyCoordinatesWithinBounds(String coordinates) {
		boolean firstChar = coordinates.charAt(0) >= '1' && coordinates.charAt(0) <= '3';
		boolean thirdChar = coordinates.charAt(2) >= '1' && coordinates.charAt(2) <= '3';
		if (firstChar && thirdChar) {
			return true;
		}
		
		System.out.println("Coordinates should be from 1 to 3!");
		return false;
	}

	private static boolean verifyCoordinatesAreNumbers(String coordinates) {
		if (coordinates.length() == 3) {
			boolean firstChar = coordinates.charAt(0) >= '0' && coordinates.charAt(0) <= '9';
			boolean secondChar = coordinates.charAt(1) == ' ';
			boolean thirdChar = coordinates.charAt(2) >= '0' && coordinates.charAt(2) <= '9';
			if (firstChar && secondChar && thirdChar) {
				return true;
			}
		}
		System.out.println("You should enter numbers!");
		return false;
	}

	private static boolean applyCoordinates(String coordinates) {
		int column = Integer.parseInt(coordinates.substring(0, 1)) - 1;
		int row = 3 - Integer.parseInt(coordinates.substring(2, 3));
		
		if (matrix[row][column] == '_') {
			matrix[row][column] = ++count % 2 == 0 ? 'O' : 'X';
			return true;
		} else {
			System.out.println("This cell is occupied! Choose another one!");
			return false;
		}
	}
	
	private static boolean verifyGameEnded() {
		
		// look for vertical or horizontal lines
		for (int i = 0; i < 3; i++) {
			if (matrix[i][0] == matrix[i][1] && matrix[i][1] == matrix[i][2]) {
				if (matrix[i][0] == 'X') {
					results = "X wins";
					return true;
				} else if (matrix[i][0] == 'O') {
					results = "O wins";
					return true;
				}
			}
			if (matrix[0][i] == matrix[1][i] && matrix[1][i] == matrix[2][i]) {
				if (matrix[0][i] == 'X') {
					results = "X wins";
					return true;
				} else if (matrix[0][i] == 'O') {
					results = "O wins";
					return true;
				}
			}
		}
		
		// look for diagonal lines
		if ((matrix[0][0] == matrix[1][1] && matrix[1][1] == matrix[2][2]) ||
				(matrix[0][2] == matrix[1][1] && matrix[1][1] == matrix[2][0])) {
			if (matrix[1][1] == 'X') {
				results = "X wins";
				return true;
			} else if (matrix[1][1] == 'O') {
				results = "O wins";
				return true;
			}
		}
		
		// look for empty spaces
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] == '_') {
					results = "Game not finished";
					return false;
				}
			}
		}
		
		results = "Draw";
		return true;
	}


	public static boolean requestMove() {
		System.out.print("Enter the coordinates: ");
		String coordinates = scanner.nextLine();
		
		if (verifyCoordinatesFormat(coordinates) && applyCoordinates(coordinates)) {
			return verifyGameEnded();
		} else {
			return requestMove();
		}
	}

	private static void readInput() {

		String symbols = scanner.nextLine();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				matrix[i][j] = symbols.charAt(i * 3 + j);
				if (matrix[i][j] != '_') {
					count++;
				}
			}
		}
	}
	
	private static void pritnResults() {
		System.out.println(results);
	}
	
	public static void main(String[] args) {
		
		readInput();
		printBoard();
		requestMove();
		printBoard();
		pritnResults();
	}

}
