import java.util.LinkedList;
import java.util.HashMap;

public class PitchSet {
    HashMap<Integer, String> degreeToNote;
    LinkedList<String> notes;

    public PitchSet() {
        degreeToNote = new HashMap<Integer, String>();
        notes = new LinkedList<String>();
    }

    public void addNote(String note) {
        if (!Constants.validNotes.contains(note)) {
            throw new IllegalArgumentException();
        }
        notes.add(note);
        degreeToNote.put(notes.size() - 1, note);
    }

    public static void main(String[] args) {
        PitchSet test = new PitchSet();
        for (String s : args) {
            test.addNote(s);
        }
    }
}
