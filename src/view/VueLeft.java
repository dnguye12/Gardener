package view;

import control.maingame.MouseListenerVueLeft;
import model.ModelGame;
import model.ModelGardener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VueLeft extends JPanel {
    ModelGame game;
    public VueLeft(ModelGame game) {
        this.setPreferredSize(new Dimension(1200,900));

        this.game = game;
        MouseListenerVueLeft vueLeftMouseListener = new MouseListenerVueLeft(this.game);
        this.addMouseListener(vueLeftMouseListener);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.drawGardeners(g);
    }

    public void drawGardeners(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);

        ArrayList<ModelGardener> gardeners = this.game.getGardeners();
        for(ModelGardener gardener : gardeners) {
            Point position = gardener.getPosition();
            g2.fillOval(position.x, position.y, 50, 50);
        }
    }
}
