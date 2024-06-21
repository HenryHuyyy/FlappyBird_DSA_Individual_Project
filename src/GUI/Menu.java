package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Menu extends JPanel implements MouseListener, MouseMotionListener {

    // Define Menu window size
    private int boardWidth = 360;
    private int boardHeight = 640;


    private Image backgroundImg;
    private Image startButtonImg;
    Sound sound = new Sound();
    private BufferedImage backgroundImage, playImage, exitImage;
    JFrame frame;
    private boolean isClicked;
    private Point mousePos = new Point(-1, -1);
    private int play, exit, state;
    private Rectangle area, area2;

    // Declare Menu constructor
    public Menu(Rectangle area, Rectangle area2, JFrame jFrame) {
        addMouseListener(this);
        addMouseMotionListener(this);
        this.area = area;
        this.frame = jFrame;
        this.area2 = area2;
        state = 1;
        play = 2;
        exit = 3;

        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/assets/menu/background-no-click.png"));
            playImage = ImageIO.read(getClass().getResourceAsStream("/assets/menu/background-click-start.png"));
            exitImage = ImageIO.read(getClass().getResourceAsStream("/assets/menu/background-click-exit.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    } //--------- end of Menu constructor ----------//

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    public void startGame() {
        frame.getContentPane().removeAll();
        FlappyBird gamePanel = new FlappyBird(boardWidth, boardHeight); // Declare FlappyBird class object to start playing
        frame.add(gamePanel);
        frame.revalidate();
        frame.repaint();
        gamePanel.requestFocusOnPanel();
    }

    public void mouseClicked(MouseEvent e) {
        // Do nothing
    }

    public void mousePressed(MouseEvent e) {
        if (area.contains(e.getPoint())) {
            handleMouseEvent(e);
        } else if (area2.contains(e.getPoint())) {
            System.exit(0);
        }
    }

    public void handleMouseEvent(MouseEvent e) {
        startGame();
    }

    public void mouseReleased(MouseEvent e) {
        isClicked = false;
        repaint();
    }

    public void mouseEntered(MouseEvent e) {
        // Do nothing
    }

    public void mouseExited(MouseEvent e) {
        // Do nothing
    }

    public void mouseMoved(MouseEvent e) {
        Point point = e.getPoint();
        if (area.contains(point)) {
            state = play;
            repaint();
        } else if (area2.contains(point)) {
            state = exit;
            repaint();
        } else {
            state = 1;
            repaint();
        }
    }

    public void mouseDragged(MouseEvent e) {
        mousePos = e.getPoint();
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
        if (state == 1) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        if (state == play) {
            g.drawImage(playImage, 0, 0, getWidth(), getHeight(), this); // Confirm pressing Start button
        }
        if (state == exit) {
            g.drawImage(exitImage, 0, 0, getWidth(), getHeight(), this); // Confirm pressing Exit button
        }
    }
}
