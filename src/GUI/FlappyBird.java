package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.awt.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.Timer;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {

    // Declare main gameplay window size
    int boardWidth = 360;
    int boardHeight = 640;
    JLabel scoreLabel;
    JPanel scoreTablePanel;
    JButton restartButton;

    //Define image variables
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;
    Image scoreCardImg;
    Image restartButtonImg;

    //Define bird size
    int birdX = boardWidth / 8;
    int birdY = boardHeight / 2;
    int birdWidth = 34;
    int birdHeight = 24;

    // Declare inner class Bird
    public class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    //Define pipes variables
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64; //scale by 1/6
    int pipeHeight = 512;

    //Define inner class Pipe
    public class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        // Define Pipe constructor
        Pipe(Image img) {
            this.img = img;
        }
    }

    //Declare Bird object from inner class Bird
    Bird bird;

    int velocityX = -4;
    int velocityY = 0;
    int gravity = 1;

    Sound sound = new Sound(); // Call the class Sound to add sound & music to the game

    LinkedList<Pipe> pipes = new LinkedList<Pipe>(); // Declare Data Structure "Linked List" for generating pipes

    Random rand = new Random();

    Timer gameLoop;
    Timer placePipesTimer;
    static boolean gameOver = false; // Set gameOver as default
    static boolean falling = false;
    double score = 0;

    // ScoreManager instance
    ScoreManager scoreManager;
    String currentPlayer = "Player1"; // This could be set dynamically

    // Declare main FlappyBird constructor
    public FlappyBird(int boardWidth, int boardHeight) {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLUE);
        setFocusable(true);
        addKeyListener(this);
        requestFocus();

        pipes = new LinkedList<Pipe>(); // Declare Data Structure "Linked List" for generating pipes

        // Initialize ScoreManager
        scoreManager = new ScoreManager();

        //-----Load elements------
        backgroundImg = new ImageIcon(getClass().getResource("/assets/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("/assets/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("/assets/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("/assets/bottompipe.png")).getImage();
        scoreCardImg = new ImageIcon(getClass().getResource("/assets/gameover/ScoreCard.png")).getImage();
        restartButtonImg = new ImageIcon(getClass().getResource("/assets/OK.png")).getImage();

        // Load bird as main character
        bird = new Bird(birdImg);



        //Place pipe timer
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });

        placePipesTimer.start();

        // Game timer
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();

        //Attempt to play background music after starting / restart the game
        playMusic(0);

    } // end of Flappy Bird constructor

    // Method to place couples of top-pipes - bottom-pipes continuously
    public void placePipes() {
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardHeight / 4; //Default value of openingSpace between pipes

        // Condition for increasing difficulty if player's score > 15, which will decrease the value of openingSpace
        // between pipes.
        if (score > 15) {
            openingSpace = boardHeight / 5;
        }
        // Draw the top-pipe image
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        // Draw the bottom-pipe image
        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    } //------ end of placePipes() ---------//

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // Render elements using Graphics object
    public void draw(Graphics g) {
        //Draw background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        //draw bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        //pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 32));

        if (gameOver) {
            g.drawString("Game over " + String.valueOf((int) score), 10, 35);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    } //------ end of draw() ---------//

    // Movement logic and score increment
    public void move() {
        // Bird movement
        if (falling || gameOver) {
            velocityY += gravity;
            bird.y += velocityY;
            bird.y = Math.max(bird.y, 0);
        } else {
            velocityY += gravity;
            bird.y += velocityY;
            bird.y = Math.max(bird.y, 0);
        }

        // pipes movement
        for (Pipe pipe : pipes) {
            pipe.x += velocityX;

            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                pipe.passed = true;
                score += 0.5; // because there are 2 pipes. Hence, 0.5*2 = 1, 1 for each set of pipes
                playSoundEffect(3); // call the sound effect if the player get one more scores.
            }

            if (!falling && collision(bird, pipe)) {
                gameOver = true;
                falling = true;
                playSoundEffect(5);
            }
        }

        if (bird.y > boardHeight) {
            gameOver = true;
            falling = false;
            playSoundEffect(5);
        }
    } //------- end of move() ---------//

    //Method for collision detection
    public boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&                 // a's top left corner doesn't reach b's top right corner
                a.x + a.width > b.x &&          // a's top right corner passes b's top left corner
                a.y < b.y + b.height &&         // a's top left corner doesn't reach b's bottom left corner
                a.y + a.height > b.y;           // a's bottom left corner passes b's top left corner
    } // ----- end of collision() ---------//

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
    }

    // Method to play sound effect for every movement of the bird
    public void playSoundEffect(int i) {
        sound.setFile(i);
        sound.play();
    } // ------- end of playSoundEffect() ---------//

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
            repaint();
        } else {
            placePipesTimer.stop();
            gameLoop.stop();
            showGameOverScreen();
            scoreManager.updatePlayerScore(currentPlayer, (int) score);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            if (!gameOver) {
                velocityY = -10;
                playSoundEffect(2);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //Not used
    }

    public void requestFocusOnPanel() {
        requestFocusInWindow();
    }

    // Show game over screen method
    private void showGameOverScreen() {
        JFrame gameOverFrame = new JFrame();
        gameOverFrame.setSize(boardWidth, boardHeight);
        gameOverFrame.setTitle("Game Over");

        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new BorderLayout());

        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gameOverPanel.add(gameOverLabel, BorderLayout.NORTH);

        JLabel scoreLabel = new JLabel("Score: " + (int) score, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gameOverPanel.add(scoreLabel, BorderLayout.CENTER);

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameOverFrame.dispose();
                restartGame();
            }
        });
        gameOverPanel.add(restartButton, BorderLayout.SOUTH);

        gameOverFrame.add(gameOverPanel);
        gameOverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameOverFrame.setVisible(true);
    } //end of showGameOverScreen()

    //Restart game by stopping the sound effect and setting the initial elements
    private void restartGame() {
        stopMusic();
        gameOver = false;
        falling = false;
        bird.x = birdX;
        bird.y = birdY;
        pipes.clear();
        velocityY = 0;
        score = 0;
        placePipesTimer.start();
        gameLoop.start();
    } // end of restartGame()

} // end of class FlappyBird

