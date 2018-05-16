import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

public class Testing {
    @Test
    public void testPitchSetBasics() {
        PitchSet p = new PitchSet();
        p.addNote("A");
        assertEquals(1, p.notes.size());
        p.addNote("B");
        assertEquals(2, p.notes.size());
        p.addNote("C");
        assertEquals(3, p.notes.size());
        assertEquals(p.degreeToNote.get(0), "A");
        assertEquals(p.degreeToNote.get(1), "B");
        assertEquals(p.degreeToNote.get(2), "C");
    }

    @Test
    public void testPitchSetException() {
        PitchSet p = new PitchSet();
        p.addNote("C#");
        p.addNote("D");
        try {
            p.addNote("Abb");
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            for (String note: Constants.validNotes) {
                p.addNote(note);
            }
        }
        catch (IllegalArgumentException e) {
            fail();
        }
    }

    @Test
    public void testRandomRow() {
        int test[] = Matrix.randomRow(12);
        assertEquals(12, test.length);
        //check that each degree shows up exactly once
        int seen[] = new int[12];
        for (int degree : test) {
            seen[degree]++;
        }
        for (int count : seen) {
            assertEquals(1, count);
        }
    }

    @Test
    public void randomMatrixValidation() {
        for (int k = 0; k < 100; k++) {
            Matrix m = new Matrix(new PitchSet(Constants.defaultPitches));
            assertTrue(quickValidation(m));
        }
    }

    //uses property that the top right -> bottom left should all be the same pitch
    static boolean quickValidation(Matrix m) {
        for (int i = 1; i < m.board.length; i++) {
            if (!m.board[0][0].equals(m.board[i][i])) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void testOnKnownMatrix() {
        int[][] knownMatrix = {
                {4, 3, 5, 1, 8, 10, 7, 6, 11, 0, 9, 2},
                {5, 4, 6, 2, 9, 11, 8, 7, 0, 1, 10, 3},
                {3, 2, 4, 0, 7, 9, 6, 5, 10, 11, 8, 1},
                {7, 6, 8, 4, 11, 1, 10, 9, 2, 3, 0, 5},
                {0, 11, 1, 9, 4, 6, 3, 2, 7, 8, 5, 10},
                {10, 9, 11, 7, 2, 4, 1, 0, 5, 6, 3, 8},
                {1, 0, 2, 10, 5, 7, 4, 3, 8, 9, 6, 11},
                {2, 1, 3, 11, 6, 8, 5, 4, 9, 10, 7, 0},
                {9, 8, 10, 6, 1, 3, 0, 11, 4, 5, 2, 7},
                {8, 7, 9, 5, 0, 2, 11, 10, 3, 4, 1, 6},
                {11, 10, 0, 8, 3, 5, 2, 1, 6, 7, 4, 9},
                {6, 5, 7, 3, 10, 0, 9, 8, 1, 2, 11, 4}
        };
        Matrix m = new Matrix(new PitchSet(Constants.defaultPitches));
        int row[] = {4, 3, 5, 1, 8, 10, 7, 6, 11, 0, 9, 2};
        m.completeMatrix(row);
        for (int i = 0; i < m.size; i++) {
            for (int j = 0; j < m.size; j++) {
                assertEquals(knownMatrix[i][j], m.board[i][j].scaleDegree);
            }
        }

    }


}
