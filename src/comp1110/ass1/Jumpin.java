package comp1110.ass1;

import java.util.Arrays;
import java.util.Objects;

public class Jumpin {

    /** The colour of each rabbit. */
    public final static State[] RABBIT_TYPES = new State[]{State.CREAM_RABBIT, State.BROWN_RABBIT, State.GRAY_RABBIT};

    /** The rabbits, foxes and mushrooms that make up the given challenge. */
    private final Rabbit[] rabbits = new Rabbit[3];
    private final Fox[] foxes = new Fox[2];
    private final Position[] mushrooms = new Position[3];

    /** The board. Any cell in the board can have one of the values from the
     State enum. */
    private final State[] state = new State[25];

    /** The game's challenge. See the Challenge class for more information. */
    private final Challenge challenge;

    /**
     * @return the rabbits in this game.
     */
    public Rabbit[] getRabbits() {
        return this.rabbits;
    }

    /**
     * @return the foxes in this game.
     */
    public Fox[] getFoxes() {
        return this.foxes;
    }

    /**
     * @return the mushrooms in this game.
     */
    public Position[] getMushrooms() {
        return this.mushrooms;
    }

    /**
     * @return the challenge of this game.
     */
    public Challenge getChallenge() {
        return this.challenge;
    }

    /**
     * @return the board state of this game.
     */
    public State[] getState() {
        return this.state;
    }

    /**
     * Sets the board state of the game.
     *
     * @param state the board state to change to
     */
    public void setState(State[] state) {
        for (int i = 0; i < state.length; i++) {
            this.state[i] = state[i];
        }
    }

    /**
     * Constructor that takes a specific challenge as input.
     *
     * @param challenge the challenge that the game will have
     */
    public Jumpin(Challenge challenge) {
        this.challenge = challenge;
        this.initialiseBoardState();
    }

    /**
     * Constructor that takes a difficulty level as input and creates a random
     * challenge that is of the given difficulty level.
     *
     * @param difficulty the difficulty level of this game's challenge
     */
    public Jumpin(int difficulty) {
        this(Challenge.newChallenge(difficulty));
    }

    /**
     * Initialises this game's board state according to its challenge.
     */
    public void initialiseBoardState() {
        String initialState = this.challenge.getInitialState();
        for (int i = 0; i < getState().length; i++) {
            this.state[i] = State.EMPTY;
        }
        initialiseRabbits(initialState);
        initialiseMushrooms(initialState);
        initialiseFoxes(initialState);
    }

    /**
     * Initialises this game's rabbits according to the game's challenge.
     *
     * @param initialState the initial state provided by this game's challenge.
     */
    private void initialiseRabbits(String initialState) {
        int rabbitIndex = 0;
        for (State type : RABBIT_TYPES) {
            String rabbitString = getRelevantSubstring(initialState, type);
            if (!rabbitString.isEmpty()) {
                Rabbit rabbit = new Rabbit(rabbitString, type);
                this.setCell(rabbit.getPosition(), rabbit.getType());
                this.rabbits[rabbitIndex] = rabbit;
                rabbitIndex++;
            }
        }
    }

    /**
     * Initialises this game's mushrooms according to the game's challenge.
     *
     * @param initialState the initial state provided by this game's challenge.
     */
    private void initialiseMushrooms(String initialState) {
        String mushroomString = getRelevantSubstring(initialState, State.MUSHROOM);
        if (!mushroomString.isEmpty()) {
            for (int i = 0; i < mushroomString.length(); i += 2) {
                String mushroomData = mushroomString.substring(i, i + 2);
                Position mushroom = new Position(mushroomData);
                this.mushrooms[i / 2] = mushroom;
                this.setCell(mushroom, State.MUSHROOM);
            }
        }
    }

    /**
     * Initialises this game's foxes according to the game's challenge.
     *
     * @param initialState the initial state provided by this game's challenge.
     */
    private void initialiseFoxes(String initialState) {
        String foxString = getRelevantSubstring(initialState, State.FOX);
        if (!foxString.isEmpty()) {
            for (int i = 0; i < foxString.length(); i += 3) {
                Fox fox = new Fox(foxString.substring(i, i + 3));
                this.foxes[i / 3] = fox;
                this.setCell(fox.getHeadPosition(), State.FOX);
                this.setCell(fox.getTailPosition(), State.FOX);
            }
        }
    }

