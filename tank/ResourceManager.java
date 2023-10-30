package tank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceManager {
    public static BufferedImage GOODtankL, GOODtankR, GOODtankU, GOODtankD;
    public static BufferedImage BADtankL, BADtankR, BADtankU, BADtankD;

    public static BufferedImage bulletL, bulletR, bulletU, bulletD;

    public static BufferedImage[] explosions = new BufferedImage[3];

    static {
        try {
            GOODtankL = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/tankL.jpg"));
//            tankR = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/tankR.jpg"));
//            tankU = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/tankU.jpg"));
//            tankD = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/tankD.jpg"));
            GOODtankR = ImageUtil.rotateImage(GOODtankL, 180);
            GOODtankU = ImageUtil.rotateImage(GOODtankL, 90);
            GOODtankD = ImageUtil.rotateImage(GOODtankL, -90);

            BADtankL = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/BADtankL.jpg"));
            BADtankR = ImageUtil.rotateImage(BADtankL, 180);
            BADtankU = ImageUtil.rotateImage(BADtankL, 90);
            BADtankD = ImageUtil.rotateImage(BADtankL, -90);

            bulletL = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/BulletL.jpg"));
            bulletR = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/BulletR.jpg"));
            bulletU = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/BulletU.jpg"));
            bulletD = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/BulletD.jpg"));

            for (int i = 0; i < 3; i++) {
                explosions[i] = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/explosion" + (i) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
