package comp1110.ass1;

public class MakeMoveResult {

    Challenge challenge;
    Direction[] directions;
    String[] expectedStates;
    Position[][] expectedPositions;
    boolean[] outcomes;
    int[] idxs;

    public MakeMoveResult(Challenge c, Direction[] ds, String[] states, Position[][] positions, boolean[] outcomes, int[] idxs) {
        this.challenge = c;
        this.directions = ds;
        this.expectedStates = states;
        this.expectedPositions = positions;
        this.outcomes = outcomes;
        this.idxs = idxs;
    }

    public MakeMoveResult(Challenge c, Direction d, String state, Position[] positions, boolean outcome, int idx) {
        this.challenge = c;
        this.directions = new Direction[]{d};
        this.expectedStates = new String[]{state};
        this.expectedPositions = new Position[][]{positions};
        this.outcomes = new boolean[]{outcome};
        this.idxs = new int[]{idx};
    }

    public MakeMoveResult(Challenge c, Direction d, int idx, boolean isFox) {
        this.challenge = c;
        this.directions = new Direction[]{d};
        this.expectedStates = new String[]{c.getInitialState()};
        this.idxs = new int[]{idx};
        if (isFox) {
            Fox fox = new Jumpin(c).getFoxes()[idx];
            this.expectedPositions = new Position[][]{new Position[]{fox.getHeadPosition(), fox.getTailPosition()}};
        }
        else {
            Rabbit rabbit = new Jumpin(c).getRabbits()[idx];
            this.expectedPositions = new Position[][]{new Position[]{rabbit.getPosition()}};
        }
        this.outcomes = new boolean[]{false};
    }

}
