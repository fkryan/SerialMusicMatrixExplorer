import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    JPanel mainGrid;
    JComponent grid[][];
    Matrix matrix;

    public GUI(Matrix m) {
        super("Matrix Generator");
        matrix = m;
        int idealWidth = (int) (66.67 * matrix.size);
        if (idealWidth < 450) {
            idealWidth = 450;
        }
        setSize(idealWidth, 500);
        setLookAndFeel();
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel directions = new JPanel();
        JLabel greeting = new JLabel("Welcome! Press a row form name to hear it played.");
        directions.add(greeting);
        add(directions);
        mainGrid = new JPanel();
        int gridSize = matrix.size + 2;
        mainGrid.setLayout(new GridLayout(gridSize, gridSize));
        grid = new JComponent[gridSize][gridSize];
        grid[0][0] = new JLabel("");
        grid[0][gridSize - 1] = new JLabel("");
        grid[gridSize - 1][gridSize - 1] = new JLabel("");
        grid[gridSize - 1][0] = new JLabel("");
        for (int j = 1; j < gridSize - 1; j++) {
            grid[0][j] = new JButton("I" + matrix.board[0][j-1].scaleDegree);
            grid[gridSize - 1][j] = new JButton("RI" + matrix.board[matrix.size - 1][j-1]);
        }
        for (int i = 1; i < gridSize - 1; i++) {
            grid[i][0] = new JButton("P" + matrix.board[i-1][0].scaleDegree);
            grid[i][gridSize - 1] = new JButton("R" + matrix.board[i-1][matrix.size - 1].scaleDegree);
        }
        displayNoteNames();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                //System.out.println("adding " + grid[i][j]);
                mainGrid.add(grid[i][j]);
            }
        }
        add(mainGrid);
        JPanel optionPanel = new JPanel();
        JComboBox viewOptions = new JComboBox();
        viewOptions.addItem("Scale Degrees");
        viewOptions.addItem("Note Names");
        viewOptions.setEditable(true);
        viewOptions.addActionListener(e -> {
            if (viewOptions.getSelectedItem().toString().equals("Scale Degrees")) {
                displayScaleDegrees();
            }
            else {
                displayNoteNames();
            }
        });
        JButton regenerate = new JButton("New Random Matrix");
        JButton restart = new JButton("Enter New Pitch Set");
        optionPanel.add(viewOptions);
        optionPanel.add(regenerate);
        optionPanel.add(restart);
        add(optionPanel);
        setVisible(true);
    }

    void displayScaleDegrees() {
        int gridSize = matrix.size + 2;
        for (int i = 1; i < gridSize - 1; i++) {
            for (int j = 1; j < gridSize - 1; j++) {
                grid[i][j] = new JLabel(matrix.board[i-1][j-1].toString(), SwingConstants.CENTER);
                grid[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
            }
        }
    }

    void displayNoteNames() {
        int gridSize = matrix.size + 2;
        for (int i = 1; i < gridSize - 1; i++) {
            for (int j = 1; j < gridSize - 1; j++) {
                grid[i][j] = new JLabel(matrix.board[i-1][j-1].noteName, SwingConstants.CENTER);
                grid[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
            }
        }
        
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
        String pitches1[] = {"C", "D", "E", "F", "G", "A", "B"};
        PitchSet p = new PitchSet();
        for (String pitch : pitches) {
            p.addNote(pitch);
        }
        GUI g = new GUI(new Matrix(p));
    }
}
