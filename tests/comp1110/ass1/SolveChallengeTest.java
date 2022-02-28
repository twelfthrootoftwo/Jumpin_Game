package comp1110.ass1;

import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class SolveChallengeTest {

    private void test(String expected, Challenge c) {
        String initState = c.getInitialState();
        Jumpin jumpin = new Jumpin(c);
        String[] out = jumpin.solve();
        assertEquals(expected, out[0], "For challenge " + c + " with initial state " + initState + ":\n" +
                "Expected solution to reach final state " + expected + ", but jumpin.solve() returned state " +
                out[0] + " and ended at state " + jumpin.stateToString(false));
    }

    @Test
    public void testStarterChallenge() {
        assertTimeoutPreemptively(Duration.ofMillis(1000), () ->
            test("C04B44G22M132434", Challenge.CHALLENGES[11])
        );
    }

    @Test
    public void testJuniorChallenge() {
        assertTimeoutPreemptively(Duration.ofMillis(1000), () ->
            test("C44M402122FS32W33", Challenge.CHALLENGES[23])
        );
    }

    @Test
    public void testExpertChallenge() {
        assertTimeoutPreemptively(Duration.ofMillis(1000), () ->
                test("B00G40M301222FE41", Challenge.CHALLENGES[35])
        );
    }

    @Test
    public void testMasterChallenge() {
        assertTimeoutPreemptively(Duration.ofMillis(1000), () ->
            test("C22B00G40M301121", Challenge.CHALLENGES[47])
        );
    }

    @Test
    public void testWizardChallenge() {
        assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
            test("C04B40G00M2244FE11S13", Challenge.CHALLENGES[48]);
        });
    }

}
