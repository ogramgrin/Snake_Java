import javax.swing.*;

public class GameWindow extends JFrame{

    public GameWindow() {
        setTitle("Snake");
        setBounds(320, 345, 400, 400 );
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        GameWindow window = new GameWindow();
    }
}
