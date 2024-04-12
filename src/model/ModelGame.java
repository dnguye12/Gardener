package model;

import control.algo.AStarPathfinder;
import control.algo.GridSystem;
import view.VueMainGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 * Classe représentant le modèle de jeu principal. Elle contient toutes les entités du jeu
 * telles que les jardiniers, les plantes, les lapins, les obstacles et les objets récupérables.
 * Elle gère également l'état du jeu comme l'unité sélectionnée, l'achat en cours, le score,
 * le temps restant et la monnaie.
 */
public class ModelGame {
    private VueMainGame vueMainGame;
    private ModelUnit selected; // Unité actuellement sélectionnée dans le jeu.
    private String isBuying; // État d'achat actuel
    private HashMap<Integer, ModelGardener> gardeners;
    private HashMap<Integer, ModelPlant> plants;
    private ArrayList<Integer> plantsToHarvest;
    private HashMap<Integer, ModelRabbit> rabbits;
    private HashMap<Integer, ModelObstacle> obstacles;
    private HashMap<Integer, ModelDrop> drops;
    private HashMap<Integer, ModelChicken> chickens;
    private ArrayList<Integer> chickensToDie;
    private HashMap<Integer, ModelFox> foxes;
    private boolean hasChickenHouse;
    private ModelChickenHouse chickenHouse;
    private ModelField field;
    private int money;
    private int score;
    private int timeLeft; // Temps restant dans le jeu.
    private AStarPathfinder pathfinder; // Le chercheur de chemin utilisant l'algorithme A* pour la navigation.
    private GridSystem gridSystem;

    public ModelGame(VueMainGame vueMainGame) {
        this.vueMainGame = vueMainGame;
        this.gardeners = new HashMap<>();

        this.plants = new HashMap<>();
        this.plantsToHarvest = new ArrayList<Integer>();

        this.rabbits = new HashMap<>();

        this.obstacles = new HashMap<>();
        this.initObstacles();

        this.drops = new HashMap<>();

        this.chickens = new HashMap<>();
        this.hasChickenHouse = false;
        this.chickenHouse = null;
        this.chickensToDie = new ArrayList<>();

        this.foxes = new HashMap<>();

        this.field = new ModelField(this, this.vueMainGame);

        this.selected = null;
        this.isBuying = "";

        this.money = 200;
        this.score = 0;
        this.timeLeft = 600;

        this.gridSystem = new GridSystem(this, this.vueMainGame);
        this.pathfinder = new AStarPathfinder(this.gridSystem.getWidth(), this.gridSystem.getHeight(), this.gridSystem.getWalkable());

        int helperx = (this.vueMainGame.getLeft_width() - 50) / 2;
        int helpery = (this.vueMainGame.getScreen_height() - 50) / 2;
        this.gardeners.put(0, new ModelGardener(0, new Point(helperx, helpery), new Point(helperx, helpery), this));
    }

    /**
     * Initialise les obstacles du jeu de manière aléatoire.
     */
    private void initObstacles() {
        HashSet<Point> already = new HashSet<>();
        Random rand = new Random();
        int helperx = this.vueMainGame.getLeft_width() / GridSystem.OBSTACLE_SIZE - 2;
        int helpery = this.vueMainGame.getScreen_height() / GridSystem.OBSTACLE_SIZE - 2;
        int x,y;
        for(int i = 1; i <= 20; i++) {
            while(true) {
                x = rand.nextInt(helperx) + 1;
                y = rand.nextInt(helpery) + 1;
                Point helper = new Point(x, y);
                if(already.contains(helper)) {
                    continue;
                }
                already.add(helper);
                break;
            }
            this.obstacles.put(i, new ModelObstacle(i, new Point(x * GridSystem.OBSTACLE_SIZE, y * GridSystem.OBSTACLE_SIZE)));
        }
    }

    public void setSelected(ModelUnit selected) {
        this.selected = selected;
    }

    public ModelUnit getSelected() {
        return this.selected;
    }

    public String getIsBuying() {
        return this.isBuying;
    }

    public void setIsBuying(String isBuying) {
        if(isBuying.equals("")) {
            this.isBuying = "";
        }else if(isBuying.equals("Gardener") &&  this.money >= 200)  {
            this.isBuying = "Gardener";
        }else if(isBuying.equals("ChickenHouse") && this.money >= 100) {
            this.isBuying = "ChickenHouse";
        }
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getTimeLeft() {
        return this.timeLeft;
    }
    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }
    public GridSystem getGridSystem() {
        return this.gridSystem;
    }

    public AStarPathfinder getPathfinder() {
        return pathfinder;
    }

    public HashMap<Integer, ModelGardener> getGardeners() {
        return this.gardeners;
    }

    public void addGardener(ModelGardener g) {
        this.gardeners.put(g.getId(), g);
    }

    public HashMap<Integer, ModelPlant> getPlants() {
        return this.plants;
    }
    public void removePlant(int id) {
        this.plants.remove(id);
    }
    public ArrayList<Integer> getPlantsToHarvest() {
        return this.plantsToHarvest;
    }

