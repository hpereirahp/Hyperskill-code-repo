package tictactoe.ai.solution;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class HardAI implements Player {
    final List<Point> iterationOrder;

    public HardAI() {
        this.iterationOrder = List.of(
                new Point(2, 2),

                new Point(1, 3),
                new Point(3, 1),
                new Point(1, 1),
                new Point(3, 3),

                new Point(2, 3),
                new Point(3, 2),
                new Point(2, 1),
                new Point(1, 2)
        );
    }

    @Override
    public void move(Field field) {
        System.out.println(field);
        System.out.println("Making move level \"hard\"");

        Symbol symbol = field.nextSymbol();

        Point next = getBestMove(field, symbol);

        field.set(next);
    }

    private Point getBestMove(Field field, Symbol symbol) {
        Stream<Point> stream = iterationOrder.stream().filter(field::isFree);
        Comparator<Point> comparator = Comparator.comparing(point -> getScore(field, point, symbol));

        return (symbol == Symbol.X ? stream.max(comparator) : stream.min(comparator)).orElseThrow();
    }

    private int getScore(Field field, Point point, Symbol symbol) {
        return getScore(field, point.getX(), point.getY(), symbol);
    }

    private int getScore(Field field, int x, int y, Symbol symbol) {
        field.set(x, y);


        if (!field.continues()) {
            Symbol winner = field.getWinner();
            field.unset(x, y);

            if (winner == Symbol.EMPTY) {
                return 0;
            }
            return winner == Symbol.X ? 1 : -1;
        } else {
            Symbol opposite = symbol.opposite();

            IntStream stream = iterationOrder.stream()
                    .filter(field::isFree)
                    .mapToInt(point -> getScore(field, point, opposite));
            int score = (opposite == Symbol.X ? stream.max() : stream.min()).orElseThrow();

            field.unset(x, y);
            return score;
        }
    }
}