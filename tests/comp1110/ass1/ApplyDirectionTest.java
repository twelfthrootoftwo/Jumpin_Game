package comp1110.ass1;

import org.junit.jupiter.api.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(value = 1000, unit = MILLISECONDS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ApplyDirectionTest {

    private static final Position[] ON_BOARD_POSITIONS
            = new Position[]{new Position(0,0), new Position(1,0), new Position(4,0),
                             new Position(1,4), new Position(3,0), new Position(1,2),
                             new Position(0,1), new Position(1,4), new Position(2,2),
                             new Position(2,1), new Position(2,3), new Position(4,2)};

    private static final Position[] ON_BOARD_POSITIONS_DIRS_APPLIED
            = new Position[]{new Position(0,-1), new Position(2,0), new Position(4,1),
                             new Position(0,4), new Position(3,-1), new Position(2,2),
                             new Position(0,2), new Position(0,4), new Position(2,1),
                             new Position(3,1), new Position(2,4), new Position(3,2)};

    private static final Position[] OFF_BOARD_POSITIONS
            = new Position[]{new Position(-2,-1), new Position(-1,5), new Position(6,-1),
                             new Position(5,6), new Position(-1,2), new Position(-2,2),
                             new Position(2,-1), new Position(0,-2), new Position(5,4),
                             new Position(5,0), new Position(4,5), new Position(3,5)};

    private static final Position[] OFF_BOARD_POSITIONS_DIRS_APPLIED
            = new Position[]{new Position(-2,-2), new Position(0,5), new Position(6,0),
                             new Position(4,6), new Position(-1,1), new Position(-1,2),
                             new Position(2,0), new Position(-1,-2), new Position(5,3),
                             new Position(6,0), new Position(4,6), new Position(2,5)};

    private void checkPosition(Position expected, Position in, Direction dir) {
        Position out = in.applyDirection(dir);
        assertEquals(expected, out, "Expected applying direction " + dir + " to position " +
                "(" + in.getX() + "," + in.getY() + ") to return position (" + expected.getX() + "," +
                expected.getY() + "), but instead got (" + out.getX() + "," + out.getY() + ").");
    }

    private void test(Position[] positions, Position[] positionsDirsApplied) {
        for (int i = 0; i < positions.length; i++) {
            Position in = positions[i];
            Position expected = positionsDirsApplied[i];
            Direction dir = Direction.values()[i % Direction.values().length];
            checkPosition(expected, in, dir);
        }
    }

    @Test
    public void testFromOnBoard() {
        test(ON_BOARD_POSITIONS, ON_BOARD_POSITIONS_DIRS_APPLIED);
    }

    @Test
    public void testFromOffBoard() {
        test(OFF_BOARD_POSITIONS, OFF_BOARD_POSITIONS_DIRS_APPLIED);
    }

}
