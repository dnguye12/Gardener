package view;

import control.algo.GridSystem;
import control.maingame.MouseListenerVueLeft;
import control.maingame.MouseMotionVueLeft;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Classe représentant la vue gauche du jeu, affichant les éléments de jeu tels que les plantes, les jardiniers, et les objets collectables.
 */
public class VueLeft extends JPanel {
    private final VueMainGame vueMainGame;
    private final Toolkit toolkit;
    ModelGame game;
    private Image bgImage;
    private Image obstacleImage;
    private MouseMotionVueLeft vueLeftMouseMotion;
    private Image IMGCoinSign;
    private Image IMGScoreSign;
    private Image IMGTimeSign;
    private Image IMGHoverGardener;
    private Image IMGHoverChickenHouse;
    private Image cursorNormal;
    private Image cursorHover;
    private Cursor c;
    private Image dropMushroom, dropWheat, dropTomato, dropSoleil;
    private Image dropEgg;
    private Image dropChicken;
    private Image IMGChickenHouse;
    private Image IMGGrass0, IMGGrass1, IMGGrass2, IMGGrass3, IMGGrass4, IMGGrass5, IMGGrass6, IMGGrass7, IMGGrass8, IMGGrass9, IMGGrass10, IMGGrass11, IMGGrass12;
    public VueLeft(VueMainGame vueMainGame, ModelGame game) {
        this.vueMainGame = vueMainGame;
        this.toolkit = Toolkit.getDefaultToolkit();
        this.setPreferredSize(new Dimension(this.vueMainGame.getLeft_width(), this.vueMainGame.getScreen_height()));

        this.game = game;
        this.initImage();

        MouseListenerVueLeft vueLeftMouseListener = new MouseListenerVueLeft(this.game);
        this.vueLeftMouseMotion = new MouseMotionVueLeft();
        this.addMouseListener(vueLeftMouseListener);
        this.addMouseMotionListener(this.vueLeftMouseMotion);
    }

    /**
     * Initialise toutes les images utilisées dans la vue gauche.
     */
    public void initImage() {
        this.bgImage = new Background(this.vueMainGame ,this.game).drawBackground();

        this.IMGCoinSign = this.toolkit.getImage("src/assets/maingame/left/coin sign.png");
        this.IMGScoreSign = this.toolkit.getImage("src/assets/maingame/left/score sign.png");
        this.IMGTimeSign = this.toolkit.getImage("src/assets/maingame/left/time sign.png");

        this.IMGHoverGardener = this.toolkit.getImage("src/assets/maingame/shop/gardener.png");
        this.IMGHoverChickenHouse = this.toolkit.getImage("src/assets/maingame/chicken/house.png");

        this.cursorNormal = this.toolkit.getImage("src/assets/cursor/normal.png");
        this.cursorHover = this.toolkit.getImage("src/assets/cursor/hover.png");

        this.dropWheat = this.toolkit.getImage("src/assets/maingame/drop/wheat.png");
        this.dropTomato = this.toolkit.getImage("src/assets/maingame/drop/tomato.png");
        this.dropSoleil = this.toolkit.getImage("src/assets/maingame/drop/soleil.png");
        this.dropMushroom = this.toolkit.getImage("src/assets/maingame/drop/mushroom.png");

        this.dropEgg = this.toolkit.getImage("src/assets/maingame/drop/egg.png");
        this.dropChicken = this.toolkit.getImage("src/assets/maingame/drop/chicken.png");
        this.IMGChickenHouse = this.toolkit.getImage("src/assets/maingame/chicken/house.png");

        this.IMGGrass0 = this.toolkit.getImage("src/assets/maingame/grass/0.png");
        this.IMGGrass1 = this.toolkit.getImage("src/assets/maingame/grass/1.png");
        this.IMGGrass2 = this.toolkit.getImage("src/assets/maingame/grass/2.png");
        this.IMGGrass3 = this.toolkit.getImage("src/assets/maingame/grass/3.png");
        this.IMGGrass4 = this.toolkit.getImage("src/assets/maingame/grass/4.png");
        this.IMGGrass5 = this.toolkit.getImage("src/assets/maingame/grass/5.png");
        this.IMGGrass6 = this.toolkit.getImage("src/assets/maingame/grass/6.png");
        this.IMGGrass7 = this.toolkit.getImage("src/assets/maingame/grass/7.png");
        this.IMGGrass8 = this.toolkit.getImage("src/assets/maingame/grass/8.png");
        this.IMGGrass9 = this.toolkit.getImage("src/assets/maingame/grass/9.png");
        this.IMGGrass10 = this.toolkit.getImage("src/assets/maingame/grass/10.png");
        this.IMGGrass11 = this.toolkit.getImage("src/assets/maingame/grass/11.png");
        this.IMGGrass12 = this.toolkit.getImage("src/assets/maingame/grass/12.png");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(this.bgImage, 0,0, this);
        this.drawGrass(g);

        if(this.game.getHasChickenHouse()) {
            this.drawChickenHouse(g);
        }

        this.drawPlants(g);
        this.drawChicken(g);
        this.drawDrop(g);
        this.drawGardeners(g);
        this.drawRabbit(g);
        this.drawFox(g);

        if(this.game.getIsBuying().length() > 0) {
            this.drawHover(g);
        }

        this.drawCoin(g);
        this.drawScore(g);
        this.drawTimeLeft(g);

        //this.drawTile(g);
        this.drawWalkable(g);
        //this.drawField(g);

        this.drawCursor();
    }

