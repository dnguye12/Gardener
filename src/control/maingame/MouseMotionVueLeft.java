package control.maingame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotionVueLeft implements MouseMotionListener {
    private static final int DELAY = 1000 / 60;
    private int x;
    private int y;
    public MouseMotionVueLeft() {
        this.x = 0;
        this.y = 0;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
}
