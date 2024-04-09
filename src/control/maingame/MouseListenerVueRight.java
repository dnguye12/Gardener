package control.maingame;

import model.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Écouteur de souris pour la vue droite du jeu,
 * gérant les interactions telles que planter, récolter et promouvoir les jardiniers,
 * ainsi que l'achat de nouveaux jardiniers.
 */
public class MouseListenerVueRight implements MouseListener {
    public enum GardenerAction {
        PLANTER,
        RECOLTER,
        PROMOUVOIR
    }
    private ModelGame game;
    private GardenerAction action;


    public MouseListenerVueRight(ModelGame game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ModelUnit selected = this.game.getSelected();
        if(selected != null) {
            if (selected instanceof ModelGardener gardener) {
                if (e.getX() >= 55 && e.getX() <= 345) {
                    if (e.getY() >= 307 && e.getY() <= 408) {
                        MusicPlayer.playPlant();
                        gardener.plant();
                    } else if (e.getY() >= 418 && e.getY() <= 519) {
                        MusicPlayer.playClick();
                        gardener.harvest();
                    } else if (e.getY() >= 529 && e.getY() <= 640) {
                        MusicPlayer.playUpgrade();
                        gardener.upgrade();
                    }
                }
            }else if(selected instanceof ModelChickenHouse chickenHouse) {
                if(e.getX() >= 55 && e.getX() <= 345) {
                    if(e.getY() >= 329 && e.getY() <= 430) {
                        chickenHouse.spawnChicken();
                    }
                }
            }
        }else {
            if(e.getX() >= 55 && e.getX() <= 345) {
                if(e.getY() >= 225 && e.getY() <= 315) {
                    MusicPlayer.playClick();
                    this.game.setIsBuying("Gardener");
                }else if(e.getY() >= 330 && e.getY() <= 420 && !this.game.getHasChickenHouse()) {
                    MusicPlayer.playClick();
                    this.game.setIsBuying("ChickenHouse");
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
