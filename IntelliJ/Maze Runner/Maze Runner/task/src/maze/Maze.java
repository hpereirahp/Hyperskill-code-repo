package maze;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class Maze implements Serializable {

    private int rows;
    private int columns;
    private int graphHeight;
    private int graphWidth;
    private int[][] matrix;
    private int[] tree;
    private String[][] maze;

    private static final String FREE = "  ";
    private static final String WALL = "\u2588\u2588";
    private static final Random RANDOM = new Random();

    public Maze(int size) {
        this(size, size);
    }

    public Maze(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.graphHeight = (rows - 1) / 2;
        this.graphWidth = (columns - 1) / 2;
        int size = graphHeight * graphWidth;
        this.matrix = new int[size][size];
        this.maze = new String[rows][columns];
    }

    public void build() {

        int value;
        for (int i = 0; i < matrix.length; i++) {

            if (i % graphWidth != graphWidth - 1) {
                value = RANDOM.nextInt(6) + 1;
                matrix[i][i + 1] = value;
                matrix[i + 1][i] = value;
            }

            if (i / graphWidth != graphHeight - 1) {
                value = RANDOM.nextInt(6) + 1;
                matrix[i][i + graphWidth] = value;
                matrix[i + graphWidth][i] = value;
            }
        }

        tree = MinimumSpanningTree.primMST(matrix);


        // set entry and exit
        int entry = RANDOM.nextInt(graphHeight) * 2 + 1;
        int exit = RANDOM.nextInt(graphHeight) * 2 + 1;
        while (entry == exit) {
            exit = RANDOM.nextInt(graphHeight) * 2 + 1;
        }
        maze[entry][0] = FREE;
        maze[exit][columns - 1] = FREE;

        int currentNode = 0;
        for (int i = 1; i < rows - 1; i = i + 2) {

            for (int j = 1; j < columns; j = j + 2) {
                maze[i][j] = FREE;
                if (findLeftEdge(currentNode)) {
                    maze[i][j + 1] = FREE;
                }
                if (findBottomEdge(currentNode)) {
                    maze[i + 1][j] = FREE;
                }
                currentNode++;
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (maze[i][j] == null) {
                    maze[i][j] = WALL;
                }
            }
        }
    }

    private boolean findLeftEdge(int currentNode) {
        for (int i = 0; i < tree.length; i++) {
            if ((tree[i] == currentNode && i == currentNode + 1) ||
                    (i == currentNode && tree[i] == currentNode + 1)) {
                return true;
            }
        }
        return false;
    }

    private boolean findBottomEdge(int currentNode) {
        for (int i = 0; i < tree.length; i++) {
            if ((tree[i] == currentNode && i == currentNode + graphWidth) ||
                    (i == currentNode && tree[i] == currentNode + graphWidth)) {
                return true;
            }
        }
        return false;
    }

    public void print() {

//        Arrays.stream(matrix)
//                .map(Arrays::toString)
//                .forEach(System.out::println);
//
//        System.out.println();
//        System.out.println("Edge \tWeight");
//        for (int i = 1; i < matrix.length; i++) {
//            System.out.println(tree[i] + " - " + i + "\t" + matrix[i][tree[i]]);
//        }
//
//        System.out.println();

        Arrays.stream(maze)
                .map(elem -> String.join("", elem))
                .forEach(System.out::println);

    }
}
