package tank;

import java.awt.*;

public class Explosion {
    public static int WIDTH = ResourceManager.explosions[0].getWidth();
    public static int HEIGHT = ResourceManager.explosions[0].getHeight();

    private int x, y;

    TankFrame tf = null;

    private int step = 0; //there are three steps of the explosion

    public Explosion(int x, int y, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.tf = tf;
    }

    public void paint(Graphics g) {
        g.drawImage(ResourceManager.explosions[step++], x, y, null);

        if (step >= ResourceManager.explosions.length) {
            tf.explosions.remove(this);
        }
    }

}
