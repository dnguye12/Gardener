package control.maingame;

import view.VueLeft;

public class Redessine extends Thread {
    private VueLeft vueLeft;
    private static final int DELAY = 1000 / 60; //60 FPS

    public Redessine(VueLeft vueLeft) {
        this.vueLeft = vueLeft;
    }

    @Override
    public void run() {
        while (true) {
            this.vueLeft.revalidate();
            this.vueLeft.repaint();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
