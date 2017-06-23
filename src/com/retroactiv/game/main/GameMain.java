package com.retroactiv.game.main;

import javax.swing.JFrame;

public class GameMain {
    private static final String GAME_TITLE = "BattleBall!";
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 450;
    public static Game sGame;

    public static void main(String[] args) {
        System.out.println("LOGGING:  Welcome to the RetroActiv Game Engine!");
        System.out.print("LOGGING:  Building JFrame...");
        JFrame frame = new JFrame(GAME_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); //Prevents manual resize of window
        System.out.println("Done");

        System.out.println("LOGGING:  Load Game...");
        sGame = new Game(GAME_WIDTH, GAME_HEIGHT);
        frame.add(sGame);
        frame.pack();
        frame.setVisible(true);
        frame.setIconImage(Resources.iconimage);
    }
}
