import java.util.Queue;
import java.util.LinkedList;
/**
 * Demonstration of Instrument interface implementation.
 * Plays audio and shows signal graphically.
 */
public class MiniHarpsichord implements Instrument {
    /** Width of graphic display. */
    private static final int DISPLAY_WIDTH = 512;
    /** Time interval for updating display. */
    private static final int DISPLAY_INTERVAL = 500;

    /** Frequency of concert A, 440Hz. */
    private static final double CONCERT_A = 440.0;
    /** Number of notes per octave. */
    private static final double NOTES_PER_OCTAVE = 12.0;

    private MusicalString stringA;
    private MusicalString stringB;
    private MusicalString stringC;
    private Queue<Double> samples;

    /**
     * Construct an instrument with three strings.
     */
    public MiniHarpsichord() {
        double concertA = CONCERT_A;
        double concertB = CONCERT_A * Math.pow(2, 2.0 / NOTES_PER_OCTAVE);
        double concertC = CONCERT_A * Math.pow(2, 3.0 / NOTES_PER_OCTAVE);
        this.stringA = new MetalString(concertA);
        this.stringB = new MetalString(concertB);
        this.stringC = new MetalString(concertC);
        this.samples = new LinkedList<Double>();
        for (int i = 0; i < DISPLAY_WIDTH; i++) {
            samples.add(0.0);
        }
    }

    @Override
    public boolean hasNote(final char noteChar) {
        return (noteChar == 'a' || noteChar == 'b' || noteChar == 'c');
    }

    @Override
    public void startNote(final char noteChar) {
        if (noteChar == 'a') {
            stringA.pluck();
        } else if (noteChar == 'b') {
            stringB.pluck();
        } else if (noteChar == 'c') {
            stringC.pluck();
        }
    }

    @Override
    public void play() {
        // Compute the superposition of samples.
        double sample = stringA.sample() + stringB.sample() + stringC.sample();
        // Send the result to the sound card.
        StdAudio.play(sample);
        // Update the graphical display.
        this.samples.remove();
        this.samples.add(sample);
        if (stringA.now() % DISPLAY_INTERVAL == 0) {
            StdDraw.show(0);
            StdDraw.clear();
            double next = samples.remove();
            samples.add(next);
            double prevY = next + 0.5;
            for (int i = 1; i < samples.size(); i++) {
                next = samples.remove();
                samples.add(next);
                double nextY = next + 0.5;
                StdDraw.line((i - 1) / (double) DISPLAY_WIDTH, prevY,
                             i / (double) DISPLAY_WIDTH, nextY);
                prevY = nextY;
            }
        }
    }

    public void tick() {
        stringA.tick();
        stringB.tick();
        stringC.tick();
    }
}
