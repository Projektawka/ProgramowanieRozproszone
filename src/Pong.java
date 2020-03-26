import javax.swing.*;
import java.awt.*;

public class Pong extends JFrame{
    public static int width = 640;
    public static int height = 550;
    private Board board;

    public Pong() {
        initUI();
    }

    private void initUI() {

        board = new Board();
        add(board);

        setSize(width, height);
        setResizable(false);

        setTitle("Pong");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Board get_instance_Board(){
        return board;
    }

    public static void main(String[] args){

        EventQueue.invokeLater(() -> {
           Pong ex = new Pong();
            ex.setVisible(true);
        });


    }
}
