import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.exit;

public class Serwer {
    public static Ball ball;
    static ExecutorService pool = Executors.newFixedThreadPool(5);
    public volatile static List<Player> players = new ArrayList<>();

    public static void main(String[] args) throws Exception {


        try (ServerSocket listener = new ServerSocket(58901)) {
            System.out.println("Server is Running...");

            ball  = new Ball();
            while (true) {
                pool.execute(new Player(listener.accept(), "left"));
                pool.execute(new Player(listener.accept(), "right"));
            }
        }
    }

    static void move_ball() {
        while (true) {
            ball.move();
            try {
                Thread.currentThread().sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    static class Player implements Runnable {
        Socket socket;
        DataInputStream input;
         DataOutputStream output;
        String side;
        public Paddle paddle;
        volatile Player opponent;
        int score = 0;

        Player(Socket socket, String side) {
            players.add(this);
            this.socket = socket;
            this.side = side;
            if (side.equals("left")) {
                System.out.println(players.size()+ "rozmiar");
                paddle = new Left_Paddle(ball);
            } else {
                paddle = new Right_Paddle(ball);
                pool.execute(() -> move_ball());
            }
            ball.add_player(this);
        }

        void wait_for_the_opponent() {
            try {
                output.writeChar('S');
                output.writeUTF(side);
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (opponent == null) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Iterator<Player> iterator = players.iterator();

                //  System.out.println(players.size());
                while (iterator.hasNext()) {
                    Player tmp = iterator.next();
                    if (tmp != this) {
                        opponent = tmp;
                        iterator.remove();
                        break;
                    }
                }
            }
        }

        @Override
        public void run() {
            try {
                setup();
                System.out.println("jedziemy");
                pool.execute(this::pisz);
                pool.execute(() -> {
                    try {
                        czytaj();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            catch (Exception ex) {
                ex.printStackTrace();
            } finally {

            }
        }

        private void setup() throws IOException {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            wait_for_the_opponent();
        }

        private void czytaj() throws Exception {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(2);
                boolean done = false;
                while (!done) {
                    char messageType = input.readChar();

                    switch (messageType) {
                        case 'Y':
                            paddle.setDy(input.readByte());
                            break;
                        case 'I':
                            System.out.println("Message B: " + input.readByte());
                            ball.startMove();
                            break;

                        default:
                            done = true;
                    }
                }

              /*
                while (input.hasNextLine()) {
                    String tresc = input.nextLine();
                    // System.out.println(tresc);
                    //Paddle.setY(Integer.parseInt(tresc));
                    paddle.setDy(Integer.parseInt(tresc));
                }
                exit(0);
*/

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                input.close();
                socket.close();
                exit(0);
            }
        }

        public void pisz2(String message){
            System.out.println("czysczenie");
           // if(message.equals("Refresh")){
                try {
                    output.writeChar('R');
                } catch (IOException e) {
                    e.printStackTrace();
               // }
            }
        }

        public void pisz3(int dir){
            System.out.println("kierunkowanie");
            // if(message.equals("Refresh")){
            try {
                output.writeChar('D');
                output.writeInt(dir);
            } catch (IOException e) {
                e.printStackTrace();
                // }
            }
        }

        private  void pisz() {
            System.out.println(Thread.currentThread().getName());
            //output.println(side);
            while (true) {
                paddle.move();
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String message = ball.getX() + " " + ball.getY() + " " + paddle.getY() + " ";
                // if(this.opponent != null) {
                message += opponent.paddle.getY();
                // }
                try {
                    output.writeChar('A');
                    output.writeUTF(message);
                    output.writeChar('P');
                    String score2 = "";

                    Iterator<Player> iterator = players.iterator();
                   /* while (iterator.hasNext()) {
                        Player tmp = iterator.next();
                        score2 +=tmp.getScore();

                    }*/

                    for (Player player: ball.players) {
                        score2 += player.getScore()+ " : ";
                    }
                    score2 = score2.substring(0, score2.length() - 3);
                    output.writeUTF(score2);
                   // System.out.println(score2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //System.out.println(message + this.side);

            }

        }

        public void addPoint() {
            this.score++;
        }
        public int getScore(){
            return this.score;
        }
    }
}

