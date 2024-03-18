package main;

import model.ModelGame;
import model.MusicPlayer;
import view.VueEnd;
import view.VueMainGame;
import view.VueMainMenu;

public class Main {
    public static void main(String[] args) {
        MusicPlayer.playMusic();
        VueMainMenu mainMenu = new VueMainMenu();

        //VueMainGame vueMainGame = new VueMainGame(new ModelGame());
        //VueEnd vueEnd = new VueEnd(new ModelGame());
    }
}