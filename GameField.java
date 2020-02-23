import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.Key;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameField extends JPanel implements ActionListener {
    private boolean inGame = true;      //нахождение в игре
    private final int SIZE = 320;       //размер поля
    private final int SNAKE_SIZE = 16;    //размер пикселя
    private final int ALL_SEGMENTS = 400;   //всего единиц на поле
    private Image snake;
    private Image food;
    private int foodX;  //позиции еды по оси Ох
    private int foodY;  //позиции еды по оси Оу
    private int snakeSize;
    private int[] x = new int[ALL_SEGMENTS];    //позиции для змейки по оси Ох
    private int[] y = new int[ALL_SEGMENTS];    //позиции для змейки по оси Оу
    private Timer timer;
    private boolean left = false;
    private boolean right = true;   //первоначальная позиция змейки
    private boolean down = false;
    private boolean up = false;
    final Random random = new Random();
    private JTextField textField;
    JButton playGame = new JButton("Play");


    public GameField() {
        setBackground(Color.pink);
        downloadImage();
        Game();
        addKeyListener(new SnakeKeyListener());
        setFocusable(true); //соединение с полем
    }

    public void downloadImage() {
        ImageIcon snakeIcon = new ImageIcon("src/snake.png");
        snake = snakeIcon.getImage();
        ImageIcon foodIcon = new ImageIcon("src/bee.png");
        food = foodIcon.getImage();
    }

    public void createFood() {
        foodX = random.nextInt(20) * SNAKE_SIZE;
        foodY = random.nextInt(20) * SNAKE_SIZE;
    }

    public void Game() {
        timer = new Timer(250, this);
        timer.start();
        snakeSize = 3;   //начальная длина змейки
        for (int i = 0; i < snakeSize; i++) {
            x[i] = 48 - i * SNAKE_SIZE;
            y[i] = 48;
        }
        createFood();
    }

    public void checkFood() {
        if (x[0] == foodX && y[0] == foodY) {
            snakeSize++;
            createFood();
        }
    }

    public void move() {
        for (int i = snakeSize; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        direction();
    }

    public void direction() {
        if (up) y[0] -= SNAKE_SIZE;
        if (right) x[0] += SNAKE_SIZE;
        if (down) y[0] += SNAKE_SIZE;
        if (left) x[0] -= SNAKE_SIZE;
    }

    public void checkSnakeSize() {
        for (int i = snakeSize; i > 0; i--) if (i > 4 && x[0] == x[i] && y[0] == y[i]) inGame = false;
    }

    public void checkField() {
        if (x[0] < 0) inGame = false;
        if (x[0] > SIZE) inGame = false;
        if (y[0] < 0) inGame = false;
        if (y[0] > SIZE) inGame = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);    //перерисовка всего поля
        if (inGame) {
            g.drawImage(food, foodX, foodY, this);
            for (int i = 0; i < snakeSize; i++) {
                g.drawImage(snake, x[i], y[i], this);
            }
        } else {
            //GAME OVER
            String over = "GAME OVER";
            Font font = new Font("Mongolian Baiti", Font.BOLD, 20);
            g.setColor(Color.DARK_GRAY);
            g.setFont(font);
            g.drawString(over, 130, SIZE / 2);
        }
    }

    /*public void PlayGame() {
        playGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inGame = true;
            }
        });
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkFood();
            checkSnakeSize();
            checkField();
            move();
        }
        repaint();
    }

    class SnakeKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_UP && !down) {
                up = true;
                right = false;
                left = false;
            }

            if (key == KeyEvent.VK_DOWN && !up) {
                down = true;
                right = false;
                left = false;
            }

            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
        }
    }
}
