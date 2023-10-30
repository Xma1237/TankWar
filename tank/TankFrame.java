package tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {
    Tank myTank = new Tank(200, 400, Dir.DOWN,TankTeam.GOOD, this);

    //    Bullet Bullet = new Bullet(300, 300, Dir.DOWN);
    static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;

    List<Bullet> bullets = new ArrayList<>();   //for Tank method- fire()

    List<Tank> enemyTank = new ArrayList<>();

    List<Explosion> explosions = new ArrayList<>(); //there are more than one explosion

    public TankFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("Tank War");
        setVisible(true);

        this.addKeyListener(new MyKeyListener());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    /*class Tank will take over this part
    //you need variables to make tank move the position
    int x = 200, y = 200;
    Dir dir = Dir.DOWN;
    private static final int SPEED = 10;   //tank moving speed
    */

    class MyKeyListener extends KeyAdapter {
        //set boolean false to left,right,up,down
        boolean bL = false;
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;

        @Override//effective when press key
        public void keyPressed(KeyEvent e) {
            /*
            int keyCode = e.getKeyCode();

            System.out.println(keyCode);    //show the key you pressed

              not perfect for going upper/lower right/left at same time
            switch (keyCode) {
                case KeyEvent.VK_LEFT:  //VK_37 also works
                    x -= 10;
                    break;
                case KeyEvent.VK_RIGHT:
                    x += 10;
                    break;
                case KeyEvent.VK_UP:
                    y -= 10;
                    break;
                case 40:
                    y += 10;
                    break;
            }

             x += 10;    //tank move 10 pixel
            repaint();  invoke method paint and show the movement
            */
            //while pressing key, make boolean true
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    System.out.println("Left");
                    bL = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    System.out.println("Right");
                    bR = true;
                    break;
                case KeyEvent.VK_UP:
                    System.out.println("Up");
                    bU = true;
                    break;
                case KeyEvent.VK_DOWN:
                    System.out.println("Down");
                    bD = true;
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }

        //effective when release key, set boolean back to false
        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }

        private void setMainTankDir() {
            //when nothing is pressed, set tank moving to false
            if (!bL && !bR && !bU && !bD) {
                myTank.setMoving(false);
            } else {
                myTank.setMoving(true);

                if (bL) myTank.setDir(Dir.LEFT);
                if (bR) myTank.setDir(Dir.RIGHT);
                if (bU) myTank.setDir(Dir.UP);
                if (bD) myTank.setDir(Dir.DOWN);
            }
        }
    }

    //double buffering to reduce the refreshing issue
    Image offScreenImage = null;

    @Override//double buffering to reduce the refreshing issue
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override   //paint相当于画笔,画出坐标x,y开始- 长宽50,50的rectangle
    public void paint(Graphics g) { //会自动调用
        Color c = g.getColor();
        g.setColor(Color.white);
        g.drawString("# of Bullets:" + bullets.size(), 10, 60);
        g.drawString("# of Enemy Tanks:" + enemyTank.size(), 10, 80);
        g.drawString("# of Explosions:" + explosions.size(), 10, 100);
        g.setColor(c);

        myTank.paint(g);//draw your tank
        for (int i = 0; i < bullets.size(); i++) {//draw your bullets
            bullets.get(i).paint(g);
        }

        for (int i = 0; i < enemyTank.size(); i++) {//draw enemy tanks
            enemyTank.get(i).paint(g);
        }

        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).paint(g);
        }

        //collision detect
        for (int i = 0; i < bullets.size(); i++) {//collision
            for (int j = 0; j < enemyTank.size(); j++) {
                bullets.get(i).collideWith(enemyTank.get(j));
            }
        }
    }
}
