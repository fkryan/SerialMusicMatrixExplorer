import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    public GUI(Matrix matrix) {
        super("Matrix Generator");
        setSize(500, 500);
        setLookAndFeel();
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainGrid = new JPanel();
        int gridSize = matrix.size + 2;
        mainGrid.setLayout(new GridLayout(gridSize, gridSize));
        JComponent grid[][] = new JComponent[gridSize][gridSize];
        grid[0][0] = new JLabel("MATRIX");
        grid[0][gridSize - 1] = new JLabel("none");
        grid[gridSize - 1][gridSize - 1] = new JLabel("none");
        grid[gridSize - 1][0] = new JLabel("none");
        for (int i = 1; i < gridSize - 1; i++) {
            for (int j = 1; j < gridSize - 1; j++) {
                grid[i][j] = new JLabel(matrix.board[i-1][j-1].toString());
            }
        }
        for (int j = 1; j < gridSize - 1; j++) {
            grid[0][j] = new JButton("I" + matrix.board[0][j-1].scaleDegree);
            grid[gridSize - 1][j] = new JButton("RI" + matrix.board[matrix.size - 1][j-1]);
        }
        for (int i = 1; i < gridSize - 1; i++) {
            grid[i][0] = new JButton("P" + matrix.board[i-1][0].scaleDegree);
            grid[i][gridSize - 1] = new JButton("R" + matrix.board[i-1][matrix.size - 1].scaleDegree);
        }
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                //System.out.println("adding " + grid[i][j]);
                mainGrid.add(grid[i][j]);
            }
        }
        add(mainGrid);
        setVisible(true);
    }

    void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        String pitches[] = {"C", "C#", "D", "Eb", "E", "F", "F#", "G", "Ab", "A", "Bb", "B"};
        PitchSet p = new PitchSet();
        for (String pitch : pitches) {
            p.addNote(pitch);
        }
        GUI g = new GUI(new Matrix(p));
    }
}
