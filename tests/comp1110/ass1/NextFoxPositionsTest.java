package comp1110.ass1;

import org.junit.jupiter.api.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(value = 1000, unit = MILLISECONDS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class NextFoxPositionsTest {

    @Test
    public void testNoFoxes() {
        for (Challenge c : NO_FOX_CHALLENGES) {
            test(new Position[2][], new Jumpin(c));
        }
    }

    @Test
    public void testOneFox() {
        for (int i = 0; i < ONE_FOX_CHALLENGES.length; i++) {
            Jumpin jumpin = new Jumpin(ONE_FOX_CHALLENGES[i]);
            test(ONE_FOX_POSITIONS[i], jumpin);
        }
    }

    @Test
    public void testTwoFoxes() {
        for (int i = 0; i < TWO_FOX_CHALLENGES.length; i++) {
            Jumpin jumpin = new Jumpin(TWO_FOX_CHALLENGES[i]);
            test(TWO_FOX_POSITIONS[i], jumpin);
        }
    }

    private void test(Position[][] expected, Jumpin jumpin) {
        Position[][] out = jumpin.getValidNextFoxPositions();
        for (int i = 0; i < expected.length; i++) {
            if (expected[i] == null) {
                assertNull(out[i], "For state " + jumpin.stateToString(false) + ":\n" +
                        "Expected valid fox positions for " + generateFoxString(jumpin) + " to be " +
                        generatePositionArrayString(expected) +
                        ", but instead got " + generatePositionArrayString(out));
            }
            else {
                assertArrayEquals(expected[i], out[i], "For state " + jumpin.stateToString(false) + ":\n" +
                        "Expected valid fox positions for " + generateFoxString(jumpin) + "to be " +
                        generatePositionArrayString(expected) +
                        ", but instead got " + generatePositionArrayString(out));
            }
        }
    }

    private static String generateFoxString(Jumpin jumpin) {
        String res = "{";
        for (Fox fox : jumpin.getFoxes()) {
            if (fox != null) {
                res += fox.getDirection().getLetter() + ", ";
            }
        }
        if (res.charAt(res.length() - 1) != '{')
            res = res.substring(0, res.length() - 2);
        res += "}";
        return res;
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

    private final static Challenge[] NO_FOX_CHALLENGES = new Challenge[]{
            Challenge.CHALLENGES[0], Challenge.CHALLENGES[1], Challenge.CHALLENGES[2],
            Challenge.CHALLENGES[3], Challenge.CHALLENGES[4], Challenge.CHALLENGES[5],
            Challenge.CHALLENGES[6], Challenge.CHALLENGES[7], Challenge.CHALLENGES[8],
            Challenge.CHALLENGES[9], Challenge.CHALLENGES[10], Challenge.CHALLENGES[11],
            Challenge.CHALLENGES[47]
    };
    private final static Challenge[] ONE_FOX_CHALLENGES = new Challenge[]{
            Challenge.CHALLENGES[12], Challenge.CHALLENGES[13], Challenge.CHALLENGES[14],
            Challenge.CHALLENGES[15], Challenge.CHALLENGES[18], Challenge.CHALLENGES[19],
            Challenge.CHALLENGES[20], Challenge.CHALLENGES[25], Challenge.CHALLENGES[27],
            Challenge.CHALLENGES[28], Challenge.CHALLENGES[30], Challenge.CHALLENGES[35],
            Challenge.CHALLENGES[40], Challenge.CHALLENGES[41], Challenge.CHALLENGES[42],
            Challenge.CHALLENGES[45], Challenge.CHALLENGES[46], Challenge.CHALLENGES[49],
            Challenge.CHALLENGES[53], Challenge.CHALLENGES[54], Challenge.CHALLENGES[59],
    };

    private final static Challenge[] TWO_FOX_CHALLENGES = new Challenge[]{
            Challenge.CHALLENGES[16], Challenge.CHALLENGES[17], Challenge.CHALLENGES[21],
            Challenge.CHALLENGES[22], Challenge.CHALLENGES[23], Challenge.CHALLENGES[24],
            Challenge.CHALLENGES[26], Challenge.CHALLENGES[29], Challenge.CHALLENGES[31],
            Challenge.CHALLENGES[32], Challenge.CHALLENGES[33], Challenge.CHALLENGES[34],
            Challenge.CHALLENGES[36], Challenge.CHALLENGES[37], Challenge.CHALLENGES[38],
            Challenge.CHALLENGES[39], Challenge.CHALLENGES[43], Challenge.CHALLENGES[44],
            Challenge.CHALLENGES[48], Challenge.CHALLENGES[50], Challenge.CHALLENGES[51],
            Challenge.CHALLENGES[52], Challenge.CHALLENGES[55], Challenge.CHALLENGES[56],
            Challenge.CHALLENGES[57], Challenge.CHALLENGES[58]
    };

    private final static Position[][][] ONE_FOX_POSITIONS = new Position[][][]{
            new Position[][]{new Position[]{}, null},
            new Position[][]{new Position[]{new Position(3,1), new Position(1,1)}, null},
            new Position[][]{new Position[]{new Position(3,2)}, null},
            new Position[][]{new Position[]{new Position(1,2)}, null},
            new Position[][]{new Position[]{new Position(3,4), new Position(3,2)}, null},
            new Position[][]{new Position[]{new Position(1,2)}, null},
            new Position[][]{new Position[]{new Position(2,1)}, null},
            new Position[][]{new Position[]{new Position(2,1)}, null},
            new Position[][]{new Position[]{new Position(1,3)}, null},
            new Position[][]{new Position[]{new Position(3,1)}, null},
            new Position[][]{new Position[]{}, null},
            new Position[][]{new Position[]{new Position(3,1), new Position(1,1)}, null},
            new Position[][]{new Position[]{new Position(1,2)}, null},
            new Position[][]{new Position[]{new Position(1,3), new Position(1,1)}, null},
            new Position[][]{new Position[]{}, null},
            new Position[][]{new Position[]{new Position(3,1)}, null},
            new Position[][]{new Position[]{new Position(2,1)}, null},
            new Position[][]{new Position[]{new Position(1,3)}, null},
            new Position[][]{new Position[]{new Position(3,2)}, null},
            new Position[][]{new Position[]{new Position(3,4), new Position(3,2)}, null},
            new Position[][]{new Position[]{new Position(2,1)}, null},
    };

    private final static Position[][][] TWO_FOX_POSITIONS = new Position[][][]{
            new Position[][]{new Position[]{new Position(1,1)}, new Position[]{new Position(3,2)}},
            new Position[][]{new Position[]{new Position(1,1), new Position(3,1)}, new Position[]{new Position(2,3)}},
            new Position[][]{new Position[]{new Position(3,1)}, new Position[]{new Position(2,3)}},
            new Position[][]{new Position[]{new Position(2,1)}, new Position[]{new Position(1,2)}},
            new Position[][]{new Position[]{new Position(3,1)}, new Position[]{new Position(2,3)}},
            new Position[][]{new Position[]{new Position(1,2)}, new Position[]{new Position(3,1)}},
            new Position[][]{new Position[]{new Position(2,1)}, new Position[]{new Position(1,4)}},
            new Position[][]{new Position[]{new Position(1,2)}, new Position[]{new Position(2,1)}},
            new Position[][]{new Position[]{new Position(1,2)}, new Position[]{new Position(3,3), new Position(1,3)}},
            new Position[][]{new Position[]{new Position(2,1)}, new Position[]{new Position(1,2)}},
            new Position[][]{new Position[]{new Position(1,2)}, new Position[]{new Position(3,1)}},
            new Position[][]{new Position[]{new Position(2,3)}, new Position[]{}},
            new Position[][]{new Position[]{new Position(1,2)}, new Position[]{new Position(2,3)}},
            new Position[][]{new Position[]{new Position(1,2)}, new Position[]{new Position(3,1)}},
            new Position[][]{new Position[]{new Position(2,1)}, new Position[]{new Position(2,1)}},
            new Position[][]{new Position[]{new Position(2,3)}, new Position[]{new Position(3,2)}},
            new Position[][]{new Position[]{new Position(1,0)}, new Position[]{}},
            new Position[][]{new Position[]{new Position(1,1), new Position(3,1)}, new Position[]{new Position(3,3)}},
            new Position[][]{new Position[]{new Position(2,1)}, new Position[]{new Position(1,4)}},
            new Position[][]{new Position[]{}, new Position[]{new Position(3,3), new Position(3,1)}},
            new Position[][]{new Position[]{new Position(2,1)}, new Position[]{new Position(1,4)}},
            new Position[][]{new Position[]{}, new Position[]{new Position(0,3), new Position(2,3)}},
            new Position[][]{new Position[]{new Position(2,1)}, new Position[]{new Position(3,0), new Position(3,2)}},
            new Position[][]{new Position[]{new Position(2,1)}, new Position[]{new Position(3,3)}},
            new Position[][]{new Position[]{new Position(3,0)}, new Position[]{new Position(2,3)}},
            new Position[][]{new Position[]{new Position(2,1)}, new Position[]{new Position(1,3), new Position(3,3)}},
    };

}
