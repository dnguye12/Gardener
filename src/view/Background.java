package view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Background {

    public Background() {
    }

    public Image drawBackground() {
        BufferedImage bg = new BufferedImage(1200, 900, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bg.createGraphics();
        try {
            Image tile0 = ImageIO.read(new File("src/assets/maingame/bg/tile0.png"));
            Image tile1 = ImageIO.read(new File("src/assets/maingame/bg/tile1.png"));
            Image tile2 = ImageIO.read(new File("src/assets/maingame/bg/tile2.png"));
            Image tile3 = ImageIO.read(new File("src/assets/maingame/bg/tile3.png"));
            Image tile4 = ImageIO.read(new File("src/assets/maingame/bg/tile4.png"));
            Image tile5 = ImageIO.read(new File("src/assets/maingame/bg/tile5.png"));

            Random random = new Random();

            int helper;
            for (int i = 0; i < 1200; i += 32) {
                for (int j = 0; j < 900; j += 32) {
                    helper = random.nextInt(30) + 1;
                    Image tile;
                    switch (helper) {
                        case 1:
                            tile = tile1;
                            break;
                        case 2:
                            tile = tile2;
                            break;
                        case 3:
                            tile = tile3;
                            break;
                        case 4:
                            tile = tile4;
                            break;
                        case 5:
                            tile = tile5;
                            break;
                        default:
                            tile = tile0;
                            break;
                    }
                    g2d.drawImage(tile, i, j, 32, 32, null);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        g2d.dispose();
        return bg;
    }
}
