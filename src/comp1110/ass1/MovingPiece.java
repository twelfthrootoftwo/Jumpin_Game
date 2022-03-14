package comp1110.ass1;

public interface MovingPiece {

    public MoveResults moveForecast(Direction dir, Jumpin board);
    public boolean makeMove(Direction dir, Jumpin board);
    public Position getPosition();
    public String actionToString(Direction dir);

    /**
     * class to allow canMove function to return both the new position and boolean for whether the move is valid
     */
    public static class MoveResults {
        //TRUE if the resulting move is valid, FALSE otherwise
        private Boolean validMove;

        //The new position if the move is valid, original position otherwise
        private Position newPos;

        public MoveResults() {

        }

        public MoveResults(Boolean validMove, Position newPos) {
            this.validMove=validMove;
            this.newPos=newPos;
        }

        // @return whether move is valid
        public Boolean getValid() { return this.validMove;}

        // @return resulting position
        public Position getPos() { return this.newPos;}

    }


}
