package comp1110.ass1;

public class Fox {

    /** The position of the fox's head. */
    private Position headPosition;

    /** The position of the fox's tail. */
    private Position tailPosition;

    /** The direction in which the fox faces. */
    private final Direction direction;

    /**
     * Constructor for a fox. It takes a string made up of the following
     * components (in order):
     *   - One character from {'N', 'E', 'S', 'W'} that denotes the direction
     *     in which the fox faces; and
     *   - Two digits between 0 and 4 inclusive that denote the position of the
     *     fox's head.
     *
     * Note that the position of the fox's tail can also be calculated from
     * this information.
     *
     * @param directionAndPos a three-character string denoting the direction
     *                        of the fox and the position of its head
     */
    public Fox(String directionAndPos) {
        assert directionAndPos.length() == 3;
        this.direction = Direction.fromChar(directionAndPos.charAt(0));
        this.headPosition = new Position(directionAndPos.substring(1, 3));
        this.tailPosition = this.headPosition.applyDirection(this.direction.getOpposite());
    }

    /**
     * @return the position of the fox's head.
     */
    public Position getHeadPosition() {
        return this.headPosition;
    }

    /**
     * @return the position of the fox's tail.
     */
    public Position getTailPosition() {
        return this.tailPosition;
    }

    /**
     * @return the direction in which the fox faces.
     */
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * Sets the position of the fox's head and tail.
     *
     * @param headPosition the position to move the fox's head to. Note that
     *                     the position of the fox's tail can be deduced from
     *                     this information.
     */
    public void setNewLoc(Position headPosition) {
        this.headPosition = headPosition;
        this.tailPosition = headPosition.applyDirection(this.direction.getOpposite());
    }

    /**
     * Checks whether the fox can make a valid move in a given direction on
     * a given board, accounting for other objects as well as the edge of the
     * board.
     *
     * @param dir   the given direction in which to check for movement
     * @param board the board on which the fox is situated
     *
     * @return whether the fox can move in the given direction on the given
     *         board
     */
    public boolean canMove(Direction dir, Jumpin board) {
        // FIXME: Task 8
        return false;
    }

    /**
     * Moves the fox one cell in the given direction on the given board, if it
     * can be moved in that direction.
     *
     * Note that this method has two specifications. The first specification is
     * that the fox's position is changed to reflect a movement of one cell in
     * the given direction, if that is possible. The second objective is to
     * return true or false reflecting whether the move was possible.
     *
     * @param dir   the direction in which to move the fox
     * @param board the board on which the fox is situated
     *
     * @return whether the move was possible
     */
    public boolean makeMove(Direction dir, Jumpin board) {
        // FIXME: Task 9
        return false;
    }

    /**
     * Returns the String encoding of an action in the given direction. For
     * details on how actions are encoded, please consult the README doc
     * located at the root level of this repository.
     *
     * Note that this method does not check whether the action is valid or not.
     * It only converts the action to a String.
     *
     * @param dir the direction in which the fox is moving
     *
     * @return the String encoding of the action in the given direction
     */
    public String actionToString(Direction dir) {
        // FIXME: Task 7
        return "FNN";
    }

    @Override
    public String toString() {
        return "F" + this.direction.getLetter() + this.headPosition.toString();
    }
}
