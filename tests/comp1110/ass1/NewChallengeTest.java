package comp1110.ass1;

import org.junit.jupiter.api.*;

import java.util.HashSet;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(value = 1000, unit = MILLISECONDS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class NewChallengeTest {

    private int getDifficulty(Challenge challenge) {
        for (int i = 0; i < Challenge.CHALLENGES.length; i++) {
            if (challenge == Challenge.CHALLENGES[i]) {
                return i / (Challenge.CHALLENGES.length / 5);
            }
        }
        return -1;
    }

    private int countChallenges(Challenge[] challenges) {
        HashSet<Challenge> challengeSet = new HashSet<>();
        for (Challenge c : challenges) {
            challengeSet.add(c);
        }
        return challengeSet.size();
    }

    private void doTest(int difficulty) {
        Challenge[] out = new Challenge[12];
        for (int i = 0; i < out.length; i++) {
            out[i] = Challenge.newChallenge(difficulty);
            int diff = getDifficulty(out[i]);
            assertEquals(diff, difficulty, "Expected difficulty " + difficulty + ", but " + (diff == -1 ? "did not get one from the prepared objectives" : "got one of difficulty " + diff) + ": problem number " + out[i].getProblemNumber() + ".");
        }
        int unique = countChallenges(out);
        assertTrue(unique >= 3, "Expected at least 3 different objectives after calling newObjective() 12 times, but only got " + unique + ".");
    }

    @Test
    public void testStarter() {
        doTest(0);
    }

    @Test
    public void testJunior() {
        doTest(1);
    }

    @Test
    public void testExpert() {
        doTest(2);
    }

    @Test
    public void testMaster() {
        doTest(3);
    }

    @Test
    public void testWizard() {
        doTest(4);
    }

}