    /**
     * Helper method that extracts game state substrings corresponding to a
     * certain element of the board (eg. mushrooms, foxes, the gray rabbit
     * etc.).
     *
     * @param boardState the state of the board, in String form
     * @param type       the letter corresponding to the relevant element of
     *                   the board
     *
     * @return the substring representing the state of the relevant element of
     *         the board
     */
    public static String getRelevantSubstring(String boardState, State type) {
        String order = "CBGMF";
        int index = boardState.indexOf(type.toString());
        if (index == -1) return "";
        int end = -1;
        for (int i = order.indexOf(type.toString()) + 1; i < order.length(); i++) {
            end = boardState.indexOf(order.charAt(i));
            if (end != -1) break;
        }
        if (end == -1) end = boardState.length();
        return boardState.substring(index + 1, end);
    }

    /**
     * Returns this game's board state in String form.
     *
     * @param removeRabbitSymmetry whether to display rabbit colours ("C", "B",
     *                             "G") or replace them with "R" (note: this is
     *                             especially helpful for a solver)
     *
     * @return this game's board state, in String form.
     */
    public String stateToString(boolean removeRabbitSymmetry) {
        return rabbitsToString(removeRabbitSymmetry) + mushroomsToString() + foxesToString();
    }

    /**
     * Returns this game's rabbits in String form.
     *
     * @param removeRabbitSymmetry whether to display rabbit colours ("C", "B",
     *                             "G") or replace them with "R" (note: this is
     *                             especially helpful for a solver)
     *
     * @return this game's rabbits, in String form.
     */
    private String rabbitsToString(boolean removeRabbitSymmetry) {
        String res = "";
        for (Rabbit rabbit : this.rabbits) {
            if (rabbit != null) {
                if (removeRabbitSymmetry)
                    res += "R" + rabbit.toString().substring(1);
                else
                    res += rabbit.toString();
            }
        }
        return res;
    }

    /**
     * @return this game's mushrooms, in String form.
     */
    private String mushroomsToString() {
        String res = "";
        if (this.challenge.getInitialState().contains("M")) {
            res += "M";
            for (Position mushroom : this.mushrooms) {
                if (mushroom != null) res += mushroom.toString();
            }
        }
        return res;
    }

    /**
     * @return this game's foxes, in String form.
     */
    private String foxesToString() {
        String res = "";
        if (this.challenge.getInitialState().contains("F")) {
            res += "F";
            for (Fox fox : this.foxes) {
                if (fox != null) res += fox.toString().substring(1);
            }
        }
        return res;
    }

    /**
     * Prints the board state of the game. This method may be useful for
     * debugging purposes.
     */
    public void printBoardState() {
        for (int i = 0; i < this.state.length; i++) {
            System.out.print(this.state[i] + " ");
            if (i % 5 == 4) {
                System.out.println();
            }
        }
    }

    /**
     * @param position the position of the cell whose state will be retrieved
     *
     * @return the state of the cell in the given position
     */
    public State getCell(Position position) {
        return this.state[position.toStorageIndex()];
    }

    /**
     * Sets the state of a given position on the board.
     *
     * @param position the position whose state will be changed
     * @param cell     the state to change the given position to
     */
    public void setCell(Position position, State cell) {
        if (position.isOnBoard())
            this.state[position.toStorageIndex()] = cell;
    }

    /**
     * Checks whether the cell at the given position is empty or not. That is,
     * whether the board State for that cell is EMPTY.
     *
     * Hint: some of the existing methods in this class may help with getting
     * the State of a cell.
     *
     * @param position the position of the cell that is being checked for
     *                 emptiness
     *
     * @return whether the corresponding cell is empty or not
     */
    public boolean isCellEmpty(Position position) {
        State testCellState=getCell(position);
        String testVal=testCellState.toString();
        if(testVal.equals("X")) return true;
        else return false;
        // FIXME: Task 2

    }

