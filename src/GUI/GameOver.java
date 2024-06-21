package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameOver extends JPanel implements MouseListener, MouseMotionListener {
    private int boardWidth = 360;
    private int boardHeight = 640;

    private ImageIcon scoreCardImage, restartImage, exitImage;
    private JFrame frame;
    private Rectangle restartButtonBounds, exitButtonBounds;
    private boolean isRestartClicked = false;
    private boolean isExitClicked = false;

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
            FlappyBird gamePanel = new FlappyBird(boardWidth, boardHeight);
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
}




//package GUI;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//public class GameOver extends JPanel implements MouseListener, MouseMotionListener {
//    private int boardWidth = 360;
//    private int boardHeight = 640;
//
//    private ImageIcon scoreCardImage, restartImage, exitImage;
//    private JFrame frame;
//    private Rectangle restartButtonBounds, exitButtonBounds;
//    private boolean isRestartClicked = false;
//    private boolean isExitClicked = false;
//
//    public GameOver(JFrame jFrame) {
//        addMouseListener(this);
//        addMouseMotionListener(this);
//        this.frame = jFrame;
//
//        // Load images
//        scoreCardImage = new ImageIcon(getClass().getResource("/assets/gameover/ScoreCard.png"));
//        restartImage = new ImageIcon(getClass().getResource("/assets/gameover/ok.png"));
//        exitImage = new ImageIcon(getClass().getResource("/assets/gameover/exit.png"));
//
//        JLabel restartLabel = new JLabel(restartImage);
//        JLabel exitLabel = new JLabel(exitImage);
//        JLabel scoreCardLabel = new JLabel(scoreCardImage);
//        scoreCardLabel.setSize(238,120);
//        scoreCardLabel.setVisible(false);
//
//        // Create a panel with GridBagLayout
//        JPanel panel = new JPanel(new GridBagLayout());
//
//        // Create GridBagConstraints for centering
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.insets = new Insets(10, 10, 10, 10);
//        gbc.anchor = GridBagConstraints.CENTER;
//
//        // Add the JLabels to the panel
//        panel.add(scoreCardLabel, gbc);
//        gbc.gridy++;
//        panel.add(restartLabel, gbc);
//        gbc.gridy++;
//        panel.add(exitLabel, gbc);
//
//        // Add the panel to the frame
//        add(panel);
//
//
//        /*
//        // Calculate positions and sizes for buttons
//        int scoreCardWidth = scoreCardImage.getWidth();
//        int scoreCardHeight = scoreCardImage.getHeight();
//        int panelWidth = Math.max(boardWidth, scoreCardWidth); // Ensure panel is wide enough for scorecard
//        int panelHeight = Math.max(boardHeight, scoreCardHeight + restartImage.getHeight() + exitImage.getHeight());
//
//        // Center scorecard
//        int scoreCardX = (panelWidth - scoreCardWidth) / 2;
//        int scoreCardY = (panelHeight - scoreCardHeight - restartImage.getHeight() - exitImage.getHeight()) / 2;
//
//        // Set bounds for buttons
//        restartButtonBounds = new Rectangle((panelWidth - restartImage.getWidth()) / 2, scoreCardY + scoreCardHeight + 20, restartImage.getWidth(), restartImage.getHeight());
//        exitButtonBounds = new Rectangle((panelWidth - exitImage.getWidth()) / 2, scoreCardY + scoreCardHeight + restartImage.getHeight() + 40, exitImage.getWidth(), exitImage.getHeight());
//
//        setPreferredSize(new Dimension(panelWidth, panelHeight)); */
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        // Draw score card image centered
//        int scoreCardX = (getWidth() - scoreCardImage.getWidth()) / 2;
//        int scoreCardY = (getHeight() - scoreCardImage.getHeight() - restartImage.getHeight() - exitImage.getHeight()) / 2;
//        g.drawImage(scoreCardImage, scoreCardX, scoreCardY, this);
//
//        // Draw restart button
//        g.drawImage(restartImage, restartButtonBounds.x, restartButtonBounds.y, this);
//
//        // Draw exit button
//        g.drawImage(exitImage, exitButtonBounds.x, exitButtonBounds.y, this);
//    }
//
//    @Override
//    public void mouseClicked(MouseEvent e) {
//        // Check if restart button clicked
//        if (restartButtonBounds.contains(e.getPoint())) {
//            handleRestart();
//        }
//        // Check if exit button clicked
//        else if (exitButtonBounds.contains(e.getPoint())) {
//            handleExit();
//        }
//    }
//
//    private void handleRestart() {
//        frame.dispose();  // Close the current frame
//        startNewGame();   // Start a new game
//    }
//
//    private void startNewGame() {
//        try {
//            FlappyBird gamePanel = new FlappyBird(boardWidth, boardHeight);
//            frame.getContentPane().removeAll();
//            frame.add(gamePanel);
//            frame.revalidate();
//            frame.repaint();
//            gamePanel.requestFocusOnPanel();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void handleExit() {
//        System.exit(0);  // Exit the application
//    }
//
//    // Other mouse event methods (not used in this example)
//    @Override
//    public void mousePressed(MouseEvent e) {}
//    @Override
//    public void mouseReleased(MouseEvent e) {}
//    @Override
//    public void mouseEntered(MouseEvent e) {}
//    @Override
//    public void mouseExited(MouseEvent e) {}
//    @Override
//    public void mouseMoved(MouseEvent e) {}
//    @Override
//    public void mouseDragged(MouseEvent e) {}
//}



