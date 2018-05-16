import java.awt.*;
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

    public static final Color BACKGROUND_COLOR = new Color(250, 250, 255);
    public static final Color ROWBUTTON_COLOR = new Color(230, 204, 255);
}
