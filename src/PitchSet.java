import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;

public class PitchSet {
    HashMap<Integer, String> degreeToNote;
    LinkedList<String> notes;

    public PitchSet() {
        degreeToNote = new HashMap<Integer, String>();
        notes = new LinkedList<String>();
    }

    public PitchSet(List<String> notesToAdd) {
        this();
        for (String note : notesToAdd) {
            this.addNote(note);
        }
    }

    public void addNote(String note) {
        if (!Constants.validNotes.contains(note)) {
            throw new IllegalArgumentException(note + " is not a valid note");
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
