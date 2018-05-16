import java.util.LinkedList;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
public class Constants {
    public static final String validNotesArray[] = {"A", "B", "C", "D", "E", "F", "G", "A#", "Ab", "B#", "Bb", "C#", "Cb",
            "D#", "Db", "E#", "Eb", "F#", "Fb", "G#", "Gb"};
    public static final String defaultPitchArray[] = {"C", "C#", "D", "Eb", "E", "F", "F#", "G", "Ab", "A", "Bb", "B"};

    public static final List<String> defaultPitches = Arrays.asList(defaultPitchArray);

    public static final List<String> validNotes =
            Arrays.asList(validNotesArray);
    public static final int PITCHSET_MAX = 12;
}
