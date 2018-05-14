import java.util.LinkedList;
import java.util.Arrays;
public class Matrix {

    class Square {
        int scaleDegree;
        String noteName;

        public Square(int degree) {
            scaleDegree = degree;
        }

        public String toString() {
            return "" + scaleDegree;
        }
    }

    Square[][] board;
    int size;

    public Matrix(int numTones) {
        if (numTones < 1 || numTones > 12) {
            throw new IllegalArgumentException();
        }
        board = new Square[numTones][numTones];
        size = numTones;
        completeMatrix(randomRow(numTones));
    }

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
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("   ");
        for (Square j : board[0]) {
            sb.append(String.format("%5s", "I" + j));
        }
        sb.append("\n\n");
        for (int i = 0; i < size; i++) {
            sb.append(String.format("%-3s", "P"+ board[i][0]));
            for (int j = 0; j < size; j++) {
                sb.append(String.format("%5s", board[i][j]));
            }
            sb.append("     ");
            sb.append(String.format("%-5s", "R" + board[i][size-1]));
            sb.append("\n");
        }
        sb.append("\n");
        sb.append("   ");
        for (Square j : board[size-1]) {
            sb.append(String.format("%5s", "RI" + j));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);
        Matrix test = new Matrix(num);
        System.out.println(test.toString());
    }
}
