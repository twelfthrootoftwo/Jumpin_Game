package comp1110.ass1.gui;

import comp1110.ass1.Direction;
import comp1110.ass1.Jumpin;
import comp1110.ass1.MovingPiece;


public class MoveNode {

    //current board state
    private Jumpin currentBoard;

    //last move, in string form
    private MoveRecord lastMove;

    //previous node
    private MoveNode prevNode;

    //booleans to record start & finish states
    private Boolean startNode;
    private Boolean endNode;

    public MoveNode(Jumpin currentBoard) {
        this.currentBoard=currentBoard;
        this.startNode=true;

        this.endNode=this.currentBoard.isSolution();
    }

    public MoveNode(Jumpin currentBoard, MoveRecord lastMove, MoveNode prevNode) {
        this.currentBoard=currentBoard;
        this.lastMove=lastMove;
        this.prevNode=prevNode;
        this.startNode=false;

        this.endNode=this.currentBoard.isSolution();
    }

    public MoveNode newMove(MovingPiece piece, Direction dir, Jumpin board) {
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
    }

    /**
     * The entry point to the tree of possible moves from a given board state
     */
    public class MoveTree {


    }
}
