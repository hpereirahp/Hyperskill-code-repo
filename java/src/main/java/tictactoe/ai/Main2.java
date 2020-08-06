package tictactoe.ai;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main2 {
	
	public enum TicTacToeDifficulty {
		EASY, NORMAL, HARD;
	}

	public static class TicTacToe {
		
		private char[][] board;
		private int count;
		private String results;
		private final Scanner scanner;
		private TicTacToeDifficulty difficulty;
		
		public TicTacToe(Scanner scanner, TicTacToeDifficulty difficulty) {
			this.count = 0;
			this.results = "";
			this.board = new char[3][3];
			this.scanner = scanner;
			this.difficulty = difficulty;
		}
		
		public String startGame() {
			this.count = 0;
			this.results = "";

			prepareEmptyBoard();
			printBoard();
			requestMove();
			
			return results;
		}
		
		private void prepareEmptyBoard() {
			// populate empty array
			for (int i = 0; i < board.length; i++) {
				Arrays.fill(board[i], ' ');
			}
		}

		private void printBoard() {
			System.out.println("---------");
			for (int i = 0; i < board.length; i++) {
				System.out.print("| ");
				for (int j = 0; j < board[i].length; j++) {
					System.out.print(board[i][j] + " ");
				}
				System.out.println("|");
			}
			System.out.println("---------");
		}
		
		private boolean verifyCoordinatesFormat(String coordinates) {
			return verifyCoordinatesAreNumbers(coordinates) && 
					verifyCoordinatesWithinBounds(coordinates);
		}
		
		private boolean verifyCoordinatesWithinBounds(String coordinates) {
			boolean firstChar = coordinates.charAt(0) >= '1' && coordinates.charAt(0) <= '3';
			boolean thirdChar = coordinates.charAt(2) >= '1' && coordinates.charAt(2) <= '3';
			if (firstChar && thirdChar) {
				return true;
			}
			
			System.out.println("Coordinates should be from 1 to 3!");
			return false;
		}

		private boolean verifyCoordinatesAreNumbers(String coordinates) {
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

		private boolean applyCoordinates(String coordinates) {
			int column = Integer.parseInt(coordinates.substring(0, 1)) - 1;
			int row = 3 - Integer.parseInt(coordinates.substring(2, 3));
			
			if (board[row][column] == ' ') {
				board[row][column] = ++count % 2 == 0 ? 'O' : 'X';
				printBoard();
				return true;
			} else {
				System.out.println("This cell is occupied! Choose another one!");
				return false;
			}
		}
		
		private boolean verifyGameEnded() {
			
			// look for vertical or horizontal lines
			for (int i = 0; i < 3; i++) {
				if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
					if (board[i][0] == 'X') {
						results = "X wins";
						return true;
					} else if (board[i][0] == 'O') {
						results = "O wins";
						return true;
					}
				}
				if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
					if (board[0][i] == 'X') {
						results = "X wins";
						return true;
					} else if (board[0][i] == 'O') {
						results = "O wins";
						return true;
					}
				}
			}
			
			// look for diagonal lines
			if ((board[0][0] == board[1][1] && board[1][1] == board[2][2]) ||
					(board[0][2] == board[1][1] && board[1][1] == board[2][0])) {
				if (board[1][1] == 'X') {
					results = "X wins";
					return true;
				} else if (board[1][1] == 'O') {
					results = "O wins";
					return true;
				}
			}
			
			// look for empty spaces
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					if (board[i][j] == ' ') {
						return false;
					}
				}
			}
			
			results = "Draw";
			return true;
		}

		private boolean requestAIMove() {

			int row = 0;
			int column = 0;

			switch (difficulty) {
				case EASY: 
					Random random = new Random();
					int move = random.nextInt(9 - count);
					for (int i = 0; i < board.length && move >= 0; i++) {
						for (int j = 0; j < board.length; j++) {
							if (board[i][j] == ' ') {
								if (--move < 0) {
									row = i;
									column = j;
									break;
								}
							}
						}
					}
					break;
				case NORMAL: 
					break;
				case HARD: 
					break;
				default:
					break;
			}			
			
			System.out.printf("Making move level \"%s\"%n", difficulty.toString().toLowerCase());
			board[row][column] = 'O';
			count++;
			printBoard();
			
			return requestMove();
		}
		
		private boolean requestMove() {
			System.out.print("Enter the coordinates: ");
			String coordinates = scanner.nextLine();
			
			if (verifyCoordinatesFormat(coordinates) && applyCoordinates(coordinates)) {
				return verifyGameEnded() ? true : requestAIMove();
			} else {
				return requestMove();
			}
		}
		
	}
	
	
	public static void main(String[] args) {
		final Scanner scanner = new Scanner(System.in);
		TicTacToe ticTacToe = new TicTacToe(scanner, TicTacToeDifficulty.EASY);
		ticTacToe.startGame();
	}
}
