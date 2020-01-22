import org.json.simple.parser.ParseException;
import visualization.Game;

import javax.swing.*;
import java.io.IOException;


public class Main{

    public static void main(String[] args) throws IOException, ParseException {
        // write your code here
        JFrame.setDefaultLookAndFeelDecorated(false);

        //Create and set up the window.
        JFrame frame = new JFrame("2048");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new Game();
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }
}

