import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class Ball {
    private int dx;
    private int dy;
    public static int x; //= Pong.width/2-20;
    public static int y; //= Pong.height/2-40;
    private static int w;
    private static int h;
    private int ver = 0;
    private int hor = 0;
    private int direction = 0;
    private Board board = null;
    private Image image;
  //  private List<Paddle> paddles;

    public Ball( Board board){
       // this.paddles=paddles;
        this.board = board;
        loadBall();
        setPosition();

    }

    private void setPosition(){
        x = Pong.width/2-20;
        y = Pong.height/2-40;
    }

    private void loadBall() {
        ImageIcon ii = new ImageIcon("src/resources/ball.png");
        image = ii.getImage();

        w = image.getWidth(null);
        h = image.getHeight(null);
    }

    public void start_move(){
        switch (direction){
            case 0:
                ver = -1;
                hor = 1;
                break;
            case 1:
                ver = 1;
                hor = 1;
                break;
            case 2:
                ver = 1;
                hor = -1;
                break;
            case 3:
                ver = -1;
                hor = -1;
                break;
        }

        setPosition();

    }

    public void stopping_move(int direction){
        ver = 0;
        hor = 0;
        setPosition();
        board.stopGame(direction);
        this.direction = direction;
    }

    public void move() {

    }


    public int getX() {

        return x;
    }

    public int getY() {

        return y;
    }

    public static int getWidth() {

        return w;
    }

    public static void setX(int x) {
        Ball.x = x;
    }

    public static void setY(int y) {
        Ball.y = y;
    }

    public static int getHeight() {

        return h;
    }

    public Image getImage() {

        return image;
    }

    public void bigger(){
        System.out.println("make bigger");
        System.out.println(w);

        Image newImage = image.getScaledInstance(40, 40, Image.SCALE_FAST);
        image = newImage;
        w = 40;
        System.out.println(w);
        h = 40;

    }

    public void smaller(){
        System.out.println("make bigger");
        System.out.println(w);

        Image newImage = image.getScaledInstance(23, 23, Image.SCALE_FAST);
        image = newImage;
        w = 23;
        System.out.println(w);
        h = 23;

    }


    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_C) {
            bigger();
        }
        if (key == KeyEvent.VK_V) {
            smaller();
        }

    }

}



