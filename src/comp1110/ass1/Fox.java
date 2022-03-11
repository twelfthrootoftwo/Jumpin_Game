package comp1110.ass1;

import static java.lang.Boolean.logicalXor;

public class Fox implements MovingPiece {

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
     * Sets this fox's head position to be the given position.
     *
     * @param p a position
     */
    public void setHeadPosition(Position p) {
        this.headPosition = p;
    }

    /**
     * Sets this fox's tail position to be the given position.
     *
     * @param p a position
     */
    public void setTailPosition(Position p) {
        this.tailPosition = p;
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
     * Checks fox piece movement and decides whether move is possible
     *
     * @param dir - the direction of intended movement
     * @param board - the current board state
     * @return a moveResults contianing a boolean for whether the move was possible and the new position of the fox's head
     */
    public MoveResults moveForecast(Direction dir, Jumpin board) {
        Boolean validMove=true;
        Position newHead = new Position(this.getHeadPosition().getX(),this.getHeadPosition().getY());
        Position newTail = new Position(this.getTailPosition().getX(),this.getTailPosition().getY());

        //First check based on direction of fox
        Direction facing=this.getDirection();
        if (logicalXor(facing.isVertical(),dir.isVertical())) {
            //move is in the wrong axis
            validMove=false;
            System.out.println("Facing incorrect");
        }

        //skip other checks if the above failed
        if(validMove) {
            //Now get coords of new position occupied
            Position newPos = new Position();


            newHead=newHead.applyDirection(dir);
            newTail=newTail.applyDirection(dir);

            //set newPos equal to the new position not currently occupied by the fox (head or tail)
            if(newHead.equals(this.getTailPosition())) {
                newPos=newTail;
            } else if(newTail.equals(this.getHeadPosition())) {
                newPos=newHead;
            }

            //check outside board
            if(!newPos.isOnBoard()) {
                validMove=false;
                System.out.println("Move off board");
            } else if(!board.isCellEmpty(newPos)){
                //check if already occupied
                validMove=false;
                System.out.println("New position occupied - pos "+newPos);
                board.printBoardState();
            }

        }
        MoveResults moveResult=new MoveResults(validMove, newHead);
        return moveResult;
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
        //the actual logic has been moved into moveForecast method above to allow implementing the MovingPiece method
        MoveResults move=moveForecast(dir,board);
        return move.getValid();

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
        Boolean validMove=this.canMove(dir,board);

        if(validMove) {
            //clear current positions
            board.setCell(this.getHeadPosition(),State.fromChar('X'));
            board.setCell(this.getTailPosition(),State.fromChar('X'));

            //apply new positions
            this.setNewLoc(this.getHeadPosition().applyDirection(dir));
            board.setCell(this.getHeadPosition(),State.fromChar('F'));
            board.setCell(this.getTailPosition(),State.fromChar('F'));
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
     * @param dir the direction in which the fox is moving
     *
     * @return the String encoding of the action in the given direction
     */
    public String actionToString(Direction dir) {
        char unit="F".charAt(0);
        char direct=this.getDirection().getLetter().charAt(0);
        char move=dir.getLetter().charAt(0);
        char[] chars={unit, direct, move};
        String output=new String(chars);


        // FIXME: Task 7
        return output;
    }

    @Override
    public String toString() {
        return "F" + this.direction.getLetter() + this.headPosition.toString();
    }
}
