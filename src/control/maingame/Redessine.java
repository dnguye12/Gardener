package control.maingame;

import view.VueLeft;
import view.VueRight;

public class Redessine extends Thread {
    private VueLeft vueLeft;
    private VueRight vueRight;
    private static final int DELAY = 1000 / 60; //60 FPS

    public Redessine(VueLeft vueLeft, VueRight vueRight) {
        this.vueLeft = vueLeft;
        this.vueRight = vueRight;
    }

    @Override
    public void run() {
        while (true) {
            this.vueLeft.revalidate();
            this.vueLeft.repaint();

            this.vueRight.revalidate();
            this.vueRight.repaint();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
