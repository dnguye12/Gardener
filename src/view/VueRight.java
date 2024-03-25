package view;

import control.maingame.MouseListenerVueRight;
import model.*;

import javax.swing.*;
import java.awt.*;

public class VueRight extends JPanel {
    private VueMainGame vueMainGame;
    private final Toolkit toolkit;
    private ModelGame game;
    private Image IMGbg;
    private Image IMGshopTalk;
    private Image IMGshop1;
    private Image IMGshopoff1;
    private Image IMGGar;
    private Image IMGGarAction0;
    private Image IMGGarAction1;
    private Image IMGGarAction2;
    private Image IMGGarAction3;
    private Image IMGGarActionOff1;
    private Image IMGGarActionOff2;
    private Image IMGGarActionOff3;
    private Image IMGCowTalk;
    private Image IMGPlantTalk;
    private Image IMGPlantButton;
    private Image IMGHearth0;
    private Image IMGHearth1;
    private Image IMGBar0;
    private Image IMGBar1;
    private Image IMGBar2;
    private Image cursorNormal;
    public VueRight(VueMainGame vueMainGame ,ModelGame game) {
        this.vueMainGame = vueMainGame;
        this.toolkit = Toolkit.getDefaultToolkit();

        this.setPreferredSize(new Dimension(this.vueMainGame.getRight_width(), this.vueMainGame.getScreen_height()));
        this.initImage();

        this.game = game;

        Cursor c =  this.toolkit.createCustomCursor(this.cursorNormal, new Point(this.getX(), this.getY()), "cursor");
        this.setCursor(c);

        MouseListenerVueRight vueRightMouseListener = new MouseListenerVueRight(this.game);
        this.addMouseListener(vueRightMouseListener);
    }

    public void initImage() {
        this.IMGbg = new ImageIcon("src/assets/maingame/right/RightBG.png").getImage();

        this.IMGshopTalk = this.toolkit.getImage("src/assets/maingame/shop/talk.png");
        this.IMGshop1 = this.toolkit.getImage("src/assets/maingame/shop/shop1.png");
        this.IMGshopoff1 = this.toolkit.getImage("src/assets/maingame/shop/shopoff1.png");

        this.IMGGar = this.toolkit.getImage("src/assets/maingame/right/gardener.png");
        this.IMGGarAction0 = this.toolkit.getImage("src/assets/maingame/right/gardeneraction0.png");
        this.IMGGarAction1 = this.toolkit.getImage("src/assets/maingame/right/gardeneraction1.png");
        this.IMGGarAction2 = this.toolkit.getImage("src/assets/maingame/right/gardeneraction2.png");
        this.IMGGarAction3 = this.toolkit.getImage("src/assets/maingame/right/gardeneraction3.png");
        this.IMGGarActionOff1 = this.toolkit.getImage("src/assets/maingame/right/gardeneroff1.png");
        this.IMGGarActionOff2 = this.toolkit.getImage("src/assets/maingame/right/gardeneroff2.png");
        this.IMGGarActionOff3 = this.toolkit.getImage("src/assets/maingame/right/gardeneroff3.png");

        this.IMGCowTalk = this.toolkit.getImage("src/assets/maingame/cow/cowtalk.png");

        this.IMGPlantTalk = this.toolkit.getImage("src/assets/maingame/plant/talk.png");
        this.IMGPlantButton = this.toolkit.getImage("src/assets/maingame/plant/button.png");

        this.IMGHearth0 = this.toolkit.getImage("src/assets/maingame/plant/hearth0.png");
        this.IMGHearth1 = this.toolkit.getImage("src/assets/maingame/plant/hearth1.png");

        this.IMGBar0 = this.toolkit.getImage("src/assets/maingame/plant/bar0.png");
        this.IMGBar1 = this.toolkit.getImage("src/assets/maingame/plant/bar1.png");
        this.IMGBar2 = this.toolkit.getImage("src/assets/maingame/plant/bar2.png");
        this.cursorNormal = this.toolkit.getImage("src/assets/cursor/normal.png");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.IMGbg, 0, 0, this.getWidth(), this.getHeight(), this);

