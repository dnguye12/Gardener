package view;

import control.maingame.MouseListenerVueRight;
import model.*;

import javax.swing.*;
import java.awt.*;

public class VueRight extends JPanel {
    private final Toolkit toolkit;
    private ModelGame game;
    private final Image bgImage;
    public VueRight(ModelGame game) {
        this.toolkit = Toolkit.getDefaultToolkit();

        this.setPreferredSize(new Dimension(VueMainGame.RIGHT_WIDTH,VueMainGame.SCREEN_HEIGHT));
        this.bgImage = new ImageIcon("src/assets/maingame/right/RightBG.png").getImage();

        this.game = game;

        MouseListenerVueRight vueRightMouseListener = new MouseListenerVueRight(this.game);
        this.addMouseListener(vueRightMouseListener);
    }

    @Override
    public void paintComponent(Graphics g) {
        //super.repaint();
        super.paintComponent(g);
        g.drawImage(this.bgImage, 0, 0, this.getWidth(), this.getHeight(), this);

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

    public void drawNull(Graphics g) {
        int x, y;

        Image IMGAvatar = this.toolkit.getImage("src/assets/maingame/right/rien.png");
        x = (this.getWidth() - IMGAvatar.getWidth(this)) / 2;
        y = 50;
        g.drawImage(IMGAvatar, x, y, this);
    }

    public void drawShop(Graphics g) {
        int x, y;
        Image IMGAvatar = this.toolkit.getImage("src/assets/maingame/shop/talk.png");
        x = (this.getWidth() - IMGAvatar.getWidth(this)) / 2;
        y = 50;
        g.drawImage(IMGAvatar, x, y, this);
        y += IMGAvatar.getHeight(this) + 50;

        int money = this.game.getMoney();
        Image IMGGardener;
        if(money >= 100) {
            IMGGardener = this.toolkit.getImage("src/assets/maingame/shop/shop1.png");
        }else {
            IMGGardener = this.toolkit.getImage("src/assets/maingame/shop/shopoff1.png");
        }
        g.drawImage(IMGGardener, x, y, this);
        y += IMGGardener.getHeight(this) + 10;
    }
    public void drawGardener(Graphics g) {
        int x, y;
        Font font = FontGetter.getFont();

        Image IMGAvatar = this.toolkit.getImage("src/assets/maingame/right/gardener.png");
        x = (this.getWidth() - IMGAvatar.getWidth(this)) / 2;
        y = 50;
        g.drawImage(IMGAvatar, x, y, this);
        y += IMGAvatar.getHeight(this) + 50;

        Image IMGAction0 = this.toolkit.getImage("src/assets/maingame/right/gardeneraction0.png");
        x = (this.getWidth() - IMGAction0.getWidth(this)) / 2;
        g.drawImage(IMGAction0, x, y, this);
        g.setFont(font);
        g.setColor(new Color(107,75,91));
        String status = ((ModelGardener)this.game.getSelected()).getStatus().getName().toUpperCase();
        FontMetrics fm = g.getFontMetrics(font);
        int numberX = x + (IMGAction0.getWidth(this) - fm.stringWidth(status)) / 2;
        int numberY = y + ((IMGAction0.getHeight(this) - fm.getHeight()) / 2) + fm.getAscent() + 5;
        g.drawString(status, numberX, numberY);
        y += IMGAction0.getHeight(this) + 10;

        Image IMGAction1;
        if(this.game.getMoney() >= 10) {
            IMGAction1 = this.toolkit.getImage("src/assets/maingame/right/gardeneraction1.png");
        }else {
            IMGAction1 = this.toolkit.getImage("src/assets/maingame/right/gardeneroff1.png");
        }

        g.drawImage(IMGAction1, x, y, this);
        y += IMGAction1.getHeight(this) + 10;

        Image IMGAction2;
        if(((ModelGardener) this.game.getSelected()).plantsNear().size() > 0) {
            IMGAction2 = this.toolkit.getImage("src/assets/maingame/right/gardeneraction2.png");
        } else {
            IMGAction2 = this.toolkit.getImage("src/assets/maingame/right/gardeneroff2.png");
        }
        g.drawImage(IMGAction2, x, y, this);
        y += IMGAction2.getHeight(this) + 10;

        Image IMGAction3;
        if(this.game.getMoney() >= 25) {
            IMGAction3 = this.toolkit.getImage("src/assets/maingame/right/gardeneraction3.png");
        }else {
            IMGAction3 = this.toolkit.getImage("src/assets/maingame/right/gardeneroff3.png");
        }
        g.drawImage(IMGAction3, x, y, this);
    }

    public void drawPlants(Graphics g) {
        int x, y;
        ModelPlant plant = (ModelPlant)this.game.getSelected();
        Font font = FontGetter.getFont().deriveFont(28f);

        Image IMGAvatar = this.toolkit.getImage("src/assets/maingame/plant/talk.png");
        x = (this.getWidth() - IMGAvatar.getWidth(this)) / 2;
        y = 50;
        g.drawImage(IMGAvatar, x, y, this);
        Image IMGPlant = this.toolkit.getImage(plant.getType().getPlantImage(plant.getStage()));
        g.drawImage(IMGPlant, x + 29, y + 27, this);
        g.setFont(font);
        g.setColor(new Color(107,75,91));
        String name = plant.getType().getName().toUpperCase();
        FontMetrics fm = g.getFontMetrics(font);
        int numberX = x + 100 + (150 - fm.stringWidth(name)) / 2;
        int numberY = y + 40 + ((40 - fm.getHeight()) / 2) + fm.getAscent() + 5;
        g.drawString(name, numberX, numberY);
        y += IMGAvatar.getHeight(this) + 50;

        Image IMGButton = this.toolkit.getImage("src/assets/maingame/plant/button.png");
        x = (this.getWidth() - IMGButton.getWidth(this)) / 2;

        //Plant hp
        int hp = plant.getHP() / 100;
        int maxHP = plant.getType().getMaxHP() / 100;
        Image IMGHearth0 = this.toolkit.getImage("src/assets/maingame/plant/hearth0.png");
        Image IMGHearth1 = this.toolkit.getImage("src/assets/maingame/plant/hearth1.png");
        g.drawImage(IMGButton, x , y , this);
        int xHeart = (this.getWidth() - IMGHearth0.getWidth(this) * maxHP - 5 * maxHP) /2;
        int yHeart = y + (IMGButton.getHeight(this) - IMGHearth0.getHeight(this)) / 2;
        for(int i = 1; i <= maxHP; i++) {
            if(i <= hp) {
                g.drawImage(IMGHearth1, xHeart, yHeart, this);

            }else {
                g.drawImage(IMGHearth0, xHeart, yHeart, this);
            }
            xHeart += IMGHearth1.getWidth(this) + 5;
        }
        y += IMGButton.getHeight(this) + 10;

        //Growth state progress
        int growth = plant.getCurrentStage() / (plant.getGrowspeed() / 10);
        int stage = plant.getStage();
        int stageMax = plant.getType().getStageCount();
        g.drawImage(IMGButton, x , y , this);
        Image IMGBar0 = this.toolkit.getImage("src/assets/maingame/plant/bar0.png");
        Image IMGBar1 = this.toolkit.getImage("src/assets/maingame/plant/bar1.png");
        Image IMGBar2 = this.toolkit.getImage("src/assets/maingame/plant/bar2.png");

        Image IMGState0 = this.toolkit.getImage(plant.getType().getStateIcon(stage));
        Image IMGState1 = this.toolkit.getImage(plant.getType().getStateIcon(stage + 1));
        xHeart = (this.getWidth() - IMGBar0.getWidth(this) * 10 - 2 * 10 - IMGState0.getWidth(this) * 2) /2;
        yHeart = y + (IMGButton.getHeight(this) - IMGState0.getHeight(this)) / 2;
        g.drawImage(IMGState0, xHeart, yHeart, this);
        g.drawImage(IMGState1, xHeart + IMGState0.getWidth(this) + 2 + IMGBar0.getWidth(this) * 10 + 2 * 10, yHeart, this);
        xHeart += IMGState0.getWidth(this) + 2;
        yHeart = y + (IMGButton.getHeight(this) - IMGBar0.getHeight(this)) / 2;
        for(int i = 1; i <= 10; i++) {
            if (i <= growth) {
                if(stage == stageMax - 1) {
                    g.drawImage(IMGBar2, xHeart, yHeart, this);
                }else {
                    g.drawImage(IMGBar1, xHeart, yHeart, this);
                }
            }else {
                g.drawImage(IMGBar0, xHeart, yHeart, this);
            }
            xHeart += IMGBar0.getWidth(this) + 2;
            }
        }

        public void drawRabbit(Graphics g) {
            int x, y;
            ModelRabbit rabbit = (ModelRabbit)this.game.getSelected();
            Font font = FontGetter.getFont().deriveFont(28f);

            Image IMGAvatar = this.toolkit.getImage("src/assets/maingame/cow/cowtalk.png");
            x = (this.getWidth() - IMGAvatar.getWidth(this)) / 2;
            y = 50;
            g.drawImage(IMGAvatar, x, y, this);
            y += IMGAvatar.getHeight(this) + 50;

            Image IMGAction0 = this.toolkit.getImage("src/assets/maingame/right/gardeneraction0.png");
            x = (this.getWidth() - IMGAction0.getWidth(this)) / 2;
            g.drawImage(IMGAction0, x, y, this);
            g.setFont(font);
            g.setColor(new Color(107,75,91));
            String status = rabbit.getStatus().getName().toUpperCase();
            FontMetrics fm = g.getFontMetrics(font);
            int numberX = x + (IMGAction0.getWidth(this) - fm.stringWidth(status)) / 2;
            int numberY = y + ((IMGAction0.getHeight(this) - fm.getHeight()) / 2) + fm.getAscent() + 5;
            g.drawString(status, numberX, numberY);
            y += IMGAction0.getHeight(this) + 10;
            y += IMGAvatar.getHeight(this) + 50;

        }
}

