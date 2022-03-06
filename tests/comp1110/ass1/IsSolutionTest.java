package comp1110.ass1;

import org.junit.jupiter.api.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(value = 1000, unit = MILLISECONDS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class IsSolutionTest {

    private final static Challenge[] ONE_RABBIT_CHALLENGES
            = new Challenge[]{Challenge.CHALLENGES[12], Challenge.CHALLENGES[13], Challenge.CHALLENGES[14],
                              Challenge.CHALLENGES[15], Challenge.CHALLENGES[16], Challenge.CHALLENGES[17],
                              Challenge.CHALLENGES[18], Challenge.CHALLENGES[19], Challenge.CHALLENGES[20],
                              Challenge.CHALLENGES[21], Challenge.CHALLENGES[22], Challenge.CHALLENGES[23]};

    private static final Challenge[] TWO_RABBIT_CHALLENGES
            = new Challenge[]{Challenge.CHALLENGES[1], Challenge.CHALLENGES[2], Challenge.CHALLENGES[3],
                              Challenge.CHALLENGES[4], Challenge.CHALLENGES[5], Challenge.CHALLENGES[6],
                              Challenge.CHALLENGES[24], Challenge.CHALLENGES[25], Challenge.CHALLENGES[26],
                              Challenge.CHALLENGES[27], Challenge.CHALLENGES[28], Challenge.CHALLENGES[29],
                              Challenge.CHALLENGES[30], Challenge.CHALLENGES[31], Challenge.CHALLENGES[32],
                              Challenge.CHALLENGES[33], Challenge.CHALLENGES[34], Challenge.CHALLENGES[35],
                              Challenge.CHALLENGES[37], Challenge.CHALLENGES[39], Challenge.CHALLENGES[40],
                              Challenge.CHALLENGES[41], Challenge.CHALLENGES[42], Challenge.CHALLENGES[46]};

    private static final Challenge[] THREE_RABBIT_CHALLENGES
            = new Challenge[]{Challenge.CHALLENGES[7], Challenge.CHALLENGES[8], Challenge.CHALLENGES[9],
                              Challenge.CHALLENGES[10], Challenge.CHALLENGES[11], Challenge.CHALLENGES[38],
                              Challenge.CHALLENGES[43], Challenge.CHALLENGES[44], Challenge.CHALLENGES[45],
                              Challenge.CHALLENGES[47], Challenge.CHALLENGES[48], Challenge.CHALLENGES[49],
                              Challenge.CHALLENGES[50], Challenge.CHALLENGES[51], Challenge.CHALLENGES[52],
                              Challenge.CHALLENGES[53], Challenge.CHALLENGES[54], Challenge.CHALLENGES[55],
                              Challenge.CHALLENGES[56], Challenge.CHALLENGES[57], Challenge.CHALLENGES[58],
                              Challenge.CHALLENGES[59]};

    private void testIsSolution(Jumpin jumpin, Position[] solutionPositions, boolean solutionExpected) {
        for (int i = 0; i < solutionPositions.length; i++) {
            if (solutionPositions[i] != null) {
                Rabbit rabbit = jumpin.getRabbits()[i];
                jumpin.setCell(rabbit.getPosition(), State.EMPTY);
                rabbit.setPosition(solutionPositions[i]);
                jumpin.setCell(rabbit.getPosition(), rabbit.getType());
            }
        }
        if (solutionExpected) {
            assertTrue(jumpin.isSolution(), "Expected Jumpin' board state " +
                    jumpin.stateToString(false) +
                    " to be a solution, since every rabbit is in a rabbit hole.");
        }
        else {
            assertFalse(jumpin.isSolution(), "Expected Jumpin' board state " +
                    jumpin.stateToString(false) +
                    " to not be a solution, because not all of the rabbits are in a rabbit hole.");
        }
    }

    @Test
    public void testNoRabbits() {
        for (int i = 0; i < 5; i++) {
            Jumpin jumpin = new Jumpin(Challenge.CHALLENGES[i]);
            for (int j = 0; j < jumpin.getRabbits().length; j++) {
                jumpin.getRabbits()[j] = null;
            }
            assertFalse(jumpin.isSolution(), "Expected Jumpin' board state " +
                    jumpin.stateToString(false) +
                    " to not be a solution, because there are no rabbits on the board.");
        }
    }

    @Test
    public void testOneRabbit() {
        for (int i = 0; i < ONE_RABBIT_CHALLENGES.length; i += 2) {
            Jumpin jumpin = new Jumpin(ONE_RABBIT_CHALLENGES[i]);
            testIsSolution(jumpin, new Position[0], false);
        }
        Position[] rabbitPositions = new Position[]{
                new Position(4,4), new Position(0,0),
                new Position(2,2), new Position(4,0),
                new Position(0,4), new Position(4,4)
        };
        for (int i = 1; i < ONE_RABBIT_CHALLENGES.length; i += 2) {
            Jumpin jumpin = new Jumpin(ONE_RABBIT_CHALLENGES[i]);
            testIsSolution(jumpin, new Position[]{rabbitPositions[i / 2]}, true);
        }
    }

    @Test
    public void testTwoRabbits() {
        for (int i = 0; i < TWO_RABBIT_CHALLENGES.length; i += 3) {
            Jumpin jumpin = new Jumpin(TWO_RABBIT_CHALLENGES[i]);
            testIsSolution(jumpin, new Position[0], false);
        }
        Position[][] rabbitPositions = new Position[][]{
                new Position[]{new Position(4,4)},
                new Position[]{new Position(4,0)},
                new Position[]{null, new Position(2,2)},
                new Position[]{null, new Position(0,4)},
                new Position[]{new Position(0,0)},
                new Position[]{new Position(0,4)},
                new Position[]{new Position(4,4)},
                new Position[]{null, new Position(2,2)}
        };
        for (int i = 1; i < TWO_RABBIT_CHALLENGES.length; i += 3) {
            Jumpin jumpin = new Jumpin(TWO_RABBIT_CHALLENGES[i]);
            testIsSolution(jumpin, rabbitPositions[i/3], false);
        }
        rabbitPositions = new Position[][]{
                new Position[]{new Position(2,2), new Position(4,0)},
                new Position[]{new Position(0,0), new Position(4,0)},
                new Position[]{new Position(4,4), new Position(2,2)},
                new Position[]{new Position(4,4), new Position(0,0)},
                new Position[]{new Position(0,4), new Position(2,2)},
                new Position[]{new Position(0,4), new Position(4,4)},
                new Position[]{new Position(0,4), new Position(4,4)},
                new Position[]{new Position(0,0), new Position(4,0)}
        };
        for (int i = 2; i < TWO_RABBIT_CHALLENGES.length; i += 3) {
            Jumpin jumpin = new Jumpin(TWO_RABBIT_CHALLENGES[i]);
            testIsSolution(jumpin, rabbitPositions[i/3], true);
        }
    }

    @Test
    public void testThreeRabbits() {
        for (int i = 0; i < THREE_RABBIT_CHALLENGES.length; i += 4) {
            Jumpin jumpin = new Jumpin(THREE_RABBIT_CHALLENGES[i]);
            testIsSolution(jumpin, new Position[0], false);
        }
        Position[][] rabbitPositions = new Position[][]{
                new Position[]{null, new Position(4,4)},
                new Position[]{null, new Position(0,0)},
                new Position[]{null, null, new Position(0,4)},
                new Position[]{new Position(2,2)},
                new Position[]{null, new Position(0,0)},
                new Position[]{null, null, new Position(4,0)}
        };
        for (int i = 1; i < THREE_RABBIT_CHALLENGES.length; i += 4) {
            Jumpin jumpin = new Jumpin(THREE_RABBIT_CHALLENGES[i]);
            testIsSolution(jumpin, rabbitPositions[i/4], false);
        }
        rabbitPositions = new Position[][]{
                new Position[]{new Position(4,0), null, new Position(0,0)},
                new Position[]{new Position(0,4), null, new Position(4,0)},
                new Position[]{new Position(0,4), new Position(4,0)},
                new Position[]{new Position(0,0), null, new Position(2,2)},
                new Position[]{new Position(0,4), null, new Position(2,2)},
                new Position[]{new Position(2,2), new Position(4,0)}
        };
        for (int i = 2; i < THREE_RABBIT_CHALLENGES.length; i += 4) {
            Jumpin jumpin = new Jumpin(THREE_RABBIT_CHALLENGES[i]);
            testIsSolution(jumpin, rabbitPositions[i/4], false);
        }
        rabbitPositions = new Position[][]{
                new Position[]{new Position(4,4), new Position(0,4)},
                new Position[]{new Position(2,2), new Position(4,4), new Position(4,0)},
                new Position[]{new Position(2,2), new Position(0,4), new Position(0,0)},
                new Position[]{new Position(4,4), new Position(4,0), new Position(0,0)},
                new Position[]{new Position(2,2), new Position(0,0), new Position(4,4)}
        };
        for (int i = 3; i < THREE_RABBIT_CHALLENGES.length; i += 4) {
            Jumpin jumpin = new Jumpin(THREE_RABBIT_CHALLENGES[i]);
            testIsSolution(jumpin, rabbitPositions[i/4], true);
        }
    }

}
