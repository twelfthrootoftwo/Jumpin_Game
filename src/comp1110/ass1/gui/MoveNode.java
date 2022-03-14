package comp1110.ass1.gui;

import comp1110.ass1.*;

import java.util.Objects;


public class MoveNode {

    //current board state
    private Jumpin currentBoard;

    //last move
    private MoveRecord lastMove;

    //previous node
    private MoveNode prevNode;

    //booleans to record start & finish states
    private Boolean startNode;
    private Boolean endNode;

    //Tracker to know which move options have been explored
    public MoveTrialTracker tracker;

    public MoveNode(Jumpin currentBoard) {
        this.currentBoard=currentBoard;
        this.startNode=true;
        this.tracker=new MoveTrialTracker();

        this.endNode=this.currentBoard.isSolution();
    }

    public MoveNode(Jumpin currentBoard, MoveRecord lastMove, MoveNode prevNode) {
        this.currentBoard=currentBoard;
        this.lastMove=lastMove;
        this.prevNode=prevNode;
        this.startNode=false;
        this.tracker=new MoveTrialTracker();

        this.endNode=this.currentBoard.isSolution();
    }

    public Boolean getStartNode() {return this.startNode;}
    public Boolean getEndNode() {return this.endNode;}
    public MoveNode getPrevNode() {return this.prevNode;}
    public MoveRecord getLastMove() {return this.lastMove;}

    //handler functions for tracking move indexes
    public int getPieceCount() {return this.tracker.getPieceCount();}
    public int getMoveCount() {return this.tracker.getMoveCount();}
    public boolean getGoBack() {return this.tracker.getGoBack();}
    public void incrementMove() {this.tracker.incrementMove();}
    public void incrementPiece() {this.tracker.incrementPiece();}
    public void checkedAll() {this.tracker.checkedAll();}

    //handler functions for getting last move info
    public MovingPiece getLastPiece() {return this.lastMove.getPiece();}
    public Direction getLastDir() {return this.lastMove.getDir();}
    public Direction getLastReverse() {return this.lastMove.getReverse();}



    public MoveNode newMove(MovingPiece piece, Direction dir, Jumpin board) {
        System.out.println("Executing move: "+piece.actionToString(dir));
        piece.makeMove(dir, board);
        MoveRecord record= new MoveRecord(piece,dir);

        MoveNode newNode = new MoveNode(board,record,this);
        return newNode;
    }

    /**
     * This class records a move, so that the move tree has a record of what move went previously
     * The intention is to avoid creating a copy of the board for every node
     */
    public class MoveRecord {
        //piece that's moving & direction of movement
        private MovingPiece piece;
        private Direction dir;

        public MoveRecord(MovingPiece piece, Direction dir) {
            this.piece=piece;
            this.dir=dir;
        }

        //return piece that moved
        public MovingPiece getPiece(){
            return this.piece;
        }

        //return direction of movement
        public Direction getDir() {
            return this.dir;
        }

        //return the opposite direction of the move (to allow moving backwards)
        public Direction getReverse() {
            return this.dir.getOpposite();
        }

        //return a string representing the move
        @Override
        public String toString() {
            String output=piece.actionToString(this.dir);
            return output;
        }
    }

    /**
     * Records which options have previously been explored
     * This is intended to be an array index for the output of Jumpin.availablemoves, but stored in an object so that it can be used in a recursion
     */
    private class MoveTrialTracker {
        private int pieceCount;
        private int moveCount;
        private boolean goBack;

        public MoveTrialTracker() {
            this.pieceCount=0;
            this.moveCount=0;
            this.goBack=false;
        }

        public int getMoveCount() {
            return moveCount;
        }

        public int getPieceCount() {
            return pieceCount;
        }

        public boolean getGoBack() {
            return goBack;
        }

        public void incrementMove() {
            this.moveCount++;
        }

        //Similar to a double For loop, when moving on to a new piece, we want to start at the first available move
        //so reset moveCount to 0
        public void incrementPiece() {
            this.pieceCount++;
            this.moveCount=0;
        }

        public void checkedAll() {
            this.goBack=true;
        }
    }

    /**
     * Runs up the list of recorded moves, converts them to string representations,and then returns a string of the entire mvoe sequence start to finish
     * @param output - should be an empty string when first called, but will be filled over the recursive calls
     * @return a string recording all moves taken to get to this moveNode
     */
    public String moveList(String output) {
        //add record of most recent moveNode to start of output string
        //because the move nodes are parsed from end state -> beginning state, each move is added to the start of the string

        System.out.println("Create move list in string form");
        if(this.startNode) {
            return output;
        }
        output=this.lastMove.toString()+output;

        //repeat if there was an earlier move
        if(!Objects.isNull(this.getPrevNode())) {
            output=this.getPrevNode().moveList(output);
        }
        return output;
    }
}
