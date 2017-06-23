package com.retroactiv.game.state;

//IMPORTS//
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.retroactiv.game.main.GameMain;

//using an abstract class allows me to have an interface with concrete methods
public abstract class State {

    public abstract void init();

    public abstract void update(float delta);

    public abstract void render(Graphics g);

    public abstract void onClick(MouseEvent e);

    public abstract void onKeyPress(KeyEvent e);

    public abstract void onKeyRelease(KeyEvent e);

    //Sets game state
    public void setCurrentState(State newState) {
        System.out.println("LOGGING:  Setting Game State...");
        GameMain.sGame.setCurrentState(newState);
        System.out.println("LOGGING:  Game State Initialized Successfully.");
    }

}
