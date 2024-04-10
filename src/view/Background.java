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

/**
 * La classe Background est responsable de la création de l'image de fond pour le jeu.
 * Elle génère un fond aléatoire en utilisant des tuiles et place des obstacles sur le fond.
 */
public class Background {
    private VueMainGame vueMainGame;
    private ModelGame game;

    public Background(VueMainGame vueMainGame ,ModelGame game) {
        this.vueMainGame = vueMainGame;
        this.game = game;
    }

    /**
     * Crée et retourne une image de fond pour le jeu.
     * Cette méthode charge différentes tuiles d'images, les dispose aléatoirement pour former le fond,
     * et place les obstacles par-dessus.
     * @return Une image BufferedImage représentant le fond généré.
     */
    public Image drawBackground() {
        BufferedImage bg = new BufferedImage(this.vueMainGame.getLeft_width(), this.vueMainGame.getScreen_height(), BufferedImage.TYPE_INT_ARGB);
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
            for (int i = 0; i < this.vueMainGame.getLeft_width(); i += 30) {
                for (int j = 0; j < this.vueMainGame.getScreen_height(); j += 30) {
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
                    g2d.drawImage(tile, i, j, 30, 30, null);
                }
            }
            HashMap<Integer, ModelObstacle> obstacles = this.game.getObstacles();
            Image helper2;
            int x,y;
            for (ModelObstacle obstacle : obstacles.values()) {
                helper2 = ImageIO.read(new File(obstacle.getImgLink()));
                x = obstacle.getPosition().x;
                y = obstacle.getPosition().y;
                g2d.drawImage(helper2, x, y, 60, 60, null);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        g2d.dispose();
        return bg;
    }
}
