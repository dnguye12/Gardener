package view;

import control.animation.AnimationGardener;
import control.animation.AnimationRabbit;
import control.maingame.*;
import model.ModelGame;

import javax.swing.*;
import java.awt.*;

public class VueMainGame {
    public static final int LEFT_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() > 1600 ? 1200 : (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 400;
    public static final int RIGHT_WIDTH = 400;
    public static final int SCREEN_HEIGHT = Math.min((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight(), 900);
    private final JFrame frame;
    private final ModelGame game;
    private Timer checkTimer;
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

        Redessine redessine = new Redessine(vueLeft, vueRight);
        ThreadGardener threadGardener = new ThreadGardener(this.game.getGardeners());
        AnimationGardener animationGardener = new AnimationGardener(this.game);

        ThreadPlant threadPlant = new ThreadPlant(this.game);

        RabbitGen rabbitGen = new RabbitGen(this.game);
        ThreadRabbit threadRabbit = new ThreadRabbit(this.game);
        AnimationRabbit animationRabbit = new AnimationRabbit(this.game);

        ThreadTime threadTime = new ThreadTime(this.game);
        this.initTimer();

        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);

        redessine.start();
        animationGardener.start();
        threadGardener.start();

        threadPlant.start();
        rabbitGen.start();
        animationRabbit.start();
        threadRabbit.start();
        threadTime.start();
    }

    private void initTimer() {
        int checkInterval = 1000;
        checkTimer = new Timer(checkInterval, e -> checkTimeLeft());
        checkTimer.start();
    }

    private void checkTimeLeft() {
        if(this.game.getTimeLeft() <= 0) {
         checkTimer.stop();
         this.frame.dispose();
            new VueEnd(this.game);
        }
    }
}
