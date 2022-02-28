package comp1110.ass1;

import org.junit.jupiter.api.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(value = 1000, unit = MILLISECONDS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class PositionConstructorTest {

    private void testFromCoordinates(int x, int y, Position expected) {
        Position out = new Position(x, y);
        assertEquals(expected.getX(), out.getX(), "Expected x coordinate of " + expected.getX() +
                " from coordinates (" + x + "," + y + ")" +
                "', but got " + out.getX() + ".");
        assertEquals(expected.getY(), out.getY(), "Expected y coordinate of " + expected.getY() +
                " from coordinates (" + x + "," + y + ")" +
                "', but got " + out.getY() + ".");
    }

    private void testFromString(String in, Position expected) {
        Position out = new Position(in);
        assertEquals(expected.getX(), out.getX(), "Expected x coordinate of " + expected.getX() +
                " from string '" + in +
                "', but got " + out.getX() + ".");
        assertEquals(expected.getY(), out.getY(), "Expected y coordinate of " + expected.getY() +
                " from string '" + in +
                "', but got " + out.getY() + ".");

    }

    @Test
    public void testCoordinates() {
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                Position expected = new Position();
                expected.setX(x);
                expected.setY(y);
                testFromCoordinates(x, y, expected);
            }
        }
    }

    @Test
    public void testString() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Position expected = new Position();
                expected.setX(x);
                expected.setY(y);
                testFromString(x + "" + y, expected);
            }
        }
    }

}
