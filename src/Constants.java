import java.awt.*;
import java.util.*;
import java.util.List;

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
    public static final Color HIGHLIGHT_COLOR = new Color(240, 25, 255);

    public static final Map<String, Integer> noteToMidi;
    static {
        noteToMidi = new HashMap<String, Integer>();
        noteToMidi.put("A", 57);
        noteToMidi.put("A#", 58);
        noteToMidi.put("Bb", 58);
        noteToMidi.put("B", 59);
        noteToMidi.put("B#", 60);
        noteToMidi.put("Cb", 59);
        noteToMidi.put("C", 60);
        noteToMidi.put("C#", 61);
        noteToMidi.put("Db", 61);
        noteToMidi.put("D", 62);
        noteToMidi.put("D#", 63);
        noteToMidi.put("Eb", 63);
        noteToMidi.put("E", 64);
        noteToMidi.put("Fb", 64);
        noteToMidi.put("E#", 65);
        noteToMidi.put("F", 65);
        noteToMidi.put("F#", 66);
        noteToMidi.put("Gb", 66);
        noteToMidi.put("G", 67);
        noteToMidi.put("G#", 68);
        noteToMidi.put("Ab", 68);

    }
}
