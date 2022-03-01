package comp1110.ass1;

public class Position {

    /** The position's x-coordinate. */
    private int x;

    /** The position's y-coordinate. */
    private int y;

    /** A default "out-of-bounds" coordinate. */
    static final int OUT = -1;

    /**
     * Create a new Position that is not on the board.
     */
    public Position() {
        this.x = OUT;
        this.y = OUT;
    }

    /**
     * Create a new location, given an x- and y-coordinate.
     *
     * Note that we can access the fields of the object under construction
     * using the `this` keyword.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Position(int x, int y) {
        this.x=x;
        this.y=y;
        // FIXME: Task 1
    }

    /**
     * Given a two-character string representing a pair of (x, y) coordinates on
     * the board, construct the corresponding Position. Recall that the two
     * characters represent the x and y coordinates of the position,
     * respectively, so the string "03" corresponds to the position (0, 3).
     *
     * @param coordinates a String representing a pair of (x, y) coordinates on
     *                    the board.
     */
    public Position(String coordinates) {
        this.x=Integer.parseInt(Character.toString(coordinates.charAt(0)));
        this.y=Integer.parseInt(Character.toString(coordinates.charAt(1)));
        // FIXME: Task 1
    }

    /**
     * @return The x coordinate of this Position.
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return The y coordinate of this Position.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Set the x coordinate of this Position.
     *
     * @param x The x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set the y coordinate of this Position.
     *
     * @param y The y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Return the storage index corresponding to this instance of Position on
     * the board (see the README.md for mapping specifications).
     *
     * @return The storage index corresponding to this Position
     */
    public int toStorageIndex() {
        return 5 * this.y + this.x;
    }

    /**
     * Returns whether or not this Location is on the Jumpin' board or not.
     * That is, whether or not both coordinates are between 0-4.
     *
     * Remember that you can access the x and y coordinates of this
     * instance of Position using the `this` keyword
     * (eg. check out the getter and setter methods above).
     *
     * @return True if this Position is on the board, False otherwise
     */
    public boolean isOnBoard() {
        return this.x >= 0 && this.x <= 4 && this.y >= 0 && this.y <= 4;
    }

    /**
     * Returns whether this position occupies the same location
     * as a given other position.  For this to be true, the x and y values
     * of this object would have to be the same as the x and y values of
     * the other object.
     *
     * @param other The location to compare to.
     * @return True if this location occupies the same location as the other
     * location, False otherwise
     */
    public boolean equals(Position other) {
        return this.x == other.x && this.y == other.y;
    }

    /**
     * Return the new Position after applying one step in the given
     * Direction. In more detail:
     *   - north corresponds to a movement of -1 along the y-axis;
     *   - south corresponds to a movement of +1 along the y-axis;
     *   - east corresponds to a movement of +1 along the x-axis; and
     *   - west corresponds to a movement of -1 along the x-axis.
     *
     * @param direction the direction to move in
     * @return the new Position after one step
     */
    public Position applyDirection(Direction direction) {
        int xChange=0;
        int yChange=0;
        switch(direction){
            case NORTH -> yChange=-1;
            case SOUTH -> yChange=1;
            case WEST -> xChange=-1;
            case EAST -> xChange=1;
        }

        Position newPos=new Position(this.x+xChange,this.y+yChange);
        //boolean legalMove=newPos.isOnBoard();

        //if (!legalMove){
        //    newPos.setX(newPos.x-xChange);
        //    newPos.setY(newPos.y-yChange);
        //}

        // FIXME: Task 5
        return newPos;
    }

    /**
     * @return The string encoding of this Position.
     */
    @Override
    public String toString() {
        return this.x + "" + this.y;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Position pos)) return false;
        return this.equals(pos);
    }

}
