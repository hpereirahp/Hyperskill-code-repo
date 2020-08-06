package tictactoe.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main5 {
	
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
		MEDIUM,
		HARD,
		USER;
		
		public Player getPlayer(Scanner scanner) {
			switch (this) {
				case EASY:
					return new EasyAI(scanner);
				case MEDIUM:
					return new MediumAI(scanner);
				case HARD:
					return new HardAI(scanner);
				case USER:
				default:
					return new Human(scanner);
			}
		}
	}
	
	static class Move {
		private int row;
		private int column;
		
		Move(int row, int column) {
			this.row = row;
			this.column = column;
		}

		public int getRow() {
			return row;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public int getColumn() {
			return column;
		}

		public void setColumn(int column) {
			this.column = column;
		}
	}

	static abstract class Player {
		
		protected final Scanner scanner;
		
		public Player(Scanner scanner) {
			this.scanner = scanner;
		}
		
		protected void confirmMove(Move move, Board board, String difficulty) {
			System.out.printf("Making move level \"%s\"%n", difficulty);
			board.setCell(move.getRow(), move.getColumn());
		}
		
		public abstract void move(Board board);
	}
	
	static class Human extends Player {
		
		public Human(Scanner scanner) {
			super(scanner);
		}
		
		@Override
		public void move(Board board) {
			System.out.print("Enter the coordinates: ");
			String coordinates = scanner.nextLine();
			
			if (!(verifyCoordinatesFormat(coordinates) && applyCoordinates(coordinates, board))) {
				move(board);
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

		private boolean applyCoordinates(String coordinates, Board board) {
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
	
	static class EasyAI extends Player {

		public EasyAI(Scanner scanner) {
			super(scanner);
		}

		@Override
		public void move(Board board) {
			confirmMove(buildMove(board), board, "easy");
		}
		
		protected Move buildMove(Board board) {

			Random random = new Random();
			int row = 0;
			int column = 0;
			
			do {
				row = random.nextInt(3);
				column = random.nextInt(3);
			} while(board.getCell(row, column) != ' ');
			
			return new Move(row, column);
		}
	}
	
	static class MediumAI extends EasyAI {

		public MediumAI(Scanner scanner) {
			super(scanner);
		}

		@Override
		public void move(Board board) {
			confirmMove(this.buildMove(board), board, "medium");
		}
		
		@Override
		protected Move buildMove(Board board) {

			char myChar = board.getCount() % 2 == 0 ? 'X' : 'O';
			// can I win
			Move move = board.canPlayerWin(myChar);
			// otherwise, can the opponent win
			move = move == null ? board.canPlayerWin(myChar == 'X' ? 'O' : 'X') : move;	
			// otherwise, play random
			return move == null ? super.buildMove(board) : move;
		}
	}

	static class HardAI extends MediumAI {

		public HardAI(Scanner scanner) {
			super(scanner);
		}

		@Override
		public void move(Board board) {			
			confirmMove(this.buildMove(board), board, "hard");
		}
		
		@Override 
		protected Move buildMove(Board board) {
			return new Minimax().getBestMove(board);
		}
	}
	
	static class Minimax {
		
		char aiPlayer;
		char oppoPlayer;
		
		public Minimax() {
		}
		
		public Move getBestMove(Board board) {
			
			this.aiPlayer = board.getCount() % 2 == 0 ? 'X' : 'O';
			this.oppoPlayer = board.getCount() % 2 == 0 ? 'O' : 'X';
			
			List<Move> availableSpots = board.getAvailableSpots();
			int bestScore = Integer.MIN_VALUE;
			int bestMove = 0;
			
			for (int i = 0; i < availableSpots.size(); i++) {
				Move move = availableSpots.get(i);
				
				board.setCell(move.getRow(), move.getColumn(), aiPlayer);
				int score = minimax(board, oppoPlayer);
				board.setCell(move.getRow(), move.getColumn(), ' ');

				if (bestScore < score) {
					bestScore = score;  
					bestMove = i;
				}
			}
			
			return availableSpots.get(bestMove);
		}
		
		private int minimax(Board newBoard, char player) {
			
			switch (newBoard.isGameFinished()) {
				case X_WINS:
					return aiPlayer == 'X' ? 10 : -10;
				case O_WINS:
					return aiPlayer == 'O' ? 10 : -10;
				case DRAW:
					return 0;
				default:
					break;
			}

			List<Move> availableSpots = newBoard.getAvailableSpots();
			int[] moves = new int[availableSpots.size()]; 
			
			for (int i = 0; i < availableSpots.size(); i++) {
				Move move = availableSpots.get(i);
				
				newBoard.setCell(move.getRow(), move.getColumn(), player);
				moves[i] = minimax(newBoard, player == aiPlayer ? oppoPlayer : aiPlayer);
				newBoard.setCell(move.getRow(), move.getColumn(), ' ');
			}
			
			int bestScore;
			if (player == aiPlayer) {
				bestScore = Integer.MIN_VALUE;
				for (int i = 0; i < moves.length; i++) {
					if (moves[i] > bestScore) {
						bestScore = moves[i];
					}
				}
			} else {
				bestScore = Integer.MAX_VALUE;
				for (int i = 0; i < moves.length; i++) {
					if (moves[i] < bestScore) {
						bestScore = moves[i];
					}
				}
			}
			return bestScore;
		}
	}
	
	static class Board {
		
		private char[][] board;
		private int count;
		
		Board() {
			this.board = new char[3][3];
			this.count = 0;
			resetBoard();
		}
		
		public List<Move> getAvailableSpots() {
			List<Move> availableSpots = new ArrayList<>();

			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					if (board[i][j] == ' ') {
						availableSpots.add(new Move(i, j));
					}
				}
			}
			
			return availableSpots;
		}

		public int getCount() {
			return count;
		}

		public char getCell(int row, int column) {
			return board[row][column];
		}

		public void setCell(int row, int column) {
			board[row][column] = ++count % 2 == 0 ? 'O' : 'X';
		}

		public void setCell(int row, int column, char value) {
			count++;
			board[row][column] = value;
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
		
		public Move canPlayerWin(char player) {
			
			// look for vertical or horizontal lines
			for (int i = 0; i < 3; i++) {
				if (board[i][0] == player && board[i][1] == player && board[i][2] == ' ') {
					return new Move(i, 2);
				}
				if (board[i][0] == player && board[i][1] == ' ' && board[i][2] == player) {
					return new Move(i, 1);
				}
				if (board[i][0] == ' ' && board[i][1] == player && board[i][2] == player) {
					return new Move(i, 0);	
				}
				
				if (board[0][i] == player && board[1][i] == player && board[2][i] == ' ') {
					return new Move(2, i);
				}
				if (board[0][i] == player && board[1][i] == ' ' && board[2][i] == player) {
					return new Move(1, i);
				}
				if (board[0][i] == ' ' && board[1][i] == player && board[2][i] == player) {
					return new Move(0, i);
				}
			}
			
			// look for diagonal lines
			if (board[0][0] == player && board[1][1] == player && board[2][2] == ' ') {
				return new Move(2, 2);
			}
			if (board[0][0] == player && board[1][1] == ' ' && board[2][2] == player) {
				return new Move(1, 1);
			}
			if (board[0][0] == ' ' && board[1][1] == player && board[2][2] == player) {
				return new Move(0, 0);
			}
			if (board[2][0] == player && board[1][1] == player && board[0][2] == ' ') {
				return new Move(0, 2);
			}
			if (board[2][0] == player && board[1][1] == ' ' && board[0][2] == player) {
				return new Move(1, 1);
			}
			if (board[2][0] == ' ' && board[1][1] == player && board[0][2] == player) {
				return new Move(2, 0);
			}
						
			return null;
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

		private Board board;
		private Player player1;
		private Player player2;
		private GameState result;
		
		TicTacToe() {
			this.board = new Board();
			this.result = GameState.NOT_FINISHED;
		}

		public String getGameResult() {
			return result.toString();
		}

		public void startGame(Player player1, Player player2) {
			
			this.result = GameState.NOT_FINISHED;
			this.player1 = player1;
			this.player2 = player2;

			board.resetBoard();
			board.printBoard();
			do {
				nextMove();
			} while ((this.result = board.isGameFinished()) == GameState.NOT_FINISHED);
		}
		
		private Player getNextPlayer() {
			return board.getCount() % 2 == 0 ? player1 : player2;
		}

		private void nextMove() {
			Player player = getNextPlayer();
			player.move(board);
			board.printBoard();
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
		TicTacToe ticTacToe = new TicTacToe();
		
		while (true) {
			System.out.print("Input command: ");
			String[] command = scanner.nextLine().trim().split(" ");
			
			if (command.length == 1 && command[0].equals("exit")) {
				break;
			} else if (!verifyCommand(command)) {
				System.out.println("Bad parameters!");
			} else {
				ticTacToe.startGame(
						GameMode.valueOf(command[1].toUpperCase()).getPlayer(scanner), 
						GameMode.valueOf(command[2].toUpperCase()).getPlayer(scanner));
				System.out.println(ticTacToe.getGameResult());
			}
		}
		
		scanner.close();
	}

}