        ModelUnit selected = this.game.getSelected();
        if(selected != null) {
            if(selected instanceof ModelGardener) {
                this.drawGardener(g);
            }
            else if(selected instanceof ModelPlant) {
                this.drawPlants(g);
            }else if(selected instanceof ModelRabbit) {
                this.drawRabbit(g);
            }
        }else {
            this.drawShop(g);
        }
    }

    public void drawShop(Graphics g) {
        int x, y;
        x = (this.getWidth() - this.IMGshopTalk.getWidth(this)) / 2;
        y = 50;
        g.drawImage(this.IMGshopTalk, x, y, this);
        y += this.IMGshopTalk.getHeight(this) + 50;

        int money = this.game.getMoney();
        if(money >= 100) {
            g.drawImage(this.IMGshop1, x, y, this);
        }else {
            g.drawImage(this.IMGshopoff1, x, y , this);
        }
        y += this.IMGshop1.getHeight(this) + 10;
    }
    public void drawGardener(Graphics g) {
        int x, y;
        Font font = FontGetter.getFont();

        ModelGardener gardener = (ModelGardener)this.game.getSelected();

        x = (this.getWidth() - this.IMGGar.getWidth(this)) / 2;
        y = 50;
        g.drawImage(this.IMGGar, x, y, this);
        y += this.IMGGar.getHeight(this) + 50;

        x = (this.getWidth() - this.IMGGarAction0.getWidth(this)) / 2;
        g.drawImage(this.IMGGarAction0, x, y, this);
        g.setFont(font);
        g.setColor(new Color(107,75,91));
        String status = gardener.getStatus().getName().toUpperCase();
        FontMetrics fm = g.getFontMetrics(font);
        int numberX = x + (this.IMGGarAction0.getWidth(this) - fm.stringWidth(status)) / 2;
        int numberY = y + ((this.IMGGarAction0.getHeight(this) - fm.getHeight()) / 2) + fm.getAscent() + 5;
        g.drawString(status, numberX, numberY);
        y += this.IMGGarAction0.getHeight(this) + 10;

        if(this.game.getMoney() >= 10) {
            g.drawImage(this.IMGGarAction1, x, y, this);
        }else {
            g.drawImage(this.IMGGarActionOff1, x, y, this);
        }
        y += this.IMGGarAction1.getHeight(this) + 10;

        if(((ModelGardener) this.game.getSelected()).plantsNear().size() > 0) {
            g.drawImage(this.IMGGarAction2, x, y, this);
        } else {
            g.drawImage(this.IMGGarActionOff2, x, y, this);
        }
        y += this.IMGGarAction2.getHeight(this) + 10;

        if(this.game.getMoney() >= 25 && gardener.getPromoteState() < 5) {
            g.drawImage(this.IMGGarAction3, x, y, this);
        }else {
            g.drawImage(this.IMGGarActionOff3, x, y, this);
        }
    }

    public void drawPlants(Graphics g) {
        int x, y;
        ModelPlant plant = (ModelPlant)this.game.getSelected();
        Font font = FontGetter.getFont().deriveFont(28f);

        x = (this.getWidth() - this.IMGPlantTalk.getWidth(this)) / 2;
        y = 50;
        g.drawImage(this.IMGPlantTalk, x, y, this);
        Image IMGPlant = this.toolkit.getImage(plant.getType().getPlantImage(plant.getStage()));
        g.drawImage(IMGPlant, x + 29, y + 27, this);
        g.setFont(font);
        g.setColor(new Color(107,75,91));
        String name = plant.getType().getName().toUpperCase();
        FontMetrics fm = g.getFontMetrics(font);
        int numberX = x + 100 + (150 - fm.stringWidth(name)) / 2;
        int numberY = y + 40 + ((40 - fm.getHeight()) / 2) + fm.getAscent() + 5;
        g.drawString(name, numberX, numberY);
        y += this.IMGPlantTalk.getHeight(this) + 50;

        x = (this.getWidth() - this.IMGPlantButton.getWidth(this)) / 2;

        //Plant hp
        int hp = plant.getHP() / 100;
        int maxHP = plant.getType().getMaxHP() / 100;
        g.drawImage(this.IMGPlantButton, x , y , this);
        int xHeart = (this.getWidth() - IMGHearth0.getWidth(this) * maxHP - 5 * maxHP) /2;
        int yHeart = y + (this.IMGPlantButton.getHeight(this) - IMGHearth0.getHeight(this)) / 2;
        for(int i = 1; i <= maxHP; i++) {
            if(i <= hp) {
                g.drawImage(this.IMGHearth1, xHeart, yHeart, this);

            }else {
                g.drawImage(this.IMGHearth0, xHeart, yHeart, this);
            }
            xHeart += this.IMGHearth1.getWidth(this) + 5;
        }
        y += this.IMGPlantButton.getHeight(this) + 10;

        //Growth state progress
        int growth = plant.getCurrentStage() / (plant.getGrowspeed() / 10);
        int stage = plant.getStage();
        int stageMax = plant.getType().getStageCount();
        g.drawImage(this.IMGPlantButton, x , y , this);

        Image IMGState0 = this.toolkit.getImage(plant.getType().getStateIcon(stage));
        Image IMGState1 = this.toolkit.getImage(plant.getType().getStateIcon(stage + 1));
        xHeart = (this.getWidth() - this.IMGBar0.getWidth(this) * 10 - 2 * 10 - IMGState0.getWidth(this) * 2) /2;
        yHeart = y + (this.IMGPlantButton.getHeight(this) - IMGState0.getHeight(this)) / 2;
        g.drawImage(IMGState0, xHeart, yHeart, this);
        g.drawImage(IMGState1, xHeart + IMGState0.getWidth(this) + 2 + this.IMGBar0.getWidth(this) * 10 + 2 * 10, yHeart, this);
        xHeart += IMGState0.getWidth(this) + 2;
        yHeart = y + (this.IMGPlantButton.getHeight(this) - this.IMGBar0.getHeight(this)) / 2;
        for(int i = 1; i <= 10; i++) {
            if (i <= growth) {
                if(stage == stageMax - 1) {
                    g.drawImage(this.IMGBar2, xHeart, yHeart, this);
                }else {
                    g.drawImage(this.IMGBar1, xHeart, yHeart, this);
                }
            }else {
                g.drawImage(this.IMGBar0, xHeart, yHeart, this);
            }
            xHeart += this.IMGBar0.getWidth(this) + 2;
            }
        }

        public void drawRabbit(Graphics g) {
            int x, y;
            ModelRabbit rabbit = (ModelRabbit)this.game.getSelected();
            Font font = FontGetter.getFont().deriveFont(28f);

            x = (this.getWidth() - this.IMGCowTalk.getWidth(this)) / 2;
            y = 50;
            g.drawImage(this.IMGCowTalk, x, y, this);
            y += this.IMGCowTalk.getHeight(this) + 50;

            x = (this.getWidth() - this.IMGGarAction0.getWidth(this)) / 2;
            g.drawImage(this.IMGGarAction0, x, y, this);
            g.setFont(font);
            g.setColor(new Color(107,75,91));
            String status = rabbit.getStatus().getName().toUpperCase();
            FontMetrics fm = g.getFontMetrics(font);
            int numberX = x + (this.IMGGarAction0.getWidth(this) - fm.stringWidth(status)) / 2;
            int numberY = y + ((this.IMGGarAction0.getHeight(this) - fm.getHeight()) / 2) + fm.getAscent() + 5;
            g.drawString(status, numberX, numberY);
            y += this.IMGGarAction0.getHeight(this) + 10;
            y += this.IMGCowTalk.getHeight(this) + 50;

        }
}

