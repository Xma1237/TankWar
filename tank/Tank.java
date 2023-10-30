package tank;

import java.awt.*;
import java.util.Random;

public class Tank {
    private int x, y;
    private Dir dir = Dir.DOWN;
    private static final int SPEED = 5;
    public static final int WIDTH = ResourceManager.GOODtankD.getWidth();
    public static final int HEIGHT = ResourceManager.GOODtankD.getHeight();
    private boolean living = true;
    private TankTeam team;//used for separate teams
    private Random random = new Random();//used for enemy tank's moving direction

    //need this TankFrame reference, so the tank can put
    //a bullet object into the TankFrame
    private TankFrame tf = null;

    //when the tank is not moving
    private boolean moving = true;

    public Tank(int x, int y, Dir dir, TankTeam team, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.team = team;
        this.tf = tf;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
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

    public TankTeam getTeam() {
        return team;
    }

    public void setTeam(TankTeam team) {
        this.team = team;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    // method to draw the tank
    public void paint(Graphics g) {
        /* Color c = g.getColor();
        g.setColor(Color.GREEN);
        g.fillRect(x, y, 50, 50);
        g.setColor(c); */
        if (!living) {//check living
            this.tf.enemyTank.remove(this);
        }

        switch (dir) {//draw the tank according to tank's dir
            case LEFT:
                g.drawImage((this.team == TankTeam.GOOD ? ResourceManager.GOODtankL : ResourceManager.BADtankL), x, y, null);
                break;
            case RIGHT:
                g.drawImage((this.team == TankTeam.GOOD ? ResourceManager.GOODtankR : ResourceManager.BADtankR), x, y, null);
                break;
            case UP:
                g.drawImage((this.team == TankTeam.GOOD ? ResourceManager.GOODtankU : ResourceManager.BADtankU), x, y, null);
                break;
            case DOWN:
                g.drawImage((this.team == TankTeam.GOOD ? ResourceManager.GOODtankD : ResourceManager.BADtankD), x, y, null);
                break;
        }

        move();
    }

    private void move() {
        if (!moving) return;

        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
        }

        if (this.team == TankTeam.BAD && random.nextInt(10) > 8) { //random firing
            this.fire();
        }

        if (this.team == TankTeam.BAD && random.nextInt(100) > 95) {
            randomDir();
        }

        //check if tank moves out of bound
        boundsCheck();
    }

    private void boundsCheck() {
        if (this.x < 2) x = 0;
        if (this.y < 28) y = 30;
        if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH - 2) x = TankFrame.GAME_WIDTH - Tank.WIDTH - 2;
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2) y = TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2;
    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    //fire bullet method
    public void fire() {
        int bX = this.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = this.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        tf.bullets.add(new Bullet(bX, bY, this.dir, this.team, tf));
    }

    public void die() {
        this.living = false;
    }
}
