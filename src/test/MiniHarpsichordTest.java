
//import org.junit.jupiter.api.Test;

/**
 * Interactive tests for MiniHarpsichord.
 */
public class MiniHarpsichordTest {
    /** Default number of keypresses for testing. */
    private static final int NUM_PRESSES = 20;

    /**
     * Play a MiniHarpsichord for fixed number of key-presses.
     */
  //  @Test
    public void interactionTest() {
        Instrument miniharp = new MiniHarpsichord();
        int numPresses = NUM_PRESSES;
        System.out.println("Press " + numPresses + " keys.");
        while (numPresses > 0) {
            // If the user has typed a key, process it.
            if (StdDraw.hasNextKeyTyped()) {
                numPresses--;
                char key = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (miniharp.hasNote(key)) {
                    miniharp.startNote(key);
                } else {
                    System.out.println("Invalid key-press: " + key);
                }
            }
            miniharp.play();
            miniharp.tick();
        }
    }
}
