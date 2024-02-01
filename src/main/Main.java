package main;

import model.ModelGame;
import view.VueMainGame;
import view.VueMainMenu;

public class Main {
    public static void main(String[] args) {
        //VueMainMenu mainMenu = new VueMainMenu();

        VueMainGame vueMainGame = new VueMainGame(new ModelGame());
    }
}