    /**
     * Dessine les jardiniers et aussi leur rayon de récolter sur le panel.
     * @param g L'objet Graphics utilisé pour le dessin.
     */
    public void drawGardeners(Graphics g) {
        Image helper;
        Graphics2D g2 = (Graphics2D) g;

        HashMap<Integer, ModelGardener> gardeners = this.game.getGardeners();
        for(ModelGardener gardener : gardeners.values()) {
            Point position = gardener.getPosition();
            helper = this.toolkit.getImage(gardener.getAnimation());
            if(gardener.isSelected()) {
                g2.setStroke(new BasicStroke(1));
                g2.setColor(Color.BLUE);

                int lineOfSightRadius = gardener.getRadius();

                g2.drawOval(position.x + 25 - lineOfSightRadius - helper.getWidth(this) / 2, position.y + 25 - lineOfSightRadius - helper.getHeight(this) / 2,
                        lineOfSightRadius * 2, lineOfSightRadius * 2);

                Color transparentColor = new Color(0, 0, 0, 33); // Semi-transparent black
                g2.setColor(transparentColor);

                g2.fillOval(position.x + 25 - lineOfSightRadius - helper.getWidth(this) / 2, position.y + 25 - lineOfSightRadius - helper.getHeight(this) / 2,
                        lineOfSightRadius * 2, lineOfSightRadius * 2);
            }
            g2.drawImage(helper, position.x - helper.getWidth(this) / 2, position.y - helper.getHeight(this) / 2, this);
        }
    }

    /**
     * Dessine les plantes sur le panel.
     * @param g L'objet Graphics utilisé pour le dessin.
     */
    public void drawPlants(Graphics g) {
        HashMap<Integer, ModelPlant> plants = this.game.getPlants();
        for(ModelPlant plant : plants.values()) {
            Image img = this.toolkit.getImage(plant.getType().getPlantImage(plant.getStage()));
            Point position = plant.getPosition();
            g.drawImage(img, position.x, position.y, 60,60, this);
        }
    }

    /**
     * Dessine l'indicateur de pièces sur le panel.
     * @param g L'objet Graphics utilisé pour le dessin.
     */
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

    /**
     * Dessine l'indicateur de score sur le panel.
     * @param g L'objet Graphics utilisé pour le dessin.
     */
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

    /**
     * Dessine l'indicateur de temps restant sur le panel.
     * @param g L'objet Graphics utilisé pour le dessin.
     */
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

    /**
     * Dessine les lapins sur le panel.
     * @param g L'objet Graphics utilisé pour le dessin.
     */
    public void drawRabbit(Graphics g) {
        Image helper;
        HashMap<Integer, ModelRabbit> rabbits = this.game.getRabbits();

        for(ModelRabbit rabbit : rabbits.values()) {
            helper = this.toolkit.getImage(rabbit.getAnimation());
            Point position = rabbit.getPosition();
            g.drawImage(helper, position.x, position.y, this);
        }
    }

    /**
     * Dessine l'indicateur de survol lorsque l'utilisateur se trouve en mode achat ou autre.
     * @param g L'objet Graphics utilisé pour le dessin.
     */
    public void drawHover(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        float opacity = 0.5f;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

        int x = this.vueLeftMouseMotion.getX();
        int y = this.vueLeftMouseMotion.getY();
        if(this.game.getIsBuying().equals("Gardener")) {
            g2d.drawImage(this.IMGHoverGardener, x, y, this);
        }else if(this.game.getIsBuying().equals("ChickenHouse")) {
            g2d.drawImage(this.IMGHoverChickenHouse, x, y, this);
        }
        g2d.dispose();
    }

    /**
     * Change le curseur en fonction du contexte (normal ou survol).
     */
    public void drawCursor() {
        if(this.game.getIsBuying().length() > 0) {
            this.c = this.toolkit.createCustomCursor(this.cursorHover , new Point(this.getX(), this.getY()), "cursorHover");
        }else {
            this.c = this.toolkit.createCustomCursor(this.cursorNormal , new Point(this.getX(), this.getY()), "cursorNormal");
        }
        this.setCursor(this.c);
    }

