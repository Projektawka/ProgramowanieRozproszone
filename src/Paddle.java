import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class Paddle {
    protected static int dy;
    protected int x = 0;
    protected int x_out = 0;
    public int y = 3;
    protected int score = 0;
    protected int w = 23;
    protected int h = 86;
    protected int up;
    protected int down;


    protected Image image;

    public Paddle() {
        loadPaddle();
    }

    private void loadPaddle() {
        ImageIcon ii = new ImageIcon("src/resources/paddle.png");
        image = ii.getImage();

        w = image.getWidth(null);
        h = image.getHeight(null);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return w;
    }

    public static int getDy() {
        return dy;
    }

    public int getHeight() {
        return h;
    }

    public int getScore() {
        return score;
    }

    public void addPoint() {
        score++;
    }

    public Image getImage() {
        return image;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
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



