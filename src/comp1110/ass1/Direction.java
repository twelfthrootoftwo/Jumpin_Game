package comp1110.ass1;

public enum Direction {

    /** The four cardinal directions. */
    NORTH, EAST, SOUTH, WEST;

    /**
     * @return True if this Direction is NORTH or SOUTH, and False if it is
     * EAST or WEST.
     */
    public boolean isVertical() {
        return this == NORTH || this == SOUTH;
    }

    /**
     * Given a character, returns a corresponding direction. In more detail:
     *   - 'N' corresponds to north;
     *   - 'E' corresponds to east;
     *   - 'S' corresponds to south; and
     *   - 'W' corresponds to west.
     *
     * @param c a given character
     *
     * @return the direction corresponding to the given character, or null if
     *         there is no corresponding direction
     */
    public static Direction fromChar(char c) {
        return switch (c) {
            case 'N' -> NORTH;
            case 'E' -> EAST;
            case 'S' -> SOUTH;
            case 'W' -> WEST;
            default -> null;
        };
    }

    /**
     * @return the opposite of this direction.
     */
    public Direction getOpposite() {
        // FIXME: Task 3
        return NORTH;
    }

    /**
     * @return the first letter of this cardinal direction
     */
    public String getLetter() {
        return switch (this) {
            case NORTH -> "N";
            case EAST -> "E";
            case SOUTH -> "S";
            case WEST -> "W";
        };
    }

}