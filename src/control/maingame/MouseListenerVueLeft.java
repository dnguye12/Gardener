package control.maingame;

import model.ModelGame;
import model.ModelGardener;
import model.ModelUnit;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

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

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            ArrayList<ModelGardener> gardeners = this.game.getGardeners();
            for (ModelGardener gardener : gardeners) {
                Point center = gardener.getPosition();
                double dx = Math.abs(center.x - e.getPoint().x);
                double dy = Math.abs(center.y - e.getPoint().y);
                if (dx * dx + dy * dy <= 50 * 50) {
                    this.game.setSelected(gardener);
                    return;
                }
            }
        }else if(e.getButton() == MouseEvent.BUTTON3) {
            ModelUnit helper = this.game.getSelected();
            if (helper != null) {
                if (helper instanceof ModelGardener) {
                    ((ModelGardener) helper).setDest(e.getPoint());
                    return;
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
