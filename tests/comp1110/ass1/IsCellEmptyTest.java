package comp1110.ass1;

import org.junit.jupiter.api.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(value = 1000, unit = MILLISECONDS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class IsCellEmptyTest {

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
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                Position position = new Position(x, y);
                emptyCellTest(jumpin, position, EMPTY_CELLS[5 * y + x]);
            }
        }
    }

}
