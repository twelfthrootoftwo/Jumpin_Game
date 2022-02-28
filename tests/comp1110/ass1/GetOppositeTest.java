package comp1110.ass1;

import org.junit.jupiter.api.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(value = 1000, unit = MILLISECONDS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class GetOppositeTest {

    private static final Direction[] OPPOSITE_DIRECTIONS
            = new Direction[]{Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST};

    @Test
    public void testOpposite() {
        for (int i = 0; i < 4; i++) {
            Direction d = Direction.values()[i];
            Direction out = d.getOpposite();
            Direction expected = OPPOSITE_DIRECTIONS[i];
            assertEquals(out, expected, "Expected the opposite of " + d + " to be " + expected + "," +
                    " but got " + out);
        }
    }

}
