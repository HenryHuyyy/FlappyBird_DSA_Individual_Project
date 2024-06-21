package GUI;

import javax.swing.*;
import java.awt.*;

public class Restart extends JFrame {

    // Restart constructor
    public Restart() {
// Create the GameOver panel
        GameOver panel = new GameOver(this);

        // Set the preferred size of the panel
        panel.setPreferredSize(new Dimension(960, 640));

        // Add the GameOver panel to the frame
        add(panel, BorderLayout.CENTER);

        // Set frame properties
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack(); // Pack the frame to fit the preferred size of the panel
    }
}
