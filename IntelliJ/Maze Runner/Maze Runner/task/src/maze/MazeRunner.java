package maze;

import java.io.IOException;
import java.util.Scanner;

public class MazeRunner {

    private final Scanner scanner;
    private Maze maze;

    public MazeRunner() {
        this.scanner = new Scanner(System.in);
        this.maze = null;
    }

    public void run() {
        boolean canRun = true;
        while (canRun) {
            printMenu();

            switch (Integer.parseInt(scanner.nextLine())) {
                case 1: // Generate
                    System.out.println("Enter the size of a new maze: ");
                    maze = new Maze(Integer.parseInt(scanner.nextLine()));
                    maze.build();
                    maze.print();
                    break;
                case 2: // Load
                    try {
                        Maze loadedMaze = (Maze) SerializationUtils.deserialize(scanner.nextLine());
                        if (loadedMaze != null) {
                            maze = loadedMaze;
                        }
                    } catch (Exception e) {
                        System.out.println("Cannot load the maze. It has an invalid format");
                    }
                    break;
                case 3: // Save
                    if (maze == null) {
                        System.out.println("Incorrect option. Please try again");
                    } else {
                        try {
                            SerializationUtils.serialize(maze, scanner.nextLine());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 4: // Display
                    if (maze == null) {
                        System.out.println("Incorrect option. Please try again");
                    } else {
                        maze.print();
                    }
                    break;
                case 0: // Exit
                    canRun = false;
                    break;
                default:
                    System.out.println("Incorrect option. Please try again");
                    break;
            }
            System.out.println("");
        }
        System.out.println("Bye!");
    }

    private void printMenu() {
        StringBuilder sb = new StringBuilder();

        sb.append("=== Menu ===\n1. Generate a new maze\n2. Load a maze\n");
        if (maze != null) {
            sb.append("3. Save the maze\n4. Display the maze\n");
        }
        sb.append("0. Exit");

        System.out.println(sb.toString());
    }
}
