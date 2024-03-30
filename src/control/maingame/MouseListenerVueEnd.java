package control.maingame;

import model.ModelGame;
import model.MusicPlayer;
import view.VueMainGame;
import view.VueMainMenu;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Gère les clics de souris dans l'écran de fin de jeu, permettant au joueur de redémarrer le jeu ou de revenir au menu principal.
 */
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

    /**
     * Gère l'action de relâchement de la souris pour détecter les clics sur les boutons de l'écran de fin.
     *
     * @param e L'événement de souris capturé.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if(x >= 668 && x <= 932) {
            if(y >= 498 && y <= 569) {
                MusicPlayer.playClick();
                this.endFrame.dispose();
                this.game.reset();
                new VueMainGame();
            }else if(y >= 595 && y <= 671) {
                MusicPlayer.playClick();
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
