package view;

import control.animation.AnimationGardener;
import control.animation.AnimationRabbit;
import control.maingame.*;
import model.ModelGame;

import javax.swing.*;
import java.awt.*;

public class VueMainGame {
    private final int left_width;
    private final int right_width;
    private final int screen_height;
    private final JFrame frame;
    private ModelGame game;
    private Timer checkTimer;
    public VueMainGame() {
        this.left_width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() > 1600 ? 1200 : (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 400;
        this.right_width = 400;
        this.screen_height = Math.min((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight(), 900);

        this.game = new ModelGame(this);

        this.frame = new JFrame();
        this.frame.setTitle("Gardener");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        VueLeft vueLeft = new VueLeft(this, this.game);
        VueRight vueRight = new VueRight(this, this.game);
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

        ThreadDrop threadDrop = new ThreadDrop(this.game.getDrops());

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

        threadDrop.start();
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
            new VueEnd(this ,this.game);
        }
    }

    public int getLeft_width() {
        return left_width;
    }

    public int getRight_width() {
        return right_width;
    }

    public int getScreen_height() {
        return screen_height;
    }
}
