import java.awt.event.KeyEvent;

public class Right_Paddle extends Paddle {

    Right_Paddle() {
        super();
        this.x = Pong.width -17- this.w;
        this.x_out = x;
        this.up = KeyEvent.VK_W;
        this.down = KeyEvent.VK_S;
    }
}
