import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Ball ball;
    private Paddle paddle;
    private Paddle opponent;
    private Graphics2D g2d = null;
    private int DELAY = 2;
    private boolean is_stopped = true;
    public static int next_dir = 0;

    public Board() {
        initBoard();
    }

    public void refresh() {
        System.out.println("myju myju");
        repaint(0, 0, 700, 700);
        repaint(0, 0, 700, 700); ///2 razy i nadal nie dziala
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        if (Klient.side.equals("left")) {
            paddle = new Left_Paddle();
            opponent = new Right_Paddle();
        } else if (Klient.side.equals("right")) {
            paddle = new Right_Paddle();
            opponent = new Left_Paddle();
        }
        /*  paddles.add( new Right_Paddle());*/
        ball = new Ball(this);

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }


    private void doDrawing(Graphics g) {

        g2d = (Graphics2D) g;
        g2d.setFont(new Font("TimesRoman", Font.BOLD, 50));

        draw_scoreboard();

        if (is_stopped) draw_arrow();


        g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(), this);
        //  int radius = 10;
        //  g.fillOval(ball.getX() - radius, ball.getY() - radius, radius, radius);


        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(), this);
        g2d.drawImage(opponent.getImage(), opponent.getX(), opponent.getY(), this);

    }

    private void draw_scoreboard() {
        String scoreboard = Klient.score;

        if(scoreboard != null) {
            g2d.drawString(scoreboard, Pong.width / 2 - 55, 60);
        }
        g2d.setColor(Color.white);
        String court = "_____________________________";
        // g2d.drawString(court, 0, -5);
        g2d.drawString(court, 0, Pong.height - 48);
        g2d.setColor(Color.darkGray);
    }

    private void draw_arrow() {
        String arrow = "\u2b67";
        int x = Pong.width / 2 - 20;
        int y = Pong.height / 2 - 20;
        switch (next_dir) {
            case 1:
                arrow = "\u2b68";
                y += 20;
                break;
            case 2:
                arrow = "\u2b69";
                x -= 20;
                y += 20;
                break;
            case 3:
                arrow = "\u2b66";
                x -= 20;
                break;
        }
        g2d.drawString(arrow, x, y);
    }

    public void SetYplayer(int y) {
        paddle.setY(y);
    }

    public void SetYoponent(int y) {
        opponent.setY(y);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        step();
    }

    private void step() {

        repaint(paddle.getX(), paddle.getY() - 5,
                paddle.getWidth(), paddle.getHeight() + 20);
        repaint(opponent.getX(), opponent.getY() - 5,
                opponent.getWidth(), opponent.getHeight() + 20);

        //ball.move();
        repaint(ball.getX() - 4, ball.getY() - 5,
                ball.getWidth() + 20, ball.getHeight() + 20);
    }

    public void startGame() throws IOException {
        Klient.start_move_ball();
    }

    public void stopGame(int directory) {
        is_stopped = true;
        next_dir = directory;

    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {
                try {
                    startGame();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return;
            }

            paddle.keyReleased(e);

        }

        @Override
        public void keyPressed(KeyEvent e) {
            ball.keyPressed(e);
            paddle.keyPressed(e);

        }
    }
}