//package GUI;
//import GUI.FlappyBird;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//
//public class GameOver extends JPanel implements MouseListener, MouseMotionListener {
//    private int boardWidth = 360;
//    private int boardHeight = 640;
//
//    private BufferedImage backgroundImage, okImage,exitImage;
//    JFrame frame;
//    private boolean isClicked;
//    private Point mousePos = new Point(-1, -1);
//    private Rectangle area,area2;
//    private int play,exit,state;
//
//    public GameOver(Rectangle area, Rectangle area2,JFrame jFrame) {
//        addMouseListener(this);
//        addMouseMotionListener(this);
//        this.area = area;
//        this.frame = jFrame;
//        this.area2 = area2;
//        state = 1;
//        play = 2;
//        exit = 3;
//        // Load the background image
//        try {
//            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/assets/gameover/gameover-no-click.png"));
//            okImage =ImageIO.read(getClass().getResourceAsStream("/assets/gameover/gameover-click-ok.png"));
//            exitImage=ImageIO.read(getClass().getResourceAsStream("/assets/gameover/gameover-click-exit.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void startGame() {
//        frame.getContentPane().removeAll();
//        FlappyBird gamePanel = new FlappyBird(boardWidth, boardHeight);
//        frame.add(gamePanel);
//        frame.revalidate();
//        frame.repaint();
//        gamePanel.requestFocusOnPanel();
//    }
//
//
//    public void mouseClicked(MouseEvent e) {
//        // Do nothing
//    }
//
//    public void mousePressed(MouseEvent e) {
//        if (area.contains(e.getPoint())) {
//            handleMouseEvent(e);
//        } else if (area2.contains(e.getPoint())) {
//            System.exit(0);
//
//        }
//    }
//    public void handleMouseEvent(MouseEvent e) {
//        frame.dispose();
//        startNewGame();
//    }
//    private void startNewGame() {
//        try {
//            startGame();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public void mouseReleased(MouseEvent e) {
//        isClicked = false;
//        repaint();
//    }
//
//    public void mouseEntered(MouseEvent e) {
//        // Do nothing
//    }
//
//    public void mouseExited(MouseEvent e) {
//        // Do nothing
//    }
//
//    public void mouseMoved(MouseEvent e) {
//        Point point = e.getPoint();
//        if (area.contains(point)) {
//            state = play;
//            repaint();
//        } else if (area2.contains(point)) {
//            state = exit;
//            repaint();
//        } else{
//            state = 1;
//            repaint();
//        }
//    }
//    public void mouseDragged(MouseEvent e) {
//        mousePos = e.getPoint();
//        repaint();
//    }
//
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        // Draw the background image
//        if(state == 1) {
//            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
//        }
//        if(state == play){
//            g.drawImage(okImage, 0, 0, getWidth(), getHeight(), this);
//        }
//        if(state == exit){
//            g.drawImage(exitImage, 0, 0, getWidth(), getHeight(), this);
//        }
//    }
//}
//
