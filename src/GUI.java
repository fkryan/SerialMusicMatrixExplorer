import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    JPanel mainGrid;
    JLabel grid[][];
    Matrix matrix;

    public GUI(PitchSet p) {
        super("Matrix Generator");
        matrix = new Matrix(p);
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
        fillMainGrid(true);
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
        regenerate.addActionListener(e -> {
            matrix = new Matrix(p);
            mainGrid.setVisible(false);
            mainGrid.removeAll();
            fillMainGrid(viewOptions.getSelectedItem().toString().equals("Scale Degrees"));
            mainGrid.setVisible(true);
        });
        JButton restart = new JButton("Enter New Pitch Set");
        optionPanel.add(viewOptions);
        optionPanel.add(regenerate);
        optionPanel.add(restart);
        add(optionPanel);
        setVisible(true);
    }

    void fillMainGrid(boolean scaleDegrees) {
        int gridSize = matrix.size + 2;
        mainGrid.setLayout(new GridLayout(gridSize, gridSize));
        grid = new JLabel[matrix.size][matrix.size];
        //0, 0 position
        mainGrid.add(new JLabel(""));
        for (int j = 0; j < matrix.size; j++) {
            JButton top = new JButton("I" + matrix.board[0][j].scaleDegree);
            mainGrid.add(top);
        }
        mainGrid.add(new JLabel(""));
        for (int i = 0; i < matrix.size; i++) {
            JButton left = new JButton("P" + matrix.board[i][0].scaleDegree);
            mainGrid.add(left);
            for (int j = 0; j < matrix.size; j++) {
                if (scaleDegrees) {
                    grid[i][j] = new JLabel(matrix.board[i][j].toString(), SwingConstants.CENTER);
                }
                else {
                    grid[i][j] = new JLabel(matrix.board[i][j].noteName, SwingConstants.CENTER);
                }
                grid[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
                mainGrid.add(grid[i][j]);
            }
            JButton right = new JButton("R" + matrix.board[i][matrix.size -1].toString());
            mainGrid.add(right);
        }
        mainGrid.add(new JLabel(""));
        for (int j = 0; j < matrix.size; j++) {
            JButton bottom = new JButton("RI" + matrix.board[matrix.size -1][j].scaleDegree);
            mainGrid.add(bottom);
        }
    }

    void displayNoteNames() {
        for (int i = 0; i < matrix.size; i++) {
            for (int j = 0; j < matrix.size; j++) {
                grid[i][j].setText(matrix.board[i][j].noteName);
            }
        }
    }

    void displayScaleDegrees() {
        for (int i = 0; i < matrix.size; i++) {
            for (int j = 0; j < matrix.size; j++) {
                grid[i][j].setText(matrix.board[i][j].toString());
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
        GUI g = new GUI(p);
    }
}
