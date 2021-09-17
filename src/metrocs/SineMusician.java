import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * Plays song from notes given in a selected text file.
 * Constructs sine wave for each note, rather than using
 * a given musical instrument.
 */
public class SineMusician implements SongPlayer {
    /** Frequency for concert A pitch = 440Hz. */
    private static final double CONCERT_A = 440.0;
    /** Number of notes in an octave. */
    private static final double NOTES_PER_OCTAVE = 12.0;

    @Override
    public JFileChooser getSong() {
        JFileChooser chooser = null;
        chooser = new JFileChooser(new File("."));
        FileNameExtensionFilter filter =
            new FileNameExtensionFilter("Song Files", "txt");
        chooser.setFileFilter(filter);
        int result = chooser.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) {
            chooser = null;
        }
        return chooser;
    }

    /**
     * Plays the specified song using constructed sine waves.
     * @param songFile the file containing the song
     * @param instrument ignored
     */
    public void playSong(final JFileChooser songFile,
                         final Instrument instrument) {
        Scanner input = null;
        try {
            input = new Scanner(songFile.getSelectedFile());
        } catch (FileNotFoundException e) {
            System.err.println(e);
            return;
        }

        while (input.hasNextInt()) {
            // Read in the note, where 0 = Concert A (A4).
            int note = input.nextInt();
            // Read in duration in seconds.
            double duration = input.nextDouble();
            // Build sine wave at indicated frequency.
            double herz = CONCERT_A * Math.pow(2, note / NOTES_PER_OCTAVE);
            int numSamples = (int) (StdAudio.SAMPLE_RATE * duration);
            double[] samples = new double[numSamples + 1];
            for (int i = 0; i <= numSamples; i++) {
                samples[i]
                    = Math.sin(2 * Math.PI * i * herz / StdAudio.SAMPLE_RATE);
            }
            StdAudio.play(samples);
        }
    }

    /**
     * Driver that invokes the musician.
     * Asks the user to select a file containing the score for
     *   a song to be performed.
     * @param args ignored
     */
    public static void main(final String[] args) {
        SongPlayer player = new SineMusician();
        player.playSong(player.getSong(), null);
    }
}
