package view;

//import control.maingame.Redessine;
import control.maingame.Redessine;
import control.maingame.ThreadGardener;
import model.ModelGame;

import javax.swing.*;
import java.awt.*;

public class VueMainGame {
    private final JFrame frame;
    private final ModelGame game;
    public VueMainGame(ModelGame game) {
        this.game = game;

        this.frame = new JFrame();
        this.frame.setTitle("Gardener");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        VueLeft vueLeft = new VueLeft(this.game);
        VueRight vueRight = new VueRight(this.game);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(vueLeft, gbc);

        gbc.gridx = 1;
        panel.add(vueRight, gbc);

        this.frame.add(panel);

        Redessine redessine = new Redessine(vueLeft);
        ThreadGardener threadGardener = new ThreadGardener(this.game.getGardeners());

        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);

        redessine.start();
        threadGardener.start();
    }
}
