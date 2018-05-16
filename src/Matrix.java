import java.util.LinkedList;
public class Matrix {

    //Represents a single pitch in the matrix
    class Square {
        int scaleDegree; //order in the scale, starting from 0
        String noteName; //letter name i.e. C, Bb, F#, etc.

        public Square(int degree) {
            scaleDegree = degree;
        }

        public Square(int degree, String note) {
            scaleDegree = degree;
            noteName = note;
        }

        public String toString() {
            return "" + scaleDegree;
        }

        public boolean equals(Object other) {
            if (!(other instanceof Square)) {
                return false;
            }
            Square s = (Square) other;
            return (scaleDegree == s.scaleDegree && noteName.equals(s.noteName));
        }
    }

    Square[][] board; //2D matrix made up of pitches
    int size;
    PitchSet pitches; //used for relationship between scale degree (ordering in set) and letter name

    public Matrix(PitchSet p) {
        pitches = p;
        size = pitches.notes.size();
        if (size < 1 || size > Constants.PITCHSET_MAX) {
            throw new IllegalArgumentException("invalid number of pitches in set");
        }
        board = new Square[size][size];
        completeMatrix(randomRow(size));
    }

    //generates a random numTones-tone row and stores pitch degrees in an array
    static int[] randomRow(int numTones) {
        int[] result = new int[numTones];
        int index = 0;
        LinkedList<Integer> degrees = new LinkedList<>();
        for (int i = 0; i < numTones; i++) {
            degrees.add(i);
        }
        for (int i = numTones - 1; i >= 0; i--) {
            int j = (int) (Math.random() * i);
            result[index] = degrees.remove(j);
            index++;
        }
        return result;
    }

    //modular arithmetic to keep degrees in the range 0->size
    static int fixDegree(int degree, int size) {
       if (degree >= size) {
           return degree % size;
       }
       else if (degree < 0) {
           return size + degree;
       }
       else {
           return degree;
       }
    }

    //given a row, completes the full matrix
    public void completeMatrix(int[] row) {
        if (row.length != size) {
            throw new IllegalArgumentException();
        }
        //copy over first row
        for (int i = 0; i < size; i++) {
            board[0][i] = new Square(row[i]);
        }

        //fill in first column (inverted)
        for (int i = 1; i < size; i++) {
            int diff = board[0][i-1].scaleDegree - board[0][i].scaleDegree;
            board[i][0] = new Square(fixDegree(board[i-1][0].scaleDegree + diff, size));
        }

        //fill in rest
        int first = row[0];
        for (int i = 1; i < size; i++) {
            int firstInRow = board[i][0].scaleDegree;
            int diff = firstInRow - first;
            for (int j = 1; j < size; j++) {
                board[i][j] = new Square(fixDegree(board[0][j].scaleDegree + diff, size));
            }
        }

        //could be added to previous traversal for efficiency but eh
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j].noteName = pitches.degreeToNote.get(board[i][j].scaleDegree);
            }
        }
    }

    //nice display with Squares represented by scale degrees
    public String toStringNumbers() {
        StringBuilder sb = new StringBuilder("   ");
        for (Square j : board[0]) {
            sb.append(String.format("%5s", "I" + j.scaleDegree));
        }
        sb.append("\n\n");
        for (int i = 0; i < size; i++) {
            sb.append(String.format("%-3s", "P"+ board[i][0].scaleDegree));
            for (int j = 0; j < size; j++) {
                sb.append(String.format("%5s", board[i][j].scaleDegree));
            }
            sb.append("     ");
            sb.append(String.format("%-5s", "R" + board[i][size-1].scaleDegree));
            sb.append("\n");
        }
        sb.append("\n");
        sb.append("   ");
        for (Square j : board[size-1]) {
            sb.append(String.format("%5s", "RI" + j.scaleDegree));
        }
        return sb.toString();
    }

    //nice display with Squares represented by letter names
    public String toStringLetters() {
        StringBuilder sb = new StringBuilder("   ");
        for (Square j : board[0]) {
            sb.append(String.format("%5s", "I" + j.scaleDegree));
        }
        sb.append("\n\n");
        for (int i = 0; i < size; i++) {
            sb.append(String.format("%-3s", "P"+ board[i][0].scaleDegree));
            for (int j = 0; j < size; j++) {
                sb.append(String.format("%5s", board[i][j].noteName));
            }
            sb.append("     ");
            sb.append(String.format("%-5s", "R" + board[i][size-1].scaleDegree));
            sb.append("\n");
        }
        sb.append("\n");
        sb.append("   ");
        for (Square j : board[size-1]) {
            sb.append(String.format("%5s", "RI" + j.scaleDegree));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        PitchSet p = new PitchSet();
        for (String s: args) {
            p.addNote(s);
        }
        Matrix test = new Matrix(p);
        System.out.println(test.toStringNumbers());
        System.out.println(test.toStringLetters());

    }
}
