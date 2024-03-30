package view;

import control.mainmenu.QuitButtonListener;
import control.mainmenu.StartButtonListener;

import javax.swing.*;
import java.awt.*;

public class VueMainMenu extends JPanel {
    private Toolkit toolkit;
    private final JFrame frame;
    private Image bgImage;
    private Image cursor;

    /**
     * La classe VueMainMenu représente le menu principal du jeu.
     */
    public VueMainMenu() {
        this.frame = new JFrame("Gardener");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.toolkit = Toolkit.getDefaultToolkit();
        this.setPreferredSize(new Dimension(Math.min((int) this.toolkit.getScreenSize().getWidth(), 1600), Math.min((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight(), 900)));

        // Définit l'image de fond du menu principal
        this.bgImage = new ImageIcon("src/assets/mainmenu/bg.png").getImage();

        // Configure le layout du panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Crée et configure les boutons
        ImageIcon startButtonIcon = new ImageIcon("src/assets/mainmenu/btnStart.png");
        JLabel startButton = new JLabel(startButtonIcon);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addMouseListener(new StartButtonListener(this.frame));

        ImageIcon quitButtonIcon = new ImageIcon("src/assets/mainmenu/btnQuit.png");
        JLabel quitButton = new JLabel(quitButtonIcon);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.addMouseListener(new QuitButtonListener(this.frame));

        // Définit le curseur personnalisé
        this.cursor = this.toolkit.getImage("src/assets/cursor/normal.png");
        Cursor c = this.toolkit.createCustomCursor(cursor , new Point(this.getX(), this.getY()), "cursor");
        this.setCursor(c);

        // Ajoute les éléments au panel
        this.add(Box.createVerticalGlue());
        this.add(startButton);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(quitButton);
        this.add(Box.createVerticalGlue());

        // Configure le frame pour afficher le menu
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
