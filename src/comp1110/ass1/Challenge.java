package comp1110.ass1;

import java.util.Random;

public class Challenge {

    /** The identifying number associated with a challenge. **/
    private final int problemNumber;

    /** The initial state of the challenge. See the README for details about
     * the initial state. **/
    private final String initialState;

    /** The shortest number of moves required to solve a challenge. **/
    private final int shortestMoves;

    /**
     * This array defines a set of 60 pre-defined puzzle challenges.
     *
     * There are 5 categories of challenge, according to 5 difficulty levels,
     * with 12 CHALLENGES per difficulty level, organized within the array as
     * follows:
     *
     * Starter: 0-11
     * Junior: 12-23
     * Expert: 24-35
     * Master: 36-47
     * Wizard: 48-59
     *
     * Each challenge is encoded in terms of:
     * 1 - A string representing the initial state of the board (see README for
     *     further details).
     * 2 - An challenge problemNumber corresponding to the challenge problem
     *     number used in the original game.
     * 3 - The shortest number of moves required to solve the challenge.
     */
    public final static Challenge[] CHALLENGES = new Challenge[]{
            // Starter
            new Challenge("C32M102031", 1, 2),
            new Challenge("C40B20M304132", 2, 3),
            new Challenge("C10B24M302123", 3, 4),
            new Challenge("C14B02M032334", 4, 4),
            new Challenge("C04B20M110203", 5, 5),
            new Challenge("C00B32M102112", 6, 6),
            new Challenge("C44B32M132334", 7, 7),
            new Challenge("C22B42G02M411232", 8, 7),
            new Challenge("C24B40G00M103021", 9, 8),
            new Challenge("C40B20G00M103001", 10, 9),
            new Challenge("C42B30G00M102133", 11, 10),
            new Challenge("C42B12G02M132434", 12, 9),

            // Junior
            new Challenge("C21M301122FW31", 13, 4),
            new Challenge("C30M122242FE21", 14, 6),
            new Challenge("C43M202223FS31", 15, 9),
            new Challenge("C10M010223FN13", 16, 7),
            new Challenge("C03M410242FN10S31", 17, 7),
            new Challenge("C34M023204FW21E13", 18, 8),
            new Challenge("C21M204223FS33", 19, 11),
            new Challenge("C10M022223FN13", 20, 13),
            new Challenge("C30M023223FE11", 21, 9),
            new Challenge("C13M142444FS32W33", 22, 11),
            new Challenge("C30M403224FE11N13", 23, 16),
            new Challenge("C41M402122FS32W33", 24, 15),

            // Expert
            new Challenge("B33G03M414244FS11N30", 25, 13),
            new Challenge("B31G22M1013FE11", 26, 12),
            new Challenge("B31G00M400304FE11S13", 27, 13),
            new Challenge("B42G11M3203FN12", 28, 12),
            new Challenge("B33G44M24FS32", 29, 13),
            new Challenge("B13G24M42FS11W31", 30, 13),
            new Challenge("B40G32M102022FS34", 31, 17),
            new Challenge("B44G31M012141FS11E23", 32, 15),
            new Challenge("B31G44M323334FE11N13", 33, 13),
            new Challenge("B14G00M4021FS11N30", 34, 15),
            new Challenge("B32G31M1102FE13N33", 35, 14),
            new Challenge("B00G33M301222FE21", 36, 19),

            // Master
            new Challenge("C24M012204FS11E13", 37, 21),
            new Challenge("C44G03M202122FS11N30", 38, 21),
            new Challenge("C32B12G22M304233FE11W31", 39, 7),
            new Challenge("C31G14M201204FE13N33", 40, 19),
            new Challenge("C24B14M212304FS11", 41, 20),
            new Challenge("C00B43M213132FS12", 42, 17),
            new Challenge("B41G11M122232FW21", 43, 26),
            new Challenge("C13B41G14M223344FN11W21", 44, 19),
            new Challenge("C42B23G03M00FW21S34", 45, 21),
            new Challenge("C02B34G03M100111FN30", 46, 19),
            new Challenge("C24B41M223223FE31", 47, 24),
            new Challenge("C20B03G40M301121", 48, 28),

            // Wizard
            new Challenge("C40B31G34M2244FE11S13", 49, 36),
            new Challenge("C30B21G20M100122FW03", 50, 23),
            new Challenge("C00B01G23M042444FE21S32", 51, 27),
            new Challenge("C31B42G34M402304FE11S13", 52, 34),
            new Challenge("C41B30G10M010204FN11W13", 53, 33),
            new Challenge("C10B34G14M200113FS31", 54, 22),
            new Challenge("C22B03G23M210444FS33", 55, 32),
            new Challenge("C03B14G41M402243FE11N31", 56, 43),
            new Challenge("C00B11G13M221444FW31E43", 57, 55),
            new Challenge("C22B02G10M404204FN31W33", 58, 67),
            new Challenge("C31B42G03M302244FE11W23", 59, 63),
            new Challenge("C31B42G34M302203FE11", 60, 87)
    };

    /**
     * Given the initialState of the challenge and a problem number, constructs
     * a `Challenge` object.
     *
     * @param initialState   A string representing the list of initial tile
     *                       placement.
     * @param problemNum     The problem number from the original board game,
     *                       a value from 1 to 60.
     * @param shortestMoves  The shortest number of moves required to solve the
     *                       challenge.
     */
    public Challenge(String initialState, int problemNum, int shortestMoves) {
        assert problemNum >= 1 && problemNum <= 60;
        this.initialState = initialState;
        this.problemNumber = problemNum;
        this.shortestMoves = shortestMoves;
    }

    /**
     * Choose a new challenge, given a difficulty level.
     *
     * The method should select a randomized challenge from the 60 pre-defined
     * challenges, being sure to select a challenge with the correct level of
     * difficulty.
     *
     * (See the comments above the declaration of the CHALLENGES array.)
     *
     * So, for example, if the difficulty is 0 (starter), then this function
     * should use a randomized index between 0 and 11 (inclusive) to select a
     * starter level challenge from CHALLENGES. On the other hand, if the
     * difficulty is 3 (master), then this function should use a randomized
     * index between 36 and 47 (inclusive) to select a master level challenge
     * from CHALLENGES.
     *
     * @param difficulty The difficulty of the game (0 - starter, 1 - junior,
     *                   2 - expert, 3 - master, 4 - wizard)
     *
     * @return A challenge at the appropriate level of difficulty.
     */
    public static Challenge newChallenge(int difficulty) {
        assert difficulty >= 0 && difficulty <= 4;

        Random random=new Random();
        int choose=random.nextInt(12);
        int challengeNum=choose+difficulty*12;

        // FIXME: Task 4
        return CHALLENGES[challengeNum];
    }

    /**
     * @return The problem number of this challenge.
     */
    public int getProblemNumber() {
        return this.problemNumber;
    }

    /**
     * @return The initial state of this challenge.
     */
    public String getInitialState() {
        return this.initialState;
    }

    /**
     * @return The shortest number of moves required to solve this challenge.
     */
    public int getShortestMoves() {
        return this.shortestMoves;
    }
}
