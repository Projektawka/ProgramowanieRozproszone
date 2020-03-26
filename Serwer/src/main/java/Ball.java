import java.util.ArrayList;
import java.util.List;

public class Ball {
    public int width = 640;
    public int height = 550;
    public int x = width / 2 - 20;
    public int y = height / 2 - 40;
    public int ver = 0;
    public int hor = 0;
    public int isStopped = 1;
    private static int w = 23;
    private static int h = 23;
    private int direction = 0;
    public List<Serwer.Player> players = new ArrayList<>();

    public Ball() {
        setPosition();
    }

    public void startMove() {
        isStopped = 0;
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
    }

    public void add_player(Serwer.Player player) {
        this.players.add(player);
        System.out.println("pomyslnie dodano do listy");
    }


    public void move() {
        if (isStopped == 0) {
            //    System.out.println(x + " "+ y);
           // System.out.println(Serwer.players.size());
            int flag = 0;

            for (Serwer.Player player : players) {
                if (player.paddle.check_collison(x, y) == 1) {
                    flag = 1;
                    System.out.println("kolizja pionowa");
                }
                if (player.paddle.check_collison(x, y) == 2) {
                    flag = 2;
                    System.out.println("kolizja pozioma");
                }
            }
            if (flag == 1) {
                hor *= -1;
                //ver*=-1;
            }
            if (flag == 2) {
                // hor*=-1;
                ver *= -1;
            }

            if (hor == 1) {
                if (x == width - 20 - w) {
                    hor *= -1;
                    //System.out.println("prawa sciana");
                    players.get(0).addPoint();
                    stopping_move((int) (Math.random() * 2) + 2);
                    this.refresh();
                } else
                    x++;
            }
            if (hor == -1) {
                if (x == 0) {
                    System.out.println("lewa sciana");
                    hor *= -1;
                    players.get(1).addPoint();
                     stopping_move((int) (Math.random()*2));
                    this.refresh();
                } else
                    x--;
            }
            if (ver == 1) {
                if (y == height - 44 - h) {
                    ver = -1;
                } else
                    y++;
            }
            if (ver == -1) {
                if (y == 0) {
                    ver = 1;
                } else
                    y--;
            }

        }
    }

    private void refresh(){
        players.get(0).pisz2("Refresh");
        players.get(1).pisz2("Refresh");
    }

    private void next_dir(int dir){
        players.get(0).pisz3(dir);
        players.get(1).pisz3(dir);
    }


    private void setPosition() {
        x = width / 2 - 20;
        y = height / 2 - 40;
    }

    public void stopping_move(int direction) {
        ver = 0;
        hor = 0;
        setPosition();
        this.isStopped = 1;
        next_dir(direction);
        this.direction = direction;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
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

    public static int getHeight() {

        return h;
    }
}
