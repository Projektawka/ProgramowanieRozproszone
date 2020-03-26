import java.awt.event.KeyEvent;

public class Left_Paddle extends Paddle {

    Left_Paddle(Ball ball){
        super(ball);
        this.x = 0;
        this.x_out = w;
        this.up = KeyEvent.VK_UP;
        this.down = KeyEvent.VK_DOWN;
    }


    @Override
    public void move() {
        if((y>0 && dy<0)||(y+h<height-40-4 && dy>0) ) {
            y += 2*dy;
                if(dy<0){
                    if(( ball.x <= this.x_out) && (this.y<= ball.y+ Ball.getHeight() && this.y+h>= ball.y)){
                        ball.y-=2;
                    }
                }
            if(dy>0){
                if(( ball.x <= this.x_out) && (this.y<= ball.y+ Ball.getHeight() && this.y+h>= ball.y)){
                    ball.y+=2;
                }
            }
        }
    }

    @Override
    public int check_collison(int x, int y){
      //  System.out.println("lewa");
        if((x == this.x_out) && (this.y<y+ Ball.getHeight() && this.y+h>y))return 1;
        if((x <= this.x_out) && (this.y == y+ Ball.getHeight() || this.y+h == y))return 2;
        return 0;
    }
}
