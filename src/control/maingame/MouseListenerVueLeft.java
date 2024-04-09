package control.maingame;

import model.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

/**
 * Gère les clics de souris sur le côté gauche de l'écran de jeu,
 * permettant la sélection et l'interaction avec les éléments du jeu tels que les jardiniers, les plantes et les lapins.
 */
public class MouseListenerVueLeft implements MouseListener {
    private ModelGame game;
    public MouseListenerVueLeft(ModelGame game) {
        this.game = game;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Gère l'événement de relâchement de la souris, permettant la sélection d'unités ou l'exécution d'actions spécifiques.
     *
     * @param e L'événement de souris contenant les informations du clic.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        ModelUnit selected = this.game.getSelected();
        String isBuying = this.game.getIsBuying();
        if(e.getButton() == MouseEvent.BUTTON1) {
            if (isBuying.length() == 0) {
                HashMap<Integer, ModelGardener> gardeners = this.game.getGardeners();
                for (ModelGardener gardener : gardeners.values()) {
                    Point center = gardener.getPosition();
                    double dx = Math.abs(center.x - e.getPoint().x);
                    double dy = Math.abs(center.y - e.getPoint().y);
                    if (dx * dx + dy * dy <= 50 * 50) {
                        if (selected != null) {
                            selected.setSelected(false);
                            MusicPlayer.playUpgrade();
                        }
                        gardener.setSelected(true);
                        this.game.setSelected(gardener);
                        return;
                    }
                }
                HashMap<Integer, ModelPlant> plants = this.game.getPlants();
                for (ModelPlant plant : plants.values()) {
                    Point center = plant.getPosition();
                    double dx = Math.abs(center.x - e.getPoint().x);
                    double dy = Math.abs(center.y - e.getPoint().y);
                    if (dx * dx + dy * dy <= 50 * 50) {
                        if (selected != null) {
                            selected.setSelected(false);
                            MusicPlayer.playPlant();
                        }
                        plant.setSelected(true);
                        this.game.setSelected(plant);
                        return;
                    }
                }

                HashMap<Integer, ModelRabbit> rabbits = this.game.getRabbits();
                for (ModelRabbit rabbit : rabbits.values()) {
                    Point center = rabbit.getPosition();
                    double dx = Math.abs(center.x - e.getPoint().x);
                    double dy = Math.abs(center.y - e.getPoint().y);
                    if (dx * dx + dy * dy <= 50 * 50) {
                        if (selected != null) {
                            selected.setSelected(false);
                            MusicPlayer.playCow();
                        }
                        rabbit.setSelected(true);
                        this.game.setSelected(rabbit);
                        return;
                    }
                }

                HashMap<Integer, ModelChicken> chickens = this.game.getChickens();
                for(ModelChicken chicken : chickens.values()) {
                    Point center = chicken.getPosition();
                    double dx = Math.abs(center.x - e.getPoint().x);
                    double dy = Math.abs(center.y - e.getPoint().y);
                    if(dx * dx + dy * dy <= 42 * 42) {
                        if(selected != null) {
                            selected.setSelected(false);
                        }
                        chicken.setSelected(true);
                        this.game.setSelected(chicken);
                        return;
                    }
                }

                if (selected != null) {
                    MusicPlayer.playClick();
                    selected.setSelected(false);
                }
                this.game.setSelected(null);
            }else {
                if(isBuying.equals("Gardener")) {
                    int money = this.game.getMoney();
                    if(money >= 100) {
                        MusicPlayer.playClick();
                        this.game.setMoney(money - 100);
                        this.game.setIsBuying("");
                        this.game.addGardener(new ModelGardener(IdGen.generateGardenerId(), e.getPoint(), e.getPoint(), this.game));
                    }
                }
            }
        }else if(e.getButton() == MouseEvent.BUTTON3) {
            if(isBuying.length() > 0) {
                this.game.setIsBuying("");
            }else {
                ModelUnit helper = this.game.getSelected();
                if (helper != null) {
                    if (helper instanceof ModelGardener) {
                        helper.setDest(e.getPoint());
                    }
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
