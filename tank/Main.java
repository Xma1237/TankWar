package tank;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();

        //initialization of enemy tanks
        for (int i = 0; i < 50; i++) {
            tf.enemyTank.add(new Tank(50 + i * 80, 200, Dir.DOWN, TankTeam.BAD, tf));
        }

        //refresh the screen to show movement
        while (true) {
            Thread.sleep(50);   //rest 50ms
            tf.repaint();
        }
    }
}
