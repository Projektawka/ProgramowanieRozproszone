import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Klient extends JFrame {

    private Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;
    public static String side = "left";
    public static String score = "WAIT!";
    static Pong ex;
    static Board board;

    public Klient(String serverAddress) throws Exception {
        socket = new Socket(serverAddress, 58901);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    public static void read_side() {
        try {
            char messageType = in.readChar();
            if (messageType == 'S') side = in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        if (in.hasNextLine()) {
            String response = in.nextLine();
            side = response;
            System.out.println(side);
        } else {
            System.out.println("serwer nic nie przyslal");
        }*/
    }

    public void czytaj() throws Exception {
        System.out.println(Thread.currentThread().getName());
        // while (true){
        try {
            String response;
            while (true) {
                char messageType = in.readChar();
                switch (messageType) {
                    case 'A':
                        response = in.readUTF();
                        extract_cords(response);
                        break;
                    case 'R':
                        board.refresh();
                        System.out.println("moze chociaz tu");
                        break;
                    case 'P':
                        score = in.readUTF();
                        //System.out.println(score);
                        break;
                    case 'D':
                        Board.next_dir = in.readInt();
                        //System.out.println(score);
                        break;

                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            socket.close();
            System.out.println("konczymy");
        }
        //}
    }

    public void extract_cords(String response) {
        Matcher m = Pattern.compile("\\d+").matcher(response);
        List<Integer> numbers = new ArrayList<Integer>();
        while (m.find()) {
            numbers.add(Integer.parseInt(m.group()));
        }
        if (numbers.size() > 0) {
            Ball.setX(numbers.get(0));
            Ball.setY(numbers.get(1));
            if (board != null) {
                board.SetYplayer(numbers.get(2));
                if (numbers.size() > 3)
                    board.SetYoponent(numbers.get(3));
            }
            //  System.out.println(numbers);
        }
    }

    public void pisz() throws IOException {
        System.out.println(Thread.currentThread().getName());
        while (true) {
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.writeChar('Y');  ///y jak polozenie w osi
            out.writeByte(Paddle.getDy());
        }
    }

    public static void start_move_ball() throws IOException {
        out.writeChar('I');    //i jak instrukcje
        out.writeByte(1);
    }

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(4);

        try {
            Klient klient = new Klient("localhost");

            read_side();

            executor.execute(() -> {
                try {
                    klient.pisz();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


            executor.execute(() -> {
                try {
                    klient.czytaj();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        EventQueue.invokeLater(() -> {
            ex = new Pong();
            ex.setVisible(true);
            board = ex.get_instance_Board();
        });

    }
}