    /**
     * Checks whether this game's current state is a final state. For rules on
     * solving challenges, please consult the README doc at the root level of
     * this repository.
     *
     * @return whether the current state is a final state
     */
    public boolean isSolution() {
        Rabbit[] rabbits=getRabbits();

        //find the number of rabbits that have been created
        //trying to access a method of a nonexistent rabbit will return an exception, so count the number of rabbits that don't trigger exceptions
        int rabbitNum=0;
        for(int i=0;i<rabbits.length;i++) {
            try {
                //System.out.println("trying");
                Boolean test=rabbits[i].isInRabbitHole();
                rabbitNum++;
            } catch (Exception e) {
                //System.out.println("Exception caught");
                break;
            }
        }
        //System.out.println(("number of rabbits: "+rabbitNum));

        Boolean boardSolved=true;


        //check each rabbit (that exists) one by one; if any are not in a hole, change boardSolved to false
        //this won't run for no rabbits, so an extra catch is added below
        for(int i=0;i<rabbitNum;i++) {
            if (rabbits[i].isInRabbitHole()) {
                continue;
            } else {
                boardSolved = false;
                break;
            }
        }

        //make solved state False for no rabbits
        if(rabbitNum==0) {
            boardSolved=false;
        }

        // FIXME: Task 6
        return boardSolved;
    }

    /**
     * Adds a new position to the end of a position array
     *
     * @param posArray - old array of positions to expand
     * @param newPos - new position to be added
     * @return the original array with an additional element at the end
     */
    public Position[] addPosToArray(Position[] posArray, Position newPos) {
        Position[] newArray=new Position[posArray.length+1];
        for(int i=0;i<posArray.length;i++) {
            newArray[i]=posArray[i];
        }
        newArray[posArray.length]=newPos;

        return newArray;
    }

    /**
     * Checks for possible movement options of pieces in an input arrya
     * All members of the input array must implement the MovingPiece interface, and this function uses the canMove method of the interface
     * This allows it to be independent of the movement rules of the pieces to check
     *
     * @param animals an array of rabbit or fox objects (pieces that implement the MovingPiece interface)
     * @return a 2d array containing the possible positions of all input pieces in the animals array
     */
    public Position[][] availableMoves(MovingPiece[] animals) {
        Position[][] possibleMoves=new Position[animals.length][];
        Direction[] directions=new Direction[4];


        //loop over rabbits
        for(int animalCounter=0;animalCounter<animals.length;animalCounter++) {
            try {//to check it's not a null piece
                int moveCounter=0;
                Position[] thisPieceMoves=new Position[0];

                if (animals[animalCounter] instanceof Rabbit) {
                    directions=Direction.values();
                } else if (animals[animalCounter] instanceof Fox) {
                    Fox f= (Fox) animals[animalCounter];
                    directions=new Direction[]{f.getDirection(), f.getDirection().getOpposite()};
                } else if(Objects.isNull(animals[animalCounter])) {
                    //not this many pieces
                    continue;
                } else {
                    System.out.println("No move checking logic exists for this piece type");
                    return new Position[0][0];
                }


                //iterate over directions
                for(int dirCounter=0;dirCounter<directions.length;dirCounter++) {

                    //use MoveResults and canMove as defined in the Rabbit class file
                    //MoveResults is a class that records whether a move was possible and what the outcome position is
                    MovingPiece.MoveResults canMove=new MovingPiece.MoveResults();
                    canMove=animals[animalCounter].moveForecast(directions[dirCounter],this);

                    //if valid move in this direction, add to the outcomes - otherwise add nothing
                    if(canMove.getValid()) {
                        thisPieceMoves=addPosToArray(thisPieceMoves,canMove.getPos());//defined above, this is just an array expansion function
                        moveCounter++;
                    }
                }
                //add to main array
                possibleMoves[animalCounter]=thisPieceMoves;


            } catch(NullPointerException e) {
                //not this many pieces - leave possibleMoves blank and don't check any further rabbits
                break;
            }
        }

        return possibleMoves;
    }


