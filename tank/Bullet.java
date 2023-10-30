package tank;

import java.awt.*;

public class Bullet {
    private static final int SPEED = 25;
    public static final int WIDTH = 15, HEIGHT = 15;
    private int x, y;
    private Dir dir;
    private boolean living = true;
    private TankTeam team = TankTeam.BAD;
    TankFrame tf = null;

    public Bullet(int x, int y, Dir dir, TankTeam team, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.team = team;
        this.tf = tf;
    }

    public TankTeam getTeam() {
        return team;
    }

    public void setTeam(TankTeam team) {
        this.team = team;
    }

    public void paint(Graphics g) {
        if (!living) {
            tf.bullets.remove(this);
        }

        /* Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c); */
        switch (dir) {
            case LEFT:
                g.drawImage(ResourceManager.bulletL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceManager.bulletR, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceManager.bulletU, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceManager.bulletD, x, y, null);
                break;
        }

        move();
    }

    private void move() {
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

        //if bullet is out of border, change live to false for delete
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) living = false;

    }

    public void collideWith(Tank tank) {//this method is to check if bullet hit tank
        if (tank.getTeam() == this.getTeam()) return;//check the team of bullets

        Rectangle rect1 = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
        Rectangle rect2 = new Rectangle(tank.getX(), tank.getY(), Tank.WIDTH, Tank.HEIGHT);

        if (rect1.intersects(rect2)) {
            tank.die();
            this.die();

            //position of explosion
            int eX = tank.getX() + Tank.WIDTH/2 -Explosion.WIDTH;
            int eY = tank.getY() + Tank.HEIGHT/2 - Explosion.HEIGHT;

            tf.explosions.add(new Explosion(eX, eY, tf));//add one explosion to list in tf
        }
    }

    private void die() {
        this.living = false;
    }
}
