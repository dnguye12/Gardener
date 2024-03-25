package view;

import control.maingame.MouseListenerVueEnd;
import model.FontGetter;
import model.ModelGame;

import javax.swing.*;
import java.awt.*;

public class VueEnd extends JPanel {
    private VueMainGame vueMainGame;
    private final JFrame frame;
    private final Toolkit toolkit;
    private ModelGame game;
    private Image bgImage;
    private Image endImage;

    public VueEnd(VueMainGame vueMainGame ,ModelGame game) {
        this.vueMainGame = vueMainGame;
        this.toolkit = Toolkit.getDefaultToolkit();

        this.frame = new JFrame("Gardener");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(Math.min((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 1600), this.vueMainGame.getScreen_height()));
        this.frame.setResizable(false);
        this.game = game;

        this.bgImage = new ImageIcon("src/assets/end/bg.png").getImage();
        this.endImage = new ImageIcon("src/assets/end/end.png").getImage();

        Image cursorNormal = this.toolkit.getImage("src/assets/cursor/normal.png");
        Cursor c =  this.toolkit.createCustomCursor(cursorNormal, new Point(this.getX(), this.getY()), "cursor");
        this.setCursor(c);

        MouseListenerVueEnd vueEndMouseListener = new MouseListenerVueEnd(this.frame, this.game);
        this.addMouseListener(vueEndMouseListener);
        this.frame.getContentPane().add(this);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.bgImage, 0, 0, this.getWidth(), this.getHeight(), this);
        g.drawImage(this.endImage, (this.getWidth() - this.endImage.getWidth(null)) / 2, (this.getHeight() - this.endImage.getHeight(null)) / 2, this);

        g.setFont(FontGetter.getFont().deriveFont(48f));
        g.setColor(new Color(107,75,91));
        String score = Integer.toString(this.game.getScore());
        FontMetrics fm = g.getFontMetrics();
        int numberX = 666 + ((270 - fm.stringWidth(score)) / 2);
        int numberY = 374 + ((81 - fm.getHeight()) / 2) + fm.getAscent();
        g.drawString(score, numberX, numberY);
    }
}
