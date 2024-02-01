package view;

import model.ModelGame;
import model.ModelGardener;
import model.ModelUnit;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class VueRight extends JPanel {
    private final Toolkit toolkit;
    private ModelGame game;
    private final Image bgImage;
    private Font font;
    public VueRight(ModelGame game) {
        this.toolkit = Toolkit.getDefaultToolkit();
        this.font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/MinecraftBold-nMK1.otf")).deriveFont(Font.BOLD, 36f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setPreferredSize(new Dimension(400,900));
        this.bgImage = new ImageIcon("src/assets/maingame/RightBG.png").getImage();

        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.repaint();
        g.drawImage(this.bgImage, 0, 0, this.getWidth(), this.getHeight(), this);

        ModelUnit selected = this.game.getSelected();
        if(selected != null) {
            if(selected instanceof ModelGardener) {
                this.drawGardener(g);
            }
        }else {
            this.drawNull(g);
        }
    }

    public void drawNull(Graphics g) {
        int x, y;

        Image IMGAvatar = this.toolkit.getImage("src/assets/maingame/rien.png");
        x = (this.getWidth() - IMGAvatar.getWidth(this)) / 2;
        y = 50;
        g.drawImage(IMGAvatar, x, y, this);
    }
    public void drawGardener(Graphics g) {
        int x, y;

        Image IMGAvatar = this.toolkit.getImage("src/assets/maingame/gardener.png");
        x = (this.getWidth() - IMGAvatar.getWidth(this)) / 2;
        y = 50;
        g.drawImage(IMGAvatar, x, y, this);
        y += IMGAvatar.getHeight(this) + 50;

        Image IMGAction0 = this.toolkit.getImage("src/assets/maingame/gardeneraction0.png");
        x = (this.getWidth() - IMGAction0.getWidth(this)) / 2;
        g.drawImage(IMGAction0, x, y, this);
        g.setFont(this.font);
        g.setColor(new Color(107,75,91));
        String status = ((ModelGardener)this.game.getSelected()).getStatus().getName().toUpperCase();
        FontMetrics fm = g.getFontMetrics(font);
        int numberX = x + (IMGAction0.getWidth(this) - fm.stringWidth(status)) / 2;
        int numberY = y + ((IMGAction0.getHeight(this) - fm.getHeight()) / 2) + fm.getAscent() + 5;
        g.drawString(status, numberX, numberY);
        y += IMGAction0.getHeight(this) + 10;

        Image IMGAction1 = this.toolkit.getImage("src/assets/maingame/gardeneraction1.png");
        g.drawImage(IMGAction1, x, y, this);
        y += IMGAction1.getHeight(this) + 10;

        Image IMGAction2 = this.toolkit.getImage("src/assets/maingame/gardeneraction2.png");
        g.drawImage(IMGAction2, x, y, this);
        y += IMGAction2.getHeight(this) + 10;

        Image IMGAction3 = this.toolkit.getImage("src/assets/maingame/gardeneraction3.png");
        g.drawImage(IMGAction3, x, y, this);
    }
}
