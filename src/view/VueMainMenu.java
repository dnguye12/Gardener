package view;

import control.mainmenu.QuitButtonListener;
import control.mainmenu.StartButtonListener;

import javax.swing.*;
import java.awt.*;

public class VueMainMenu extends JPanel {
    private final JFrame frame;

    private Image bgImage;

    public VueMainMenu() {
        this.frame = new JFrame("Gardener");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(1600, 900));

        this.bgImage = new ImageIcon("src/assets/mainmenu/bg.png").getImage();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        ImageIcon startButtonIcon = new ImageIcon("src/assets/mainmenu/btnStart.png");
        JLabel startButton = new JLabel(startButtonIcon);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addMouseListener(new StartButtonListener(this.frame));

        ImageIcon quitButtonIcon = new ImageIcon("src/assets/mainmenu/btnQuit.png");
        JLabel quitButton = new JLabel(quitButtonIcon);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.addMouseListener(new QuitButtonListener(this.frame));

        this.add(Box.createVerticalGlue());
        this.add(startButton);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(quitButton);
        this.add(Box.createVerticalGlue());

        this.frame.getContentPane().add(this);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.bgImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
