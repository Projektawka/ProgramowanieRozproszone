import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class Paddle {
    protected int dy;
    protected int x = 0;
    protected int x_out = 0;
    protected int y = 3;
    protected int score = 0;
    protected int w=23;
    protected int h=86;
    protected int up;
    protected int down;
    protected  int width = 640;
    protected  int height = 550;
    protected  Ball ball;


    protected Image image;

    public Paddle(Ball ball){
        this.ball= ball;
    }


    public abstract void move();

    public abstract int check_collison(int x, int y);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public  void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {

        return h;
    }

    public int getScore() {

        return score;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void addPoint(){
        score++;
    }

    public Image getImage() {

        return image;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == up) {
            dy = -1;
        }
        if (key == down) {
            dy = 1;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == up) {
            dy = 0;
        }
        if (key == down) {
            dy = 0;
        }
    }
}



