import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class GUI extends JFrame {
    JPanel mainGrid;
    JLabel grid[][];
    Matrix matrix;
    Synthesizer midiSynth;
    MidiChannel mChannels[];

    public GUI(PitchSet p) {
        super("Matrix Explorer");
        try {
            midiSynth = MidiSystem.getSynthesizer();
            midiSynth.open();
            Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
            mChannels = midiSynth.getChannels();
            midiSynth.loadInstrument(instr[0]);
        }
        catch (MidiUnavailableException e) {}
        matrix = new Matrix(p);

        //scale JFrame to matrix size
        int idealWidth = (int) (66.67 * matrix.size);
        if (idealWidth < 450) {
            idealWidth = 450;
        }
        setSize(idealWidth, 500);
        setLookAndFeel();
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //user instructions at top of frame
        JPanel directions = new JPanel();
        JLabel greeting = new JLabel("Welcome! Press a row form name to hear it played.");
        directions.add(greeting);
        add(directions);

        //creates main matrix grid with row form labels
        mainGrid = new JPanel();
        mainGrid.setBackground(Constants.BACKGROUND_COLOR);
        fillMainGrid(true);
        add(mainGrid);

        //user options at bottom of frame
        JPanel optionPanel = new JPanel();
        //choose whether to view square in matrix as scale degrees or note names
        JComboBox viewOptions = new JComboBox();
        viewOptions.addItem("Pitch Numbers");
        viewOptions.addItem("Note Names");
        viewOptions.setEditable(true);
        viewOptions.addActionListener(e -> {
            if (viewOptions.getSelectedItem().toString().equals("Pitch Numbers")) {
                displayScaleDegrees();
            }
            else {
                displayNoteNames();
            }
        });
        //generate a new randomized matrix
        JButton regenerate = new JButton("New Random Matrix");
        regenerate.addActionListener(e -> {
            matrix = new Matrix(p);
            mainGrid.setVisible(false);
            mainGrid.removeAll();
            fillMainGrid(viewOptions.getSelectedItem().toString().equals("Pitch Numbers"));
            mainGrid.setVisible(true);
        });
        //start from new pitchset
        JButton restart = new JButton("Enter New Pitch Set");
        restart.addActionListener(e -> {
            StartWindow s = new StartWindow();
            dispose();
        });
        optionPanel.add(viewOptions);
        optionPanel.add(regenerate);
        optionPanel.add(restart);
        add(optionPanel);
        setVisible(true);
    }

    //uses a GridLayout to display the matrix itself (stored in grid) as well as row form labels as JButtons
    void fillMainGrid(boolean scaleDegrees) {
        int gridSize = matrix.size + 2;
        mainGrid.setLayout(new GridLayout(gridSize, gridSize));
        grid = new JLabel[matrix.size][matrix.size];
        //0, 0 position
        mainGrid.add(new JLabel(""));
        for (int j = 0; j < matrix.size; j++) {
            JButton top = new JButton("I" + matrix.board[0][j].scaleDegree);
            top.putClientProperty("row", 0);
            top.putClientProperty("col", j);
            top.setBackground(Constants.ROWBUTTON_COLOR);
            top.addActionListener(e -> {
                play(e);
            });
            mainGrid.add(top);
        }
        mainGrid.add(new JLabel(""));
        for (int i = 0; i < matrix.size; i++) {
            JButton left = new JButton("P" + matrix.board[i][0].scaleDegree);
            left.putClientProperty("row", i);
            left.putClientProperty("col", 0);
            left.setBackground(Constants.ROWBUTTON_COLOR);
            left.addActionListener(e -> {
                play(e);
            });
            mainGrid.add(left);

            //copies over grid data
            for (int j = 0; j < matrix.size; j++) {
                if (scaleDegrees) {
                    grid[i][j] = new JLabel(matrix.board[i][j].toString(), SwingConstants.CENTER);
                }
                else {
                    grid[i][j] = new JLabel(matrix.board[i][j].noteName, SwingConstants.CENTER);
                }
                grid[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
                grid[i][j].putClientProperty("row", i);
                grid[i][j].putClientProperty("col", j);
                mainGrid.add(grid[i][j]);
            }
            JButton right = new JButton("R" + matrix.board[i][matrix.size -1].toString());
            right.putClientProperty("row", i);
            right.putClientProperty("col", matrix.size - 1);
            right.setBackground(Constants.ROWBUTTON_COLOR);
            right.addActionListener(e -> {
                play(e);
            });
            mainGrid.add(right);
        }
        mainGrid.add(new JLabel(""));
        for (int j = 0; j < matrix.size; j++) {
            JButton bottom = new JButton("RI" + matrix.board[matrix.size -1][j].scaleDegree);
            bottom.putClientProperty("row", matrix.size - 1);
            bottom.putClientProperty("col", j);
            bottom.setBackground(Constants.ROWBUTTON_COLOR);
            bottom.addActionListener(e -> {
                play(e);
            });
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

    void play(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        int row = (int) source.getClientProperty("row");
        int col = (int) source.getClientProperty("col");
        JLabel notes[] = new JLabel[matrix.size];
        if (row == 0) {
            for (int i = 0; i < matrix.size; i++) {
                notes[i] = grid[i][col];
            }
        }
        else if (row == matrix.size - 1) {
            for (int i = matrix.size - 1; i >= 0; i--) {
                notes[matrix.size - 1 - i] = grid[i][col];
            }
        }
        else if (col == 0) {
            for (int j = 0; j < matrix.size; j++) {
                notes[j] = grid[row][j];
            }
        }
        else {
            for (int j = matrix.size - 1; j>=0; j--) {
                notes[matrix.size - 1 - j] = grid[row][j];
            }
        }
        for (JLabel note : notes) {
            int r = (int) note.getClientProperty("row");
            int c = (int) note.getClientProperty("col");
            int pitch = Constants.noteToMidi.get(matrix.board[r][c].noteName);
            highlight(r, c);
            playNote(pitch);
        }
    }

    public void highlight(int row, int col) {
        grid[row][col].setBackground(Constants.HIGHLIGHT_COLOR);
    }

    public void playNote(int pitch) {
        mChannels[0].noteOn(pitch, 1000);
        try {
            Thread.sleep(600);
        } catch (InterruptedException ex) {}
        mChannels[0].noteOff(pitch);
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
