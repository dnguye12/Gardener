package control.maingame;

import model.ModelGame;
import view.VueMainGame;
import view.VueMainMenu;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseListenerVueEnd implements MouseListener {
    private JFrame endFrame;
    private ModelGame game;
    public MouseListenerVueEnd(JFrame endFrame, ModelGame game) {
        this.endFrame = endFrame;
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
        System.out.println(e.getX() + " " + e.getY());
        int x = e.getX();
        int y = e.getY();
        if(x >= 668 && x <= 932) {
            if(y >= 498 && y <= 569) {
                this.endFrame.dispose();
                this.game.reset();
                new VueMainGame(game);
            }else if(y >= 595 && y <= 671) {
                this.endFrame.dispose();
                VueMainMenu mainMenu = new VueMainMenu();
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
