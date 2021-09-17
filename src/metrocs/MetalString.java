
import java.util.*;

/**
 * MetalString objects simulate metal strings that resonate at a specified
 * fundamental frequency.
 * Internally, the object uses the Karplus-Strong
 * algorithm to simulate changes in the string's vibration.
 *
 * @author Jeremy Bivans
 * @version TODO:9/12/21 v. 2
 */
public class MetalString implements MusicalString {
    /** Decay factor appropriate for a plucked string. */
    public static final double ENERGY_DECAY_FACTOR = 0.996;

    LinkedList<Double> MetalStrings = new LinkedList<>(); // LinkedList declaration for buffer?

    //Tracker for iterations of tick
    private int count = 0;

    public MetalString(final double frequency) {

        int N;

        if (MetalStrings.size() < 2 || frequency <= 0.0) { throw new IllegalArgumentException(); }
        else {
            N = (int) (StdAudio.SAMPLE_RATE / frequency);          //Size of the buffer is given by the frequency and sample rate
        }

        for (int i = 0; i <= N; i++) {               //Fills the LinkedList with placeholder zeroes

            MetalStrings.addLast(0.0);

        }

    }

    public void pluck() {

        //MetalString MetalStrings;
        double max = 0.5;                           //variables for the max/min of the random samples
        double min = -0.5;

        for (int i = 0; i <= MetalStrings.size(); i++) {                   //Adds the random sample to the back and removes the zero placeholder

            MetalStrings.addLast(Math.random() * (max - min) + min); //random sample
            MetalStrings.removeFirst();                   //removes placeholder zeroes

        }

    }

    public void tick() {

        double temp = ((MetalStrings.getFirst() + MetalStrings.get(1)) / 2) * ENERGY_DECAY_FACTOR;   //avg of 1st 2 samples for Karplus-Strong update

        MetalStrings.removeFirst();                       //get rid of the first sample in buffer

        MetalStrings.addLast(temp);                      //puts the Karplus-Strong update into the buffer

        count++;                                    //To track iterations of tick for time

    }

    public double sample() { return MetalStrings.getFirst(); } //Sample shows the current head of the LinkedList

    public int now() { return count; }//Return counter variable embedded in tick method to count iterations of tick

}
