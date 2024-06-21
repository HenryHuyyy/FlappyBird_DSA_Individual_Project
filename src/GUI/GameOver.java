package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameOver extends JPanel implements MouseListener, MouseMotionListener {

    // Define GameOver window size
    private int boardWidth = 360;
    private int boardHeight = 640;

    // Define elements
    private ImageIcon scoreCardImage, restartImage, exitImage;
    private JFrame frame;
    private Rectangle restartButtonBounds, exitButtonBounds;
    private boolean isRestartClicked = false;
    private boolean isExitClicked = false;

    //Define GameOver constructor
    public GameOver(JFrame jFrame) {
        addMouseListener(this);
        addMouseMotionListener(this);
        this.frame = jFrame;

        // Load images
        scoreCardImage = new ImageIcon(getClass().getResource("/assets/gameover/ScoreCard.png"));
        restartImage = new ImageIcon(getClass().getResource("/assets/gameover/ok.png"));
        exitImage = new ImageIcon(getClass().getResource("/assets/gameover/exit.png"));

        JLabel restartLabel = new JLabel(restartImage);
        JLabel exitLabel = new JLabel(exitImage);
        JLabel scoreCardLabel = new JLabel(scoreCardImage);
        scoreCardLabel.setSize(238,120);
        scoreCardLabel.setVisible(false);

        // Create a panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());

        // Create GridBagConstraints for centering
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Add the JLabels to the panel
        panel.add(scoreCardLabel, gbc);
        gbc.gridy++;
        panel.add(restartLabel, gbc);
        gbc.gridy++;
        panel.add(exitLabel, gbc);

        // Add the panel to the frame
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        // Set bounds for buttons
        restartButtonBounds = new Rectangle((boardWidth - restartImage.getIconWidth()) / 2, (boardHeight + scoreCardImage.getIconHeight()) / 2 + 20, restartImage.getIconWidth(), restartImage.getIconHeight());
        exitButtonBounds = new Rectangle((boardWidth - exitImage.getIconWidth()) / 2, (boardHeight + scoreCardImage.getIconHeight()) / 2 + restartImage.getIconHeight() + 40, exitImage.getIconWidth(), exitImage.getIconHeight());

        setPreferredSize(new Dimension(boardWidth, boardHeight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw scorecard image centered
        int scoreCardX = (getWidth() - scoreCardImage.getIconWidth()) / 2;
        int scoreCardY = (getHeight() - scoreCardImage.getIconHeight() - restartImage.getIconHeight() - exitImage.getIconHeight()) / 2;
        g.drawImage(scoreCardImage.getImage(), scoreCardX, scoreCardY, this);

        // Update button bounds
        restartButtonBounds.setLocation((getWidth() - restartImage.getIconWidth()) / 2, scoreCardY + scoreCardImage.getIconHeight() + 20);
        exitButtonBounds.setLocation((getWidth() - exitImage.getIconWidth()) / 2, restartButtonBounds.y + restartImage.getIconHeight() + 20);

        // Draw restart button
        g.drawImage(restartImage.getImage(), restartButtonBounds.x, restartButtonBounds.y, this);

        // Draw exit button
        g.drawImage(exitImage.getImage(), exitButtonBounds.x, exitButtonBounds.y, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Check if restart button clicked
        if (restartButtonBounds.contains(e.getPoint())) {
            handleRestart();
        }
        // Check if exit button clicked
        else if (exitButtonBounds.contains(e.getPoint())) {
            handleExit();
        }
    }

    private void handleRestart() {
        frame.dispose();  // Close the current frame
        startNewGame();   // Start a new game
    }

    private void startNewGame() {
        try {
            FlappyBird gamePanel = new FlappyBird(boardWidth, boardHeight); // Declare new FlappyBird class object if restart the game
            frame.getContentPane().removeAll();
            frame.add(gamePanel);
            frame.revalidate();
            frame.repaint();
            gamePanel.requestFocusOnPanel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleExit() {
        System.exit(0);  // Exit the application
    }

    // Other mouse event methods (not used in this example)
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
} //--------- end of GameOver class -----------//

