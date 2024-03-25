package control.mainmenu;

import model.ModelGame;
import model.MusicPlayer;
import view.VueMainGame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StartButtonListener implements MouseListener {
    JFrame frame;

    public StartButtonListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        MusicPlayer.playClick();
        this.frame.dispose();
        //ModelGame game = new ModelGame();
        new VueMainGame();

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
