import java.util.LinkedList;
import java.util.HashMap;

public class PitchSet {
    HashMap<String, Integer> noteToDegree;
    LinkedList<String> notes;

    public PitchSet() {
        noteToDegree = new HashMap<>();
        notes = new LinkedList<>();
    }

    public void addNote(String note) {
        if (!Constants.validNotes.contains(note)) {
            throw new IllegalArgumentException();
        }
        notes.add(note);
        noteToDegree.put(note, notes.size() - 1);
    }

    public Matrix generateMatrix() {
        return new Matrix(notes.size());
    }

    public static void main(String[] args) {
        PitchSet test = new PitchSet();
        for (String s : args) {
            test.addNote(s);
        }
        Matrix m = test.generateMatrix();
        System.out.println(m);
    }
}
