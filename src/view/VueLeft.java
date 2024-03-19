package view;

import control.maingame.MouseListenerVueLeft;
import control.maingame.MouseMotionVueLeft;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class VueLeft extends JPanel {
    private final Toolkit toolkit;
    ModelGame game;
    private Image bgImage;
    private Image obstacleImage;
    private MouseMotionVueLeft vueLeftMouseMotion;
    private Image IMGCoinSign;
    private Image IMGScoreSign;
    private Image IMGTimeSign;
    private Image i1;
    private Image cursorNormal;
    private Image cursorHover;
    private Cursor c;
    public VueLeft(ModelGame game) {
        this.toolkit = Toolkit.getDefaultToolkit();
        this.setPreferredSize(new Dimension(VueMainGame.LEFT_WIDTH,VueMainGame.SCREEN_HEIGHT));

        this.game = game;
        this.initImage();

        MouseListenerVueLeft vueLeftMouseListener = new MouseListenerVueLeft(this.game);
        this.vueLeftMouseMotion = new MouseMotionVueLeft();
        this.addMouseListener(vueLeftMouseListener);
        this.addMouseMotionListener(this.vueLeftMouseMotion);
    }

    public void initImage() {
        this.bgImage = new Background(this.game).drawBackground();
        this.IMGCoinSign = this.toolkit.getImage("src/assets/maingame/left/coin sign.png");
        this.IMGScoreSign = this.toolkit.getImage("src/assets/maingame/left/score sign.png");
        this.IMGTimeSign = this.toolkit.getImage("src/assets/maingame/left/time sign.png");
        this.i1 = this.toolkit.getImage("src/assets/maingame/shop/gardener.png");
        this.cursorNormal = this.toolkit.getImage("src/assets/cursor/normal.png");
        this.cursorHover = this.toolkit.getImage("src/assets/cursor/hover.png");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(this.bgImage, 0,0, this);

        this.drawPlants(g);
        this.drawGardeners(g);
        this.drawRabbit(g);

        if(this.game.getIsBuying().length() > 0) {
            this.drawHover(g);
        }

        this.drawCoin(g);
        this.drawScore(g);
        this.drawTimeLeft(g);
        this.drawCursor();

        g.setColor(Color.BLUE);
    }

    public void drawGardeners(Graphics g) {
        Image helper;
        Graphics2D g2 = (Graphics2D) g;

        HashMap<Integer, ModelGardener> gardeners = this.game.getGardeners();
        for(ModelGardener gardener : gardeners.values()) {
            Point position = gardener.getPosition();
            if(gardener.isSelected()) {
                g2.setStroke(new BasicStroke(1));
                g2.setColor(Color.BLUE);

                int lineOfSightRadius = gardener.getRadius();

                g2.drawOval(position.x + 25 - lineOfSightRadius, position.y + 25 - lineOfSightRadius,
                        lineOfSightRadius * 2, lineOfSightRadius * 2);

                Color transparentColor = new Color(0, 0, 0, 33); // Semi-transparent black
                g2.setColor(transparentColor);

                g2.fillOval(position.x + 25 - lineOfSightRadius, position.y + 25 - lineOfSightRadius,
                        lineOfSightRadius * 2, lineOfSightRadius * 2);
            }
            helper = this.toolkit.getImage(gardener.getAnimation());
            g2.drawImage(helper, position.x, position.y, this);
        }
    }

    public void drawPlants(Graphics g) {
        HashMap<Integer, ModelPlant> plants = this.game.getPlants();
        for(ModelPlant plant : plants.values()) {
            Image img = this.toolkit.getImage(plant.getType().getPlantImage(plant.getStage()));
            Point position = plant.getPosition();
            g.drawImage(img, position.x, position.y, this);
        }
    }

    public void drawCoin(Graphics g) {
        int x = 50;
        int y = 0;
        g.drawImage(this.IMGCoinSign, x, y, this);

        g.setFont(FontGetter.getFont().deriveFont(24f));
        g.setColor(new Color(107,75,91));
        String coin = Integer.toString(this.game.getMoney());
        FontMetrics fm = g.getFontMetrics();
        int numberX = 95;
        int numberY = y + ((this.IMGCoinSign.getHeight(this) - fm.getHeight()) / 2) + fm.getAscent() + 6;
        g.drawString(coin, numberX, numberY);
    }

    public void drawScore(Graphics g) {
        int x = 175;
        int y = 0;
        g.drawImage(IMGScoreSign, x, y, this);

        g.setFont(FontGetter.getFont().deriveFont(24f));
        g.setColor(new Color(107,75,91));
        String score = Integer.toString(this.game.getScore());
        FontMetrics fm = g.getFontMetrics();
        int numberX = 225;
        int numberY = y + ((IMGScoreSign.getHeight(this) - fm.getHeight()) / 2) + fm.getAscent() + 6;
        g.drawString(score, numberX, numberY);
    }

    public void drawTimeLeft(Graphics g) {
        int x = 300;
        int y = 0;
        g.drawImage(IMGTimeSign, x, y, this);

        g.setFont(FontGetter.getFont().deriveFont(24f));
        g.setColor(new Color(107,75,91));
        String time = Integer.toString(this.game.getTimeLeft());
        FontMetrics fm = g.getFontMetrics();
        int numberX = 355;
        int numberY = y + ((IMGTimeSign.getHeight(this) - fm.getHeight()) / 2) + fm.getAscent() + 6;
        g.drawString(time, numberX, numberY);
    }

    public void drawRabbit(Graphics g) {
        Image helper;
        HashMap<Integer, ModelRabbit> rabbits = this.game.getRabbits();

        for(ModelRabbit rabbit : rabbits.values()) {
            helper = this.toolkit.getImage(rabbit.getAnimation());
            Point position = rabbit.getPosition();
            g.drawImage(helper, position.x, position.y, this);
        }
    }

    public void drawHover(Graphics g) {
        int x = this.vueLeftMouseMotion.getX();
        int y = this.vueLeftMouseMotion.getY();
        g.drawImage(this.i1, x, y, this);
    }

    public void drawCursor() {
        if(this.game.getIsBuying().length() > 0) {
            this.c = this.toolkit.createCustomCursor(this.cursorHover , new Point(this.getX(), this.getY()), "cursorHover");
        }else {
            this.c = this.toolkit.createCustomCursor(this.cursorNormal , new Point(this.getX(), this.getY()), "cursorNormal");
        }
        this.setCursor(this.c);
        }

    }