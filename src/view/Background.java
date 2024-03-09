package view;

import model.ModelGame;
import model.ModelObstacle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class Background {
    private ModelGame game;

    public Background(ModelGame game) {
        this.game = game;
    }

    public Image drawBackground() {
        BufferedImage bg = new BufferedImage(VueMainGame.LEFT_WIDTH, VueMainGame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
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
            for (int i = 0; i < VueMainGame.LEFT_WIDTH; i += 32) {
                for (int j = 0; j < VueMainGame.SCREEN_HEIGHT; j += 32) {
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
            HashMap<Integer, ModelObstacle> obstacles = this.game.getObstacles();
            Image helper2;
            int x,y;
            for (ModelObstacle obstacle : obstacles.values()) {
                helper2 = ImageIO.read(new File(obstacle.getImgLink()));
                x = obstacle.getPosition().x;
                y = obstacle.getPosition().y;
                g2d.drawImage(helper2, x, y, 48, 48, null);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        g2d.dispose();
        return bg;
    }
}
