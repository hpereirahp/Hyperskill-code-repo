package tictactoe.ai;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main3 {
	
	enum GameState {
		NOT_FINISHED,
		X_WINS,
		O_WINS,
		DRAW;

		@Override
		public String toString() {
			switch (this) {
				case NOT_FINISHED:
					return "Game not finished";
				case X_WINS:
					return "X wins";
				case O_WINS:
					return "O wins";
				case DRAW:
					return "Draw";
				default:
					return "Unknown";
			}
		}
	}

	enum GameMode {
		EASY,
		NORMAL,
		HARD,
		USER;
	}
	
	static class Board {
		
		private char[][] board;
		private int count;
		
		Board() {
			this.board = new char[3][3];
			this.count = 0;
			resetBoard();
		}
		
		private int getCount() {
			return count;
		}

		public char getCell(int row, int column) {
			return board[row][column];
		}

		public void setCell(int row, int column) {
			board[row][column] = ++count % 2 == 0 ? 'O' : 'X';
		}

		public void printBoard() {
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
		
		public void resetBoard() {
			this.count = 0;
			// populate empty array
			for (int i = 0; i < board.length; i++) {
				Arrays.fill(board[i], ' ');
			}
		}

		public GameState isGameFinished() {
			
			// look for vertical or horizontal lines
			for (int i = 0; i < 3; i++) {
				if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
					if (board[i][0] == 'X') {
						return GameState.X_WINS;
					} else if (board[i][0] == 'O') {
						return GameState.O_WINS;
					}
				}
				if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
					if (board[0][i] == 'X') {
						return GameState.X_WINS;
					} else if (board[0][i] == 'O') {
						return GameState.O_WINS;
					}
				}
			}
			
			// look for diagonal lines
			if ((board[0][0] == board[1][1] && board[1][1] == board[2][2]) ||
					(board[0][2] == board[1][1] && board[1][1] == board[2][0])) {
				if (board[1][1] == 'X') {
					return GameState.X_WINS;
				} else if (board[1][1] == 'O') {
					return GameState.O_WINS;
				}
			}
			
			// look for empty spaces
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					if (board[i][j] == ' ') {
						return GameState.NOT_FINISHED;
					}
				}
			}

			return GameState.DRAW;
		}
	}
	
	static class TicTacToe {

		private final Scanner scanner;
		private Board board;
		private GameMode player1;
		private GameMode player2;
		private GameState result;
		
		TicTacToe(Scanner scanner) {
			this.scanner = scanner;
			this.board = new Board();
			this.result = GameState.NOT_FINISHED;
		}

		public String getGameResult() {
			return result.toString();
		}

		public void startGame(GameMode player1, GameMode player2) {
			
			this.result = GameState.NOT_FINISHED;
			this.player1 = player1;
			this.player2 = player2;

			board.resetBoard();
			board.printBoard();
			do {
				nextMove();
			} while ((this.result = board.isGameFinished()) == GameState.NOT_FINISHED);
		}
		
		private GameMode getNextPlayer() {
			return board.getCount() % 2 == 0 ? player1 : player2;
		}

		private void nextMove() {
			GameMode player = getNextPlayer();
			switch (player) {
				case EASY:
				case NORMAL:
				case HARD:
					requestAIMove(player);
					break;
				case USER:
					requestHumanMove();
					break;
				default:
					System.err.println("Unknown game mode");
					break;
			}
			board.printBoard();
		}
	    
		private void requestAIMove(GameMode difficulty) {

			int row = 0;
			int column = 0;

			switch (difficulty) {
				case EASY: 
					Random random = new Random();
					do {
						row = random.nextInt(3);
						column = random.nextInt(3);
					} while(board.getCell(row, column) != ' ');
					break;
				case NORMAL: 
					// TODO
					break;
				case HARD: 
					// TODO
					break;
				default:
					System.err.println("Unknown game difficulty");
					break;
			}			
			
			System.out.printf("Making move level \"%s\"%n", difficulty.toString().toLowerCase());
			board.setCell(row, column);
		}
		
		private void requestHumanMove() {
			System.out.print("Enter the coordinates: ");
			String coordinates = scanner.nextLine();
			
			if (!(verifyCoordinatesFormat(coordinates) && applyCoordinates(coordinates))) {
				requestHumanMove();
			}
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
			
			if (board.getCell(row, column) == ' ') {
				board.setCell(row, column);
				return true;
			} else {
				System.out.println("This cell is occupied! Choose another one!");
				return false;
			}
		}
		
	}

	private static boolean verifyCommand(String[] command) {
		
		if (command.length != 3 || !command[0].equals("start")) {
			return false;
		} 

		try {
			GameMode.valueOf(command[1].toUpperCase());
			GameMode.valueOf(command[2].toUpperCase());
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static void main(String[] args) {

		final Scanner scanner = new Scanner(System.in);
		TicTacToe ticTacToe = new TicTacToe(scanner);
		
		while (true) {
			System.out.print("Input command: ");
			String[] command = scanner.nextLine().trim().split(" ");
			
			if (command.length == 1 && command[0].equals("exit")) {
				break;
			} else if (!verifyCommand(command)) {
				System.out.println("Bad parameters!");
			} else {
				ticTacToe.startGame(
						GameMode.valueOf(command[1].toUpperCase()), 
						GameMode.valueOf(command[2].toUpperCase()));
				System.out.println(ticTacToe.getGameResult());
			}
		}
		
		scanner.close();
	}

}
