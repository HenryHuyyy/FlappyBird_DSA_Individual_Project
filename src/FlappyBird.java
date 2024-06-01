import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.Timer;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;

    //Define image variables
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //Define bird size
    int birdX = boardWidth/8;
    int birdY = boardHeight/2;
    int birdWidth = 34;
    int birdHeight = 24;



    class Bird {

        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    //Define pipes object
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;     //scale by 1/6
    int pipeHeight = 512;

    class Pipe {

        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        // Define constructor
        Pipe(Image img) {
            this.img = img;
        }
    }

    //game logic
    Bird bird;

    int velocityX = -4;
    int velocityY = 0;
    int gravity = 1;
    Sound sound = new Sound();

    ArrayList<Pipe> pipes;
    Random rand = new Random();

    Timer gameLoop;
    Timer placePipesTimer;
    boolean gameOver = false;
    double score = 0;


    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLUE);

        setFocusable(true);
        addKeyListener(this);

        //-----Load images------
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        // Bird
        bird = new Bird(birdImg);

        //Define new pipe by using ArrayList
        pipes = new ArrayList<Pipe>();

        //Place pipe timer
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });

        placePipesTimer.start();

        // Game timer
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();

        playMusic(0);
    }

    public void placePipes() {
        // (0 - 1) * pipeHeight/2 -> (0 - 256)
        // 128
        // 0 - 128 - (0 - 256) --> pipeHeight/4 -> 3/4 pipeHeight
        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = boardHeight/4;
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw(g);
        }

        public void draw(Graphics g) {
            System.out.println("draw");
            //draw background
            g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

            //draw bird
            g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

            //pipes
            for(int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width,pipe.height,null);
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 32));

            if (gameOver) {
                g.drawString("Game over " + String.valueOf((int) score), 10,35);
            }
            else {
                g.drawString(String.valueOf((int) score),10,35);
            }

        }

    public void move() {
        //bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        //pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                pipe.passed = true;
                score += 0.5; //because there are 2 pipes! Hence, 0.5*2 = 1, 1 for each set of pipes
                playSoundEffect(3);
            }

            if (collision(bird,pipe)) {
                gameOver = true;
                playSoundEffect(5);
            }
        }

        if (bird.y > boardHeight) {
            gameOver = true;
            playSoundEffect(5);
        }
    }
    public boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&                 // a's top left corner doesn't reach b's top right corner
                a.x + a.width > b.x &&          // a's top right corner passes b's top left corner
                a.y < b.y + b.height &&         // a's top left corner doesn't reach b's bottom left corner
                a.y + a.height > b.y;           // a's bottom left corner passes b's top left corner
    }

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
    }

    public void playSoundEffect(int i) {
        sound.setFile(i);
        sound.play();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if (gameOver) {
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;
            playSoundEffect(2);
            if (gameOver) {
                //restart the game by resetting condition
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipesTimer.start();
            }
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
