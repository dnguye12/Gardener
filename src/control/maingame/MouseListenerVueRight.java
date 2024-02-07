package control.maingame;

import model.ModelGame;
import model.ModelGardener;
import model.ModelUnit;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
            if (selected instanceof ModelGardener) {
                ModelGardener gardener = (ModelGardener) selected;
                if (e.getX() >= 55 && e.getX() <= 345) {
                    if (e.getY() >= 307 && e.getY() <= 408) {
                        gardener.plant();
                    } else if (e.getY() >= 418 && e.getY() <= 519) {
                        gardener.harvest();
                    } else if (e.getY() >= 529 && e.getY() <= 640) {
                        System.out.println("Promouvoir");
                    }
                }
            }
        }else {
            if(e.getX() >= 55 && e.getX() <= 345) {
                if(e.getY() >= 225 && e.getY() <= 315) {
                    this.game.setIsBuying("Gardener");
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
