package view;

import control.maingame.MouseListenerVueLeft;
import model.ModelGame;
import model.ModelGardener;
import model.ModelPlant;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.util.ArrayList;

public class VueLeft extends JPanel {
    private final Toolkit toolkit;
    ModelGame game;
    public VueLeft(ModelGame game) {
        this.toolkit = Toolkit.getDefaultToolkit();
        this.setPreferredSize(new Dimension(1200,900));

        this.game = game;
        MouseListenerVueLeft vueLeftMouseListener = new MouseListenerVueLeft(this.game);
        this.addMouseListener(vueLeftMouseListener);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.drawPlants(g);
        this.drawGardeners(g);

        this.drawCoin(g);
        this.drawScore(g);
    }

    public void drawGardeners(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);

        ArrayList<ModelGardener> gardeners = this.game.getGardeners();
        for(ModelGardener gardener : gardeners) {
            Point position = gardener.getPosition();
            g2.fillOval(position.x, position.y, 50, 50);
        }
    }

    public void drawPlants(Graphics g) {
        ArrayList<ModelPlant> plants = this.game.getPlants();
        for(ModelPlant plant : plants) {
            Image img = this.toolkit.getImage(plant.getType().getPlantImage(plant.getStage()));
            Point position = plant.getPosition();
            g.drawImage(img, position.x, position.y, this);
        }
    }

    public void drawCoin(Graphics g) {
        int x = 50;
        int y = 0;
        Image IMGCoinSign = this.toolkit.getImage("src/assets/maingame/coin sign.png");
        g.drawImage(IMGCoinSign, x, y, this);

        g.setFont(FontGetter.getFont().deriveFont(24f));
        g.setColor(new Color(107,75,91));
        String coin = Integer.toString(this.game.getMoney());
        FontMetrics fm = g.getFontMetrics();
        int numberX = 95;
        int numberY = y + ((IMGCoinSign.getHeight(this) - fm.getHeight()) / 2) + fm.getAscent() + 6;
        g.drawString(coin, numberX, numberY);
    }

    public void drawScore(Graphics g) {
        int x = 175;
        int y = 0;
        Image IMGScoreSign = this.toolkit.getImage("src/assets/maingame/score sign.png");
        g.drawImage(IMGScoreSign, x, y, this);

        g.setFont(FontGetter.getFont().deriveFont(24f));
        g.setColor(new Color(107,75,91));
        String score = Integer.toString(this.game.getScore());
        FontMetrics fm = g.getFontMetrics();
        int numberX = 225;
        int numberY = y + ((IMGScoreSign.getHeight(this) - fm.getHeight()) / 2) + fm.getAscent() + 6;
        g.drawString(score, numberX, numberY);
    }
}
