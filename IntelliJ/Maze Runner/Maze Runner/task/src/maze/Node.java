package maze;

import org.jetbrains.annotations.NotNull;

public class Node implements Comparable<Node> {

    private int row;
    private int column;

    public Node(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public int compareTo(@NotNull Node node) {
        if (this.row == node.row) {
            if (this.column == node.column) {
                return 0;
            } else if (this.column > node.column) {
                return 1;
            } else {
                return -1;
            }
        } else if (this.row > node.row) {
            return 1;
        } else {
            return -1;
        }
    }
}
