import java.awt.event.KeyEvent;

public class Left_Paddle extends Paddle {

    Left_Paddle(){
        super();
        this.x = 0;
        this.x_out = w;
        this.up = KeyEvent.VK_UP;
        this.down = KeyEvent.VK_DOWN;
    }
}
