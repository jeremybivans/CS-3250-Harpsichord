import java.util.ArrayList;
import java.util.Arrays;

/**
 * Harpsichords with a three-octave range
 * including two octaves below A4 (440Hz).
 *
 * @author Jeremy Bivans
 * @version $Id: Harpsichord.java v5 2021 9-12 10 PM jody $
 */
public class Harpsichord implements Instrument {
    /** The keyboard layout for this instrument. */
    final private String keyboard = Instrument.QWERTY_KEY_MAP;
    /** Frequency of concert A (440Hz). */
    private static final double CONCERT_A = 440.0;
    /** Number of notes in an octave. */
    private static final double NOTES_PER_OCTAVE = 12.0;
    /** Width of graphic display. */
    private static final int DISPLAY_WIDTH = 512;
    /** Time interval for updating display. */
    private static final int DISPLAY_INTERVAL = 500;

    final private MusicalString[] strings = new MusicalString[keyboard.length()];// container holding each string
    ArrayList<Double> samples = new ArrayList<>(36);    //List of arrays to 'hold' the note values in Hz

    /**
     * Construct a harpsichord object, initializing its strings.
     */

    public Harpsichord() {

        for (int i = 0; i < strings.length; i++) {

            double concert = CONCERT_A * Math.pow(2, (i - 24) / NOTES_PER_OCTAVE); // iterates through array of notes to ge the right pitch based off of concert A
            MusicalString harpString = new MetalString(concert);        //Assigns the note to a string based on relative position
            strings[i] = harpString;

        }

        for (int i = 0; i < DISPLAY_WIDTH; i++) {
            samples.add(0.0);
        }

    }

    @Override
    public boolean hasNote(final char noteChar) {

        return keyboard.contains(String.valueOf(noteChar));

    }

    /**
     * Plucks the string for note associated with
     * the given keyboard character.
     * @param noteChar the character specifying the note
     * @throws IllegalArgumentException() if there is no match
     */
    @Override
    public void startNote(final char noteChar) {

        if ( keyboard.contains(String.valueOf(noteChar))) {

            Arrays.fill(strings, keyboard);

        }

        else { throw new IllegalArgumentException(); }

        }



        //Used the Mini harpsichord logic
    @Override
    public void play() {
        // Compute the superposition of samples.
        int i = 0;
        double sample = strings[i].sample();
        // Send the result to the sound card.
        StdAudio.play(sample);
        // Update the graphical display.
        this.samples.remove(i);
        this.samples.add(sample);
        if (strings[i].now() % DISPLAY_INTERVAL == 0) {
            StdDraw.show(0);
            StdDraw.clear();
            double next = samples.remove(i);
            samples.add(next);
            double prevY = next + 0.5;
            for ( i = 1; i < samples.size(); i++) {
                next = samples.remove(i);
                samples.add(next);
                double nextY = next + 0.5;
                StdDraw.line((i - 1) / (double) DISPLAY_WIDTH, prevY,
                        i / (double) DISPLAY_WIDTH, nextY);
                prevY = nextY;
            }
        }
    }

    @Override
    public void tick() {

        for (MusicalString string : strings) {
            string.tick();
        }

    }
}
