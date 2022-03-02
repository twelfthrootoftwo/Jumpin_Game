package comp1110.ass1;

import org.junit.jupiter.api.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(value = 1000, unit = MILLISECONDS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class IsCellEmptyTest {

    private static final State[] BOARD = new State[]{
            State.EMPTY, State.EMPTY, State.EMPTY, State.MUSHROOM, State.EMPTY,
            State.EMPTY, State.MUSHROOM, State.CREAM_RABBIT, State.FOX, State.FOX,
            State.EMPTY, State.EMPTY, State.MUSHROOM, State.EMPTY, State.EMPTY,
            State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY,
            State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY, State.EMPTY
    };

    private static final Position rabbitPosition = new Position();
    private static final Position[] foxPositions = new Position[2];

    static {
        rabbitPosition.setX(2);
        rabbitPosition.setY(1);
        for (int i = 0; i < 2; i++) foxPositions[i] = new Position();
        foxPositions[0].setX(3);
        foxPositions[0].setY(1);
        foxPositions[1].setX(4);
        foxPositions[1].setY(1);
    }

    private static final boolean[] EMPTY_CELLS = new boolean[]{true, true, true, false, true,
                                                               true, false, false, false, false,
                                                               true, true, false, true, true,
                                                               true, true, true, true, true,
                                                               true, true, true, true, true};

    private void emptyCellTest(Jumpin jumpin, Position position, boolean expected) {
        boolean out = jumpin.isCellEmpty(position);
        assertEquals(expected, out, "For state " + jumpin.stateToString(false) + ":\n" +
                "Expected the statement \"Position (" + position.getX() +
                "," + position.getY() + ") is empty\" to be " + expected + ", but instead got " + out + ".");
    }

    @Test
    public void testEmptyCell() {
        Jumpin jumpin = new Jumpin(Challenge.CHALLENGES[12]);
        jumpin.setState(BOARD);
        jumpin.getRabbits()[0].setPosition(rabbitPosition);
        jumpin.getFoxes()[0].setHeadPosition(foxPositions[0]);
        jumpin.getFoxes()[0].setTailPosition(foxPositions[1]);
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                Position position = new Position(x, y);
                emptyCellTest(jumpin, position, EMPTY_CELLS[5 * y + x]);
            }
        }
    }

}