    /**
     * Dessine les objets collectables sur le panel.
     * @param g L'objet Graphics utilisé pour le dessin.
     */
    public void drawDrop(Graphics g) {
        HashMap<Integer, ModelDrop> drops = this.game.getDrops();
        for(ModelDrop drop : drops.values()) {
            Point position = drop.getPosition();
            Point height = drop.getHeight();
            if(drop instanceof ModelPlantDrop helper) {
                switch (helper.getType()) {
                    case WHEAT -> g.drawImage(this.dropWheat, position.x, height.y, this);
                    case TOMATO -> g.drawImage(this.dropTomato, position.x, height.y, this);
                    case SUNFLOWER -> g.drawImage(this.dropSoleil, position.x, height.y, this);
                    case MUSHROOM -> g.drawImage(this.dropMushroom, position.x, height.y, this);
                }
            }else if(drop instanceof  ModelEggDrop) {
                g.drawImage(this.dropEgg, position.x, height.y, this);
            }else if(drop instanceof  ModelChickenDrop) {
                g.drawImage(this.dropChicken, position.x, height.y, this);
            }
        }
    }

    public void drawChicken(Graphics g) {
        Image helper;
        HashMap<Integer, ModelChicken> chickens = this.game.getChickens();
        for(ModelChicken chicken : chickens.values()) {
            helper = this.toolkit.getImage(chicken.getAnimation());
            Point position = chicken.getPosition();
            g.drawImage(helper, position.x, position.y, this);
        }
    }

    public void drawChickenHouse(Graphics g) {
        ModelChickenHouse chickenHouse = this.game.getChickenHouse();
        g.drawImage(this.IMGChickenHouse, chickenHouse.getPosition().x, chickenHouse.getPosition().y, this);
    }

    public void drawGrass(Graphics g) {
        ModelFieldCell[][] grid = this.game.getField().getGrid();

        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(!grid[i][j].hasGrass()) {
                    continue;
                }
                int helper = grid[i][j].getGrassType();
                Image img = null;
                switch (helper) {
                    case 0 -> img = this.IMGGrass0;
                    case 1 -> img = this.IMGGrass1;
                    case 2 -> img = this.IMGGrass2;
                    case 3 -> img = this.IMGGrass3;
                    case 4 -> img = this.IMGGrass4;
                    case 5 -> img = this.IMGGrass5;
                    case 6 -> img = this.IMGGrass6;
                    case 7 -> img = this.IMGGrass7;
                    case 8 -> img = this.IMGGrass8;
                    case 9 -> img = this.IMGGrass9;
                    case 10 -> img = this.IMGGrass10;
                    case 11 -> img = this.IMGGrass11;
                    case 12 -> img = this.IMGGrass12;
                }
                g.drawImage(img, i * 60, j * 60, 60,60,this);
            }
        }
    }

    public void drawFox(Graphics g) {
        Image helper;
        HashMap<Integer, ModelFox> foxes = this.game.getFoxes();

        for(ModelFox fox : foxes.values()) {
            helper = this.toolkit.getImage(fox.getAnimation());
            Point position = fox.getPosition();
            g.drawImage(helper, position.x, position.y, this);
        }
    }

    public void drawTile(Graphics g) {
        boolean[][] grid = this.game.getGridSystem().getWalkable();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                // Check if the sum of row and column indices is even or odd
                if ((i + j) % 2 == 0) {
                    g.setColor(new Color(0, 255, 0, 30)); // Green for even
                } else {
                    g.setColor(new Color(255, 0, 0, 30)); // Red for odd
                }
                g.fillRect(i * GridSystem.CELL_SIZE, j * GridSystem.CELL_SIZE, GridSystem.CELL_SIZE, GridSystem.CELL_SIZE);
            }
        }
    }

    public void drawWalkable(Graphics g) {
        boolean[][] grid = this.game.getGridSystem().getWalkable();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(!grid[i][j]) {
                    g.setColor(new Color(0, 255, 0, 30)); // Green for even
                    g.fillRect(i * GridSystem.CELL_SIZE, j * GridSystem.CELL_SIZE, GridSystem.CELL_SIZE, GridSystem.CELL_SIZE);
                }
            }
        }
    }

    public void drawField(Graphics g) {
        ModelFieldCell[][] grid = this.game.getField().getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j].getContent() != null) {
                    g.setColor(new Color(0, 0, 255, 30)); // Green for even
                    g.fillRect(i * 30, j * 30, 30, 30);
                }
            }
        }
    }

}