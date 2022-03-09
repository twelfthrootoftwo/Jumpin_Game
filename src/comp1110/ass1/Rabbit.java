package comp1110.ass1;

public class Rabbit {

    /** The position of the rabbit. */
    private Position position;

    /** The colour of this rabbit. */
    private final State type;

    /**
     * Constructor for a rabbit. It takes a two-letter String, which is made up
     * only of digits between 0 and 4 inclusive, corresponding to this rabbit's
     * position on the board. It also takes a character corresponding to this
     * rabbit's colour. This character must be either 'B' (for brown), 'C' (for
     * cream) or 'G' (for gray).
     *
     * @param position the position this rabbit occupies, represented as a
     *                 String
     * @param type     the colour of this rabbit
     */
    public Rabbit(String position, State type) {
        assert position.length() == 2;
        assert position.charAt(0) < '5' && position.charAt(0) >= '0';
        assert position.charAt(1) < '5' && position.charAt(1) >= '0';
        assert type == State.BROWN_RABBIT || type == State.CREAM_RABBIT || type == State.GRAY_RABBIT;
        this.type = type;
        this.position = new Position(position);
    }

    /**
     * @return the position occupied by this rabbit
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * @return the colour of this rabbit
     */
    public State getType() {
        return this.type;
    }

    /**
     * Sets the position of this rabbit according to the given position.
     *
     * @param position this rabbit's new position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @return whether the rabbit is in one of the rabbit holes located at
     *         (0,0), (4,0), (2,2), (0,4) and (4,4).
     */
    public boolean isInRabbitHole() {
        boolean correctX = this.position.getX() == 0 || this.position.getX() == 4;
        boolean correctY = this.position.getY() == 0 || this.position.getY() == 4;
        boolean middleOfBoard = this.position.getX() == 2 && this.position.getY() == 2;
        return (correctX && correctY) || middleOfBoard;
    }

    /**
     * Moves this rabbit to the closest cell to it in a given direction, if
     * this is possible. It must follow the rules of the game where it can't jump
     * into a directly adjacent cell or one occupied by another object.
     *
     * This method should also return a boolean indicating whether the move
     * was possible.
     *
     * @param dir   the direction in which to move this rabbit
     * @param board the board which this rabbit occupies
     *
     * @return whether the move was possible
     */
    public boolean makeMove(Direction dir, Jumpin board) {
        // FIXME: Task 10
        //set up variable for new position
        Position newPos=new Position(this.getPosition().getX(),this.getPosition().getY());
        Boolean validMove=true;
        System.out.println("Starting pos: "+newPos);
        System.out.println("Direction: "+dir);


        newPos=newPos.applyDirection(dir);
        //Check if on board first
        if(newPos.isOnBoard()) {
            //Then check proposed move has something to jump over
            if (board.isCellEmpty(newPos)) {
                validMove = false;
                System.out.println("Next cell in this dir is empty");
            } else {
                //something to jump - go along until fwe find an empty cell
                while (newPos.isOnBoard() && (!board.isCellEmpty(newPos))) {
                    newPos = newPos.applyDirection(dir);
                }
                //check if move target is on board
                if (newPos.isOnBoard()) {
                    System.out.println("Valid move, hopping to new position " + newPos);
                } else {
                    System.out.println("No available spaces between current pos and edge of board");
                    validMove = false;
                }
            }
        } else {
            //not on board
            System.out.println("Moving off the edge of board");
            validMove=false;
        }

        //if move is valid, move rabbit & update board
        if(validMove) {
            board.setCell(this.getPosition(),State.fromChar('X'));
            this.setPosition(newPos);
            board.setCell(this.getPosition(),this.getType());
        }


        return validMove;
    }

    /**
     * Returns the String encoding of an action in the given direction. For
     * details on how actions are encoded, please consult the README doc
     * located at the root level of this repository.
     *
     * Note that this method does not check whether the action is valid or not.
     * It only converts the action to a String.
     *
     * @param dir the direction in which the rabbit is moving
     *
     * @return the String encoding of the action in the given direction
     */
    public String actionToString(Direction dir) {
        // FIXME: Task 7
        char unit=this.getType().toString().charAt(0);
        char move=dir.getLetter().charAt(0);
        char[] chars={unit, move};
        String output=new String(chars);

        return output;
    }

    @Override
    public String toString() {
        return this.type.toString() + this.position.toString();
    }
}
