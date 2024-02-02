package view;

import java.awt.*;
import java.io.File;

public class FontGetter {
    public static Font getFont() {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/MinecraftBold-nMK1.otf")).deriveFont(Font.BOLD, 36f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return font;
    }
}
