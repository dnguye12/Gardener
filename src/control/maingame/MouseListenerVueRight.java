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
        // Si on a déjà sélectionné un élément
        if(selected != null) {
            // Si c'est un jardinier, on vérifie si on a cliqué sur un bouton d'action
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
                // Si c'est un poulailler, on vérifie si on a cliqué sur le bouton de spawn
            }else if(selected instanceof ModelChickenHouse chickenHouse) {
                if(e.getX() >= 55 && e.getX() <= 345) {
                    if(e.getY() >= 329 && e.getY() <= 430) {
                        chickenHouse.spawnChicken();
                    }
                }
            }
        }else {
            // Si on n'a pas sélectionné d'élément, on vérifie si on a cliqué sur un bouton d'achat 
            if(e.getX() >= 55 && e.getX() <= 345) {
                if(e.getY() >= 315 && e.getY() <= 405) {
                    MusicPlayer.playClick();
                    this.game.setIsBuying("Gardener");
                }else if(e.getY() >= 420 && e.getY() <= 510 && !this.game.getHasChickenHouse()) {
                    MusicPlayer.playClick();
                    this.game.setIsBuying("ChickenHouse");
                }else if(e.getY() >= 535 && e.getY() <= 625) {
                    MusicPlayer.playClick();
                    this.game.quit();
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
