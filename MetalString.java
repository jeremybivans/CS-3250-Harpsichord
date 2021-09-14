
import java.util.*;
import java.lang;

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

    public MetalString(final double frequency) {

        double frequency = 0.0;                             //variables to determine
        int N = 0;

        LinkedList<Double> MetalStrings = new LinkedList<Double>(); // LinkedList declaration for buffer?

        MetalStrings MetalString;

        if (N < 2 || frequency <= 0.0) { throw IllegalArgumentException; }
        else {
            N = Std.Audio.SAMPLE_RATE / frequency;          //Size of the buffer is given by the frequency and sample rate
        }

        MetalString.size = N;

        for (int i = 0; i <= N; i++) {               //Fills the LinkedList with placeholder zeroes

            MetalString.addLast(0.0);

        }

    }
    public void pluck() {

        double max = 0.5;                           //variables for the max/min of the random samples
        double min = -0.5;

        for (int i = 0; i <= N; i++) {                   //Adds the random sample to the back and removes the zero placeholder

            MetalString.addLast(Math.random() * (max - min) + min); //random sample
            MetalString.removeFirst();                   //removes placeholder zeroes

        }

    }

    public void tick() {

        double temp = 0.0;                          //placeholder variable to apply the Karpus-Strong update to buffer
        int count = 0;                              //Tracker for iterations of tick

        temp = (MetalString.getFirst + MetalString.get(1)) / 2;   //avg of 1st 2 samples for Karpus-Strong update

        MetalString.removeFirst();                       //get rid of the first sample in buffer

        MetalString.addLast(temp);                      //puts the Karpus-Strong update into the buffer

        count++;                                    //To track iterations of tick for time

    }

    public double sample() { return MetalString.getFirst(); } //Sample shows the current head of the LinkedList

    public int now() { return count; }//Return counter variable inbedded in tick method to count iterations of tick

}
