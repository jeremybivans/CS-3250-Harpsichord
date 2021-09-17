
//import org.junit.jupiter.api.Test;

/**
 * Interactive tests for Harpsichord.
 */
public class HarpsichordTest {
    /** Default number of keypresses for testing. */
    private static final int NUM_PRESSES = 20;

    /**
     * Play a Harpsichord for a fixed number of key-presses.
     */
  //  @Test
    public void interactionTest() {
        Instrument instr = new Harpsichord();
        int numPresses = NUM_PRESSES;
        System.out.println("Press " + numPresses + " keys.");
        while (numPresses > 0) {
            // If the user has typed a key, process it.
            if (StdDraw.hasNextKeyTyped()) {
                numPresses--;
                char key = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (instr.hasNote(key)) {
                    instr.startNote(key);
                } else {
                    System.out.println("Invalid key-press: " + key);
                }
            }
            instr.play();
            instr.tick();
        }
    }
}
