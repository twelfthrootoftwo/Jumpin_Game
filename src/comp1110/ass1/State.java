package comp1110.ass1;

public enum State {
    BROWN_RABBIT, CREAM_RABBIT, GRAY_RABBIT, FOX, MUSHROOM, EMPTY;

    /**
     * Given a character, returns a corresponding state. In more detail:
     *   - 'B' corresponds to BROWN_RABBIT;
     *   - 'C' corresponds to CREAM_RABBIT;
     *   - 'G' corresponds to GRAY_RABBIT;
     *   - 'F' corresponds to FOX;
     *   - 'M' corresponds to MUSHROOM;
     *   - 'X' corresponds to EMPTY;
     *
     * @param c a given character
     *
     * @return the state corresponding to the given character, or null if there
     *         is no corresponding state
     */
    public static State fromChar(char c) {
        return switch (c) {
            case 'B' -> BROWN_RABBIT;
            case 'C' -> CREAM_RABBIT;
            case 'G' -> GRAY_RABBIT;
            case 'F' -> FOX;
            case 'M' -> MUSHROOM;
            case 'X' -> EMPTY;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case BROWN_RABBIT -> "B";
            case CREAM_RABBIT -> "C";
            case GRAY_RABBIT -> "G";
            case FOX -> "F";
            case MUSHROOM -> "M";
            case EMPTY -> "X";
        };
    }
}
