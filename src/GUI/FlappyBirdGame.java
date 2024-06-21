package GUI;

import javax.swing.*;
import java.awt.*;

public class FlappyBirdGame extends JFrame {
    public FlappyBirdGame() {
        Menu panel = new Menu( new Rectangle(100,290,160,56), new Rectangle(120,370,105,56),this);
        panel.setPreferredSize(new Dimension(360,640));

        setVisible(true);
        add(panel, BorderLayout.CENTER);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

}
