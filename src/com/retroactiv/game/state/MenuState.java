package com.retroactiv.game.state;

//IMPORTS//
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.retroactiv.game.main.Resources;


public class MenuState extends State{

    @Override
    public void init() {
        System.out.println("LOGGING:  MenuState Initialized SUccessfully.");
    }

    @Override
    public void update(float delta) {

    }

    //Renders images
    @Override
    public void render(Graphics g) {
        g.drawImage(Resources.welcome, 0, 0, null);
    }

    @Override
    public void onClick(MouseEvent e) {
        System.out.println("LOGGING:  MouseEvent - Change to PlayState()!");
        setCurrentState(new PlayState());
    }

    @Override
    public void onKeyPress(KeyEvent e) {
        System.out.println("LOGGING:  KeyEvent (onKeyPress)!");
    }

    @Override
    public void onKeyRelease(KeyEvent e) {
        System.out.println("LOGGING:  KeyEvent (onKeyRelease)!");
    }

}
