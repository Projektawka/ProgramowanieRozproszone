import java.awt.event.KeyEvent;

public class Right_Paddle extends Paddle {

    Right_Paddle(Ball ball){
        super(ball);
        this.x = width-17-this.w;
        this.x_out = x;
        this.up = KeyEvent.VK_W;
        this.down = KeyEvent.VK_S;
    }

    @Override
    public void move() {

        if((y>0 && dy<0)||(y+h<height-40-4 && dy>0) ) {
            y += 2*dy;
            if(dy<0){
                if(( ball.x+ ball.getWidth() >= this.x_out) && (this.y<= ball.y+ Ball.getHeight() && this.y+h>= ball.y)){
                    ball.y-=2;
                }
            }
            if(dy>0){
                if(( ball.x+ ball.getWidth() >= this.x_out) && (this.y<= ball.y+ Ball.getHeight() && this.y+h>= ball.y)){
                    ball.y+=2;
                }
            }
        }

    }
    @Override
    public int check_collison(int x, int y){
       // System.out.println("prawa");
        if((x+ ball.getWidth() == this.x_out) && (this.y<y+ Ball.getHeight() && this.y+h>y))return 1;
        if((x+ ball.getWidth() >= this.x_out) && (this.y==y+ Ball.getHeight() || this.y+h==y))return 2;
        return 0;
    }
}