    public void addPlantsToHarvest(ModelPlant plant) {
        int id = plant.getId();
        if(this.plantsToHarvest.contains(id)) {
            return;
        }
        this.plantsToHarvest.add(id);
    }

    public void setPlantsToHarvest(ArrayList<Integer> plantsToHarvest) {
        this.plantsToHarvest = plantsToHarvest;
    }

    public void addPlant(ModelPlant plant) {
        this.plants.putIfAbsent(plant.getId(), plant);
    }

    public HashMap<Integer, ModelRabbit> getRabbits() {
        return this.rabbits;
    }
    /**
     * Ajoute un nouveau lapin dans le jeu à une position aléatoire.
     * La direction initiale du lapin est également déterminée aléatoirement parmi quatre directions possibles.
     * Joue le son associé à l'apparition d'un lapin.
     */
    public void addRabbit() {
        Random rand = new Random();
        int helperx = this.vueMainGame.getLeft_width() - 50;
        int helpery = this.vueMainGame.getScreen_height() - 50;

        // 0 = up, 1 = right, 2 = down, 3 = left
        int dir = rand.nextInt(4);

        int x,y;
        if(dir == 0) {
            x = rand.nextInt(helperx);
            y = 0;
        }else if(dir == 1) {
            x = helperx;
            y = rand.nextInt(helpery);
        }else if(dir == 2) {
            x = rand.nextInt(helperx);
            y = helpery;
        }else {
            x = 0;
            y = rand.nextInt(helpery);
        }
        Point point = new Point(x, y);
        int helper =  x <= 600 ? -1 : 1;
        int idr = IdGen.generateRabbitId();
        this.rabbits.put(idr, new ModelRabbit(idr, point, point, this, helper, this.vueMainGame));
        MusicPlayer.playCow();
    }

    public HashMap<Integer, ModelObstacle> getObstacles() {
        return this.obstacles;
    }

    public HashMap<Integer, ModelDrop> getDrops() {
        return this.drops;
    }

    public void addDrop(ModelDrop drop) {
        this.drops.put(drop.getId(), drop);
    }
    public void removeDrop(int id) {
        this.drops.remove(id);
    }

    public HashMap<Integer, ModelChicken> getChickens() {
        return this.chickens;
    }
    public void addChicken(ModelChicken chicken) {
        this.chickens.put(chicken.getId(), chicken);
    }
    public void addChickenToDie(int id) {
        this.chickensToDie.add(id);
    }
    public void removeChicken() {
        for(Integer id : this.chickensToDie) {
            this.chickens.remove(id);
        }
        this.chickensToDie.clear();
    }
    public boolean getHasChickenHouse() {
        return this.hasChickenHouse;
    }

    public ModelChickenHouse getChickenHouse() {
        return this.chickenHouse;
    }
    public void setChickenHouse(ModelChickenHouse chickenHouse) {
        this.chickenHouse = chickenHouse;
        this.hasChickenHouse = true;
    }
    public HashMap<Integer, ModelFox> getFoxes() {
        return this.foxes;
    }
    public void addFox() {
        Random rand = new Random();
        int helperx = this.vueMainGame.getLeft_width() - 50;
        int helpery = this.vueMainGame.getScreen_height() - 50;

        // 0 = up, 1 = right, 2 = down, 3 = left
        int dir = rand.nextInt(4);

        int x,y;
        if(dir == 0) {
            x = rand.nextInt(helperx);
            y = 0;
        }else if(dir == 1) {
            x = helperx;
            y = rand.nextInt(helpery);
        }else if(dir == 2) {
            x = rand.nextInt(helperx);
            y = helpery;
        }else {
            x = 0;
            y = rand.nextInt(helpery);
        }
        Point point = new Point(x, y);
        int helper =  x <= 600 ? -1 : 1;
        int idf = IdGen.generateFoxId();
        this.foxes.put(idf, new ModelFox(idf, point, point, this, helper, this.vueMainGame));
        MusicPlayer.playFox();
    }
    public ModelField getField() {
        return this.field;
    }
    /**
     * Réinitialise le jeu en effaçant toutes les entités (jardiniers, plantes, lapins, obstacles, objets récupérables),
     * en réinitialisant l'unité sélectionnée, l'état d'achat, la monnaie, le score, et le temps restant.
     * Ajoute un jardinier par défaut au centre de l'écran pour commencer le jeu.
     */
    public void reset() {
        this.gardeners.clear();
        this.plants.clear();
        this.plantsToHarvest.clear();
        this.rabbits.clear();
        this.obstacles.clear();
        this.drops.clear();
        this.chickens.clear();
        this.chickensToDie.clear();
        this.foxes.clear();

        this.selected = null;
        this.isBuying = "";

        this.money = 10;
        this.score = 0;
        this.timeLeft = 300;
        int helperx = (this.vueMainGame.getLeft_width() - 50) / 2;
        int helpery = (this.vueMainGame.getScreen_height() - 50) / 2;
        this.gardeners.put(0, new ModelGardener(0, new Point(helperx, helpery), new Point(helperx, helpery), this));
    }
}
