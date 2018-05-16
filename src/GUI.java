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
            midiSynth.loadInstrument(instr[78]);
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
            top.setBackground(Constants.ROWBUTTON_COLOR);
            String[] notes = new String[matrix.size];
            for (int i = 0; i < matrix.size; i++) {
                notes[i] = matrix.board[i][j].noteName;
            }
            top.addActionListener(e -> {
                playRow(notes);
            });
            mainGrid.add(top);
        }
        mainGrid.add(new JLabel(""));
        for (int i = 0; i < matrix.size; i++) {
            JButton left = new JButton("P" + matrix.board[i][0].scaleDegree);
            left.setBackground(Constants.ROWBUTTON_COLOR);
            String notes[] = new String[matrix.size];
            for (int j = 0; j < matrix.size; j++) {
                notes[j] = matrix.board[i][j].noteName;
            }
            left.addActionListener(e -> {
                playRow(notes);
            });
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
            right.setBackground(Constants.ROWBUTTON_COLOR);
            String notes2[] = new String[matrix.size];
            for (int j = matrix.size - 1; j >= 0; j--) {
                notes2[matrix.size - 1 - j] = matrix.board[i][j].noteName;
            }
            right.addActionListener(e -> {
                playRow(notes2);
            });
            mainGrid.add(right);
        }
        mainGrid.add(new JLabel(""));
        for (int j = 0; j < matrix.size; j++) {
            JButton bottom = new JButton("RI" + matrix.board[matrix.size -1][j].scaleDegree);
            bottom.setBackground(Constants.ROWBUTTON_COLOR);
            String notes[] = new String[matrix.size];
            for (int i = matrix.size - 1; i >= 0; i--) {
                notes[matrix.size - 1 - i] = matrix.board[i][j].noteName;
            }
            bottom.addActionListener(e -> {
                playRow(notes);
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

    void playRow(String[] row) {
        //System.out.println("PLAYING: " + Arrays.toString(row));
        for (String r : row) {
            int note = Constants.noteToMidi.get(r);
            mChannels[0].noteOn(note, 1000);
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                mChannels[0].noteOff(note);
            }
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
