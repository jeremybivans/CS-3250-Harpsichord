
import java.util.*;
import java.lang.Array;
import java.lang.String;

/**
 * Harpsichords with a three-octave range
 * including two octaves below A4 (440Hz).
 *
 * @author Jeremy Bivans
 * @version $Id: Harpsichord.java v5 2021 9-12 10 PM jody $
 */
public class Harpsichord implements Instrument {
    /** The keyboard layout for this instrument. */
    private String keyboard = Instrument.QWERTY_KEY_MAP;
    /** Frequency of concert A (440Hz). */
    private static final double CONCERT_A = 440.0;
    /** Number of notes in an octave. */
    private static final double NOTES_PER_OCTAVE = 12.0;

    private MusicalString[] strings = new MusicalString[keyboard.length()]; // container holding each string
    /**
     * Construct a harpsichord object, initializing its strings.
     */
    public Harpsichord() {


        ArrayList<Double> notes = new ArrayList<Double>(36);    //List of arrays to 'hold' the note values in Hz

        for (int i = 0; i < strings.length; i++) {

            double concert = CONCERT_A * Math.pow(2, (i - 24) / NOTES_PER_OCTAVE); // iterates through array of notes to ge tthe right pitch based off of concert A
            MusicalString harpString = new MetalString(concert);        //Assigns the note to a string based on relative position
            strings[i] = harpString;

        }

    }

    @Override
    public boolean hasNote(final char noteChar) {

        if ( keyboard.contains(noteChar)) { return true; }

        else { return false; }

    }

    /**
     * Plucks the string for note associated with
     * the given keyboard character.
     * @param noteChar the character specifying the note
     * @throws IllegalArgumentException if there is no match
     */
    @Override
    public void startNote(final char noteChar) {

        if ( keyboard.contains(noteChar)) {

            for (int i = 0; i < strings.length; i++) {
                strings[i] = keyboard.charAt(i);

            }

        }

        else { throw IllegalArgumentException; }

        }



    /** @TODO Implementation */ //Used the Mini harpsichord logic
    @Override
    public void play() {
        // Compute the superposition of samples.
        double sample = strings[i];
        // Send the result to the sound card.
        StdAudio.play(sample);
        // Update the graphical display.
        this.samples.remove();
        this.samples.add(sample);
        if (strings.now() % DISPLAY_INTERVAL == 0) {
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

    /** @TODO Implementation */
    @Override
    public void tick() {

        for (int i = 0; i < strings.length; i++) {
            strings[i].tick();
         }

    }
}
