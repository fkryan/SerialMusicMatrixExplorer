import javax.swing.*;
import java.awt.*;
import java.util.StringTokenizer;

public class StartWindow extends JFrame {

    public StartWindow() {
        super("Create Pitch Set");
        setSize(600, 120);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel directions1 = new JLabel("Enter up to 12 note names separated by spaces with up to one sharp or flat");
        add(directions1);
        //JLabel directions2 = new JLabel("Sample Input: C C# D Eb E F F# G Ab A Bb B");
        //add(directions2);
        JTextField input = new JTextField("C C# D Eb E F F# G Ab A Bb B", 20);
        input.setHorizontalAlignment(JTextField.CENTER);
        input.addActionListener(e -> {
            PitchSet p = new PitchSet();
            StringTokenizer token = new StringTokenizer(input.getText());
            while (token.hasMoreTokens()) {
                p.addNote(token.nextToken(" "));
            }
            GUI g = new GUI(p);
            dispose();
        });
        add(input);
        setVisible(true);
    }

    public static void main(String[] args) {
        StartWindow s = new StartWindow();
    }
}
