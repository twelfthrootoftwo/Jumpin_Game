package comp1110.ass1;

import org.junit.jupiter.api.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(value = 1000, unit = MILLISECONDS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class NextRabbitPositionsTest {

    @Test
    public void testOneRabbit() {
        for (int i = 0; i < ONE_RABBIT_CHALLENGES.length; i++) {
            Jumpin jumpin = new Jumpin(ONE_RABBIT_CHALLENGES[i]);
            test(ONE_RABBIT_POSITIONS[i], jumpin);
        }
    }

    @Test
    public void testTwoRabbits() {
        for (int i = 0; i < TWO_RABBIT_CHALLENGES.length; i++) {
            Jumpin jumpin = new Jumpin(TWO_RABBIT_CHALLENGES[i]);
            test(TWO_RABBIT_POSITIONS[i], jumpin);
        }
    }

    @Test
    public void testThreeRabbits() {
        for (int i = 0; i < THREE_RABBIT_CHALLENGES.length; i++) {
            Jumpin jumpin = new Jumpin(THREE_RABBIT_CHALLENGES[i]);
            test(THREE_RABBIT_POSITIONS[i], jumpin);
        }
    }

    private void test(Position[][] expected, Jumpin jumpin) {
        Position[][] out = jumpin.getValidNextRabbitPositions();
        for (int i = 0; i < expected.length; i++) {
            if (expected[i] == null) {
                assertNull(out[i], "For state " + jumpin.stateToString(false) + ":\n" +
                        "Expected valid rabbit positions for {C, B, G} to be " + generatePositionArrayString(expected) +
                        ", but instead got " + generatePositionArrayString(out));
            }
            else {
                assertArrayEquals(expected[i], out[i], "For state " + jumpin.stateToString(false) + ":\n" +
                        "Expected valid rabbit positions for {C, B, G} to be " + generatePositionArrayString(expected) +
                        ", but instead got " + generatePositionArrayString(out));
            }
        }
    }

    private static String generatePositionArrayString(Position[][] positions) {
        String res = "{";
        for (Position[] ps : positions) {
            if (ps != null) {
                res += "{";
                for (Position p : ps) {
                    if (p != null) {
                        res += "(" + p.getX() + "," + p.getY() + "), ";
                    }
                    else {
                        res += "null, ";
                    }
                }
                res = res.substring(0, res.length() - 2) + "}, ";
            }
            else {
                res += "null, ";
            }
        }
        res = res.substring(0, res.length() - 2) + "};\n";
        return res;
    }

    private static final Challenge[] ONE_RABBIT_CHALLENGES = new Challenge[]{
            Challenge.CHALLENGES[0], Challenge.CHALLENGES[12], Challenge.CHALLENGES[13],
            Challenge.CHALLENGES[14], Challenge.CHALLENGES[15], Challenge.CHALLENGES[16],
            Challenge.CHALLENGES[17], Challenge.CHALLENGES[18], Challenge.CHALLENGES[19],
            Challenge.CHALLENGES[20], Challenge.CHALLENGES[21], Challenge.CHALLENGES[22],
            Challenge.CHALLENGES[23], Challenge.CHALLENGES[36]
    };

    private static final Challenge[] TWO_RABBIT_CHALLENGES = new Challenge[]{
            Challenge.CHALLENGES[1], Challenge.CHALLENGES[2], Challenge.CHALLENGES[3],
            Challenge.CHALLENGES[4], Challenge.CHALLENGES[5], Challenge.CHALLENGES[6],
            Challenge.CHALLENGES[24], Challenge.CHALLENGES[25], Challenge.CHALLENGES[26],
            Challenge.CHALLENGES[27], Challenge.CHALLENGES[28], Challenge.CHALLENGES[29],
            Challenge.CHALLENGES[30], Challenge.CHALLENGES[31], Challenge.CHALLENGES[32],
            Challenge.CHALLENGES[33], Challenge.CHALLENGES[34], Challenge.CHALLENGES[35],
            Challenge.CHALLENGES[37], Challenge.CHALLENGES[39], Challenge.CHALLENGES[40],
            Challenge.CHALLENGES[41], Challenge.CHALLENGES[42], Challenge.CHALLENGES[46]
    };

    private static final Challenge[] THREE_RABBIT_CHALLENGES = new Challenge[]{
            Challenge.CHALLENGES[7], Challenge.CHALLENGES[8], Challenge.CHALLENGES[9],
            Challenge.CHALLENGES[10], Challenge.CHALLENGES[11], Challenge.CHALLENGES[38],
            Challenge.CHALLENGES[43], Challenge.CHALLENGES[44], Challenge.CHALLENGES[45],
            Challenge.CHALLENGES[47], Challenge.CHALLENGES[48], Challenge.CHALLENGES[49],
            Challenge.CHALLENGES[50], Challenge.CHALLENGES[51], Challenge.CHALLENGES[52],
            Challenge.CHALLENGES[53], Challenge.CHALLENGES[54], Challenge.CHALLENGES[55],
            Challenge.CHALLENGES[56], Challenge.CHALLENGES[57], Challenge.CHALLENGES[58],
            Challenge.CHALLENGES[59]
    };

    private static final Position[][][] ONE_RABBIT_POSITIONS = new Position[][][]{
            new Position[][]{new Position[]{new Position(3,0)}, null, null},
            new Position[][]{new Position[]{new Position(2,3), new Position(0,1)}, null, null},
            new Position[][]{new Position[]{}, null, null},
            new Position[][]{new Position[]{}, null, null},
            new Position[][]{new Position[]{}, null, null},
            new Position[][]{new Position[]{new Position(0,1)}, null, null},
            new Position[][]{new Position[]{}, null, null},
            new Position[][]{new Position[]{}, null, null},
            new Position[][]{new Position[]{}, null, null},
            new Position[][]{new Position[]{}, null, null},
            new Position[][]{new Position[]{}, null, null},
            new Position[][]{new Position[]{}, null, null},
            new Position[][]{new Position[]{new Position(1,1)}, null, null},
            new Position[][]{new Position[]{}, null, null}
    };

    private static final Position[][][] TWO_RABBIT_POSITIONS = new Position[][][]{
            new Position[][]{new Position[]{new Position(4,2), new Position(1,0)}, new Position[]{}, null},
            new Position[][]{new Position[]{}, new Position[]{new Position(2,2)}, null},
            new Position[][]{new Position[]{}, new Position[]{new Position(0,4)}, null},
            new Position[][]{new Position[]{new Position(0,1)}, new Position[]{}, null},
            new Position[][]{new Position[]{new Position(2,0)}, new Position[]{}, null},
            new Position[][]{new Position[]{new Position(2,4)}, new Position[]{}, null},
            new Position[][]{new Position[]{}, new Position[]{}, null},
            new Position[][]{new Position[]{}, new Position[]{}, null},
            new Position[][]{new Position[]{}, new Position[]{new Position(0,2)}, null},
            new Position[][]{new Position[]{new Position(2,2)}, new Position[]{new Position(1,4)}, null},
            new Position[][]{new Position[]{new Position(3,0)}, new Position[]{}, null},
            new Position[][]{new Position[]{}, new Position[]{}, null},
            new Position[][]{new Position[]{}, new Position[]{new Position(1,2)}, null},
            new Position[][]{new Position[]{}, new Position[]{}, null},
            new Position[][]{new Position[]{}, new Position[]{new Position(2,4)}, null},
            new Position[][]{new Position[]{}, new Position[]{new Position(2,0)}, null},
            new Position[][]{new Position[]{new Position(3,0)}, new Position[]{}, null},
            new Position[][]{new Position[]{}, new Position[]{}, null},
            new Position[][]{new Position[]{}, new Position[]{}, null},
            new Position[][]{new Position[]{}, new Position[]{new Position(1,1)}, null},
            new Position[][]{new Position[]{new Position(2,2)}, new Position[]{new Position(3,4)}, null},
            new Position[][]{new Position[]{}, new Position[]{}, null},
            new Position[][]{new Position[]{new Position(0,1)}, new Position[]{new Position(1,3)}, null},
            new Position[][]{new Position[]{new Position(2,0)}, new Position[]{new Position(1,1)}, null}
    };

    private static final Position[][][] THREE_RABBIT_POSITIONS = new Position[][][]{
            new Position[][]{new Position[]{}, new Position[]{new Position(4,0)}, new Position[]{}},
            new Position[][]{new Position[]{}, new Position[]{new Position(2,0)}, new Position[]{new Position(2,0)}},
            new Position[][]{new Position[]{}, new Position[]{}, new Position[]{new Position(0,2)}},
            new Position[][]{new Position[]{}, new Position[]{}, new Position[]{new Position(2,0)}},
            new Position[][]{new Position[]{}, new Position[]{new Position(1,4)}, new Position[]{new Position(2,2)}},
            new Position[][]{new Position[]{new Position(3,4), new Position(0,2)}, new Position[]{new Position(1,0)}, new Position[]{new Position(0,2)}},
            new Position[][]{new Position[]{new Position(1,0)}, new Position[]{new Position(0,1)}, new Position[]{new Position(1,0)}},
            new Position[][]{new Position[]{}, new Position[]{new Position(4,3)}, new Position[]{}},
            new Position[][]{new Position[]{new Position(0,0), new Position(0,4)}, new Position[]{}, new Position[]{new Position(0,0)}},
            new Position[][]{new Position[]{new Position(2,2)}, new Position[]{}, new Position[]{new Position(1,0)}},
            new Position[][]{new Position[]{}, new Position[]{}, new Position[]{}},
            new Position[][]{new Position[]{new Position(0,0)}, new Position[]{new Position(2,3)}, new Position[]{new Position(4,0), new Position(2,3), new Position(0,0)}},
            new Position[][]{new Position[]{new Position(0,2)}, new Position[]{new Position(4,1)}, new Position[]{}},
            new Position[][]{new Position[]{}, new Position[]{}, new Position[]{}},
            new Position[][]{new Position[]{}, new Position[]{}, new Position[]{new Position(1,4)}},
            new Position[][]{new Position[]{new Position(4,0)}, new Position[]{}, new Position[]{new Position(1,2)}},
            new Position[][]{new Position[]{new Position(2,0), new Position(4,2), new Position(2,4)}, new Position[]{}, new Position[]{new Position(2,0), new Position(4,3)}},
            new Position[][]{new Position[]{}, new Position[]{}, new Position[]{new Position(2,1)}},
            new Position[][]{new Position[]{}, new Position[]{}, new Position[]{}},
            new Position[][]{new Position[]{}, new Position[]{}, new Position[]{}},
            new Position[][]{new Position[]{}, new Position[]{}, new Position[]{}},
            new Position[][]{new Position[]{}, new Position[]{}, new Position[]{}}
    };

}
