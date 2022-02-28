package comp1110.ass1;

import org.junit.jupiter.api.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(value = 1000, unit = MILLISECONDS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class FoxCanMoveTest {

    private static final Direction[][] CONFLICTING_DIRECTIONS = new Direction[][]{
            new Direction[]{Direction.SOUTH},
            new Direction[]{Direction.EAST},
            new Direction[]{Direction.WEST},
            new Direction[]{Direction.NORTH},
            new Direction[]{Direction.WEST, Direction.WEST},
            new Direction[]{Direction.EAST, Direction.EAST},
            new Direction[]{Direction.WEST},
            new Direction[]{Direction.NORTH},
            new Direction[]{Direction.SOUTH},
            new Direction[]{Direction.NORTH, Direction.WEST},
            new Direction[]{Direction.SOUTH, Direction.EAST},
            new Direction[]{Direction.NORTH, Direction.WEST},
            new Direction[]{Direction.EAST, Direction.WEST},
            new Direction[]{Direction.EAST},
            new Direction[]{Direction.SOUTH, Direction.WEST},
            new Direction[]{Direction.SOUTH}
    };

    private static final boolean[][] CAN_FOX_MOVE_FORWARD = new boolean[][]{
            new boolean[]{false},
            new boolean[]{true, true},
            new boolean[]{false},
            new boolean[]{true, true},
            new boolean[]{true, true},
            new boolean[]{true, false},
            new boolean[]{true, false},
            new boolean[]{true},
            new boolean[]{true, true},
            new boolean[]{true, false},
            new boolean[]{true, true},
            new boolean[]{true, true},
            new boolean[]{true},
            new boolean[]{true},
            new boolean[]{false},
            new boolean[]{true, false}
    };

    private static final boolean[][] CAN_FOX_MOVE_BACKWARD = new boolean[][]{
            new boolean[]{true, true},
            new boolean[]{true},
            new boolean[]{true},
            new boolean[0], // Challenge #48 doesn't have any foxes
            new boolean[]{false, false},
            new boolean[]{true},
            new boolean[]{false, true},
            new boolean[]{false, false},
            new boolean[]{false, true},
            new boolean[]{false},
            new boolean[]{true},
            new boolean[]{false, true},
            new boolean[]{false, true},
            new boolean[]{false, false},
            new boolean[]{false, true},
            new boolean[]{false}
    };

    private void test(Fox fox, Direction direction, Jumpin jumpin, boolean expected) {
        if (expected) {
            assertTrue(fox.canMove(direction, jumpin), "On board state " + jumpin.stateToString(false) + ":\n" +
                    "Expected fox " + fox + " to be able to move in direction " +
                    direction + ", but fox.canMove(" + direction + ") returned false!");
        }
        else {
            assertFalse(fox.canMove(direction, jumpin), "On board state " + jumpin.stateToString(false) + ":\n" +
                    "Expected fox " + fox + " to not be able to move in direction " +
                    direction + ", but fox.canMove(" + direction + ") returned true!");
        }
    }

    @Test
    public void testConflictingDirections() {
        for (int i = 0; i < CONFLICTING_DIRECTIONS.length; i++) {
            Jumpin jumpin = new Jumpin(Challenge.CHALLENGES[i + 12]);
            for (int j = 0; j < CONFLICTING_DIRECTIONS[i].length; j++) {
                test(jumpin.getFoxes()[j], CONFLICTING_DIRECTIONS[i][j], jumpin, i % 2 == 1);
            }
        }
    }

    @Test
    public void testMoveForward() {
        for (int i = 0; i < CAN_FOX_MOVE_FORWARD.length; i++) {
            Jumpin jumpin = new Jumpin(Challenge.CHALLENGES[i + 28]);
            for (int j = 0; j < CAN_FOX_MOVE_FORWARD[i].length; j++) {
                Fox fox = jumpin.getFoxes()[j];
                test(fox, fox.getDirection(), jumpin, CAN_FOX_MOVE_FORWARD[i][j]);
            }
        }
    }

    @Test
    public void testMoveBackward() {
        for (int i = 0; i < CAN_FOX_MOVE_BACKWARD.length; i++) {
            Jumpin jumpin = new Jumpin(Challenge.CHALLENGES[i + 44]);
            for (int j = 0; j < CAN_FOX_MOVE_BACKWARD[i].length; j++) {
                Fox fox = jumpin.getFoxes()[j];
                test(fox, fox.getDirection().getOpposite(), jumpin, CAN_FOX_MOVE_BACKWARD[i][j]);
            }
        }
    }

}
