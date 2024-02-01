package control.mainmenu;

import model.ModelGame;
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
        this.frame.setVisible(false);
        ModelGame game = new ModelGame();
        new VueMainGame(game);
        this.frame.dispose();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
