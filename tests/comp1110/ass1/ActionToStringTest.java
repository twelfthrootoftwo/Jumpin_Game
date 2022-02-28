package comp1110.ass1;

import org.junit.jupiter.api.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(value = 1000, unit = MILLISECONDS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ActionToStringTest {

    private static final String[] DIR_STRINGS = new String[]{"N", "E", "S", "W"};

    private static final Position[] BROWN_RABBIT_POSITIONS
            = new Position[]{new Position(3,2), new Position(3,0),
                             new Position(1,1), new Position(1,1)};

    private static final Position[] CREAM_RABBIT_POSITIONS
            = new Position[]{new Position(3,4), new Position(0,2),
                             new Position(0,4), new Position(0,1)};

    private static final Position[] GRAY_RABBIT_POSITIONS
            = new Position[]{new Position(4,1), new Position(2,2),
                             new Position(3,3), new Position(4,3)};

    private static final Position[] NORTH_FOX_POSITIONS
            = new Position[]{new Position(4,2), new Position(1,0),
                             new Position(2,3), new Position(3,1)};

    private static final Position[] SOUTH_FOX_POSITIONS
            = new Position[]{new Position(2,1), new Position(0,2),
                             new Position(3,4), new Position(4,3)};

    private static final Position[] EAST_FOX_POSITIONS
            = new Position[]{new Position(2,1), new Position(3,4),
                             new Position(1,3), new Position(4,2)};

    private static final Position[] WEST_FOX_POSITIONS
            = new Position[]{new Position(1,3), new Position(0,4),
                             new Position(2,2), new Position(3,0)};

    private void test(String expected, String out, String animal, Direction direction) {
        assertEquals(expected, out, "Expected " + animal + " moving in direction " +
                direction + " to return string " + expected + ", but instead got " + out + ".");
    }

    private void testRabbit(String expected, Rabbit rabbit, Direction direction) {
        String out = rabbit.actionToString(direction);
        String colour;
        if (rabbit.getType() == State.BROWN_RABBIT)      colour = "brown";
        else if (rabbit.getType() == State.CREAM_RABBIT) colour = "cream";
        else                                             colour = "gray";
        test(expected, out, colour + " rabbit", direction);
    }

    private void testFox(String expected, Fox fox, Direction direction) {
        String out = fox.actionToString(direction);
        String foxType = fox.getDirection().toString().toLowerCase() + "-facing fox";
        test(expected, out, foxType, direction);
    }

    @Test
    public void testRabbitActions() {
        Rabbit brownRabbit = new Rabbit("00", State.BROWN_RABBIT);
        Rabbit creamRabbit = new Rabbit("00", State.CREAM_RABBIT);
        Rabbit grayRabbit = new Rabbit("00", State.GRAY_RABBIT);
        for (int i = 0; i < Direction.values().length; i++) {
            brownRabbit.setPosition(BROWN_RABBIT_POSITIONS[i]);
            creamRabbit.setPosition(CREAM_RABBIT_POSITIONS[i]);
            grayRabbit.setPosition(GRAY_RABBIT_POSITIONS[i]);
            testRabbit("B" + DIR_STRINGS[i], brownRabbit, Direction.values()[i]);
            testRabbit("C" + DIR_STRINGS[i], creamRabbit, Direction.values()[i]);
            testRabbit("G" + DIR_STRINGS[i], grayRabbit, Direction.values()[i]);
        }
    }

    @Test
    public void testVerticalFox() {
        Fox northFox = new Fox("N00");
        Fox southFox = new Fox("S00");
        for (int i = 0; i < Direction.values().length; i++) {
            northFox.setNewLoc(NORTH_FOX_POSITIONS[i]);
            southFox.setNewLoc(SOUTH_FOX_POSITIONS[i]);
            testFox("FN" + DIR_STRINGS[i], northFox, Direction.values()[i]);
            testFox("FS" + DIR_STRINGS[i], southFox, Direction.values()[i]);
        }
    }

    @Test
    public void testHorizontalFox() {
        Fox eastFox = new Fox("E00");
        Fox westFox = new Fox("W00");
        for (int i = 0; i < Direction.values().length; i++) {
            eastFox.setNewLoc(EAST_FOX_POSITIONS[i]);
            westFox.setNewLoc(WEST_FOX_POSITIONS[i]);
            testFox("FE" + DIR_STRINGS[i], eastFox, Direction.values()[i]);
            testFox("FW" + DIR_STRINGS[i], westFox, Direction.values()[i]);
        }
    }

}
