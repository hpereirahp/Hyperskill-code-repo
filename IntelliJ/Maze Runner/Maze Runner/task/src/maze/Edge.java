package maze;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Edge implements Comparable<Edge> {

    private int value;
    private Node from;
    private Node to;

    public Edge(Node from, Node to) {
        this.value = new Random().nextInt(5);
        this.from = from;
        this.to = to;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(@NotNull Edge edge) {
        if (this.value == edge.value) {
            int compareNode = this.from.compareTo(edge.from);
            if (compareNode != 0) {
                return compareNode;
            }
            return this.to.compareTo(edge.to);
        } else if (this.value > edge.value) {
            return 1;
        } else {
            return -1;
        }
    }
}