    /**
     * Returns all valid positions that each rabbit can jump to in this game
     * state.
     *
     * This is returned as an array of arrays of Positions. The i-th
     * Position array denotes the valid Positions that the i-th rabbit in `this.rabbits`
     * can jump to. Therefore, the resulting Position[][] should always be of
     * length 3. If an element of `this.rabbits` is `null` (as in, the rabbit
     * doesn't exist in this game), then the corresponding Position[] inside
     * the resulting Position[][] should also be `null`.
     *
     * If the i-th rabbit in `this.rabbits` cannot move anywhere in
     * the given state, then the i-th Position array in the output of this
     * method should be empty. This can be achieved with the code
     * `new Position[0]`.
     *
     * Otherwise, the i-th Position array in the output of this method should
     * return all Positions that the i-th rabbit in `this.rabbits` can jump to.
     * These Positions should be ordered according to the Direction in which
     * they are located relative to the rabbit. The order of Directions (and
     * therefore Positions) is NORTH, EAST, SOUTH, WEST. This is the same order
     * as defined in the `Direction` enum. You can obtain all four Directions
     * in this order as a Direction[] using the method `Direction.values()`.
     * There should be no `null` elements or off-board Positions in any Position array.
     * If a rabbit cannot move in a certain Direction, you can ignore the
     * result of moving in that Direction and move onto the next Direction.
     *
     * @return the valid Positions that each rabbit in this game can jump to,
     *         structured according to the definition above.
     */
    public Position[][] getValidNextRabbitPositions() {
        // FIXME: Task 11
        Rabbit[] rabbits=this.getRabbits();

        //availableMoves is defined above
        Position[][] possibleMoves=availableMoves(rabbits);

        return possibleMoves;
    }

    /**
     * Returns all valid positions that each fox can move to in this game
     * state.
     *
     * This is returned as an array of arrays of Positions. The i-th
     * Position array denotes the valid Positions that the i-th fox in `this.foxes`
     * can move to. Therefore, the resulting Position[][] should always be of
     * length 2. If an element of `this.foxes` is `null` (as in, the fox
     * doesn't exist in this game), then the corresponding Position[] inside
     * the resulting Position[][] should also be `null`.
     *
     * If the i-th fox in `this.foxes` cannot move anywhere in
     * the given state, then the i-th Position array in the output of this
     * method should be empty. This can be achieved with the code
     * `new Position[0]`.
     *
     * Otherwise, the i-th Position array in the output of this method should
     * return all Positions that the i-th fox in `this.foxes` can move to.
     * These Positions should be ordered according to the Direction in which
     * the fox faces. If the fox can move both forwards and backwards, then the
     * fox's Position after having moved forwards should be placed before the
     * fox's Position after having moved backwards. If the fox can only move in
     * one direction, the order of the resulting singleton array is not important.
     * There should be no `null` elements or off-board Positions in any Position array.
     * If a fox cannot move in a certain Direction, you can ignore the
     * result of moving in that Direction and move onto the next Direction.
     *
     * @return the valid Positions that each fox in this game can move to,
     *         structured according to the definition above.
     */
    public Position[][] getValidNextFoxPositions() {
        // FIXME: Task 11
        Fox[] foxes=this.getFoxes();
        Position[][] possibleMoves=availableMoves(foxes);

        return possibleMoves;
    }

    /**
     * Simple function to return a copy of the current Jumpin (board state and pieces)
     * This allows us to use the movement logic stored in the piece classes
     * @return a new Jumpin which is a copy of the current one
     */
    public Jumpin duplicate() {
        Jumpin newBoard = new Jumpin(0);
        newBoard.setState(this.state);
        return newBoard;
    }

    public void constructTree() {

    }

    /**
     * Solve the Challenge by finding a shortest solution, i.e. one with
     * the fewest number actions possible.
     *
     * Returns an array of two Strings, the first one being the resulting
     * solution state of the game (where all the rabbits are in rabbit holes)
     * and the second being a sequence of actions used to arrive at this
     * solution state.
     *
     * @return a solution to this game's challenge
     */
    public String[] solve() {
        // FIXME: Task 12
        return new String[]{"", ""};
    }

}
