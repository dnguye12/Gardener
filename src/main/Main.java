package main;

import model.MusicPlayer;
import view.VueMainMenu;

public class Main {
    public static void main(String[] args) {
        MusicPlayer.initSound();
        MusicPlayer.playMusic();
        VueMainMenu mainMenu = new VueMainMenu();
    }
}