package com.retroactiv.game.main;

//imports//
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.retroactiv.game.state.State;
import com.retroactiv.game.state.LoadState;
import com.retroactiv.framework.util.InputHandler;

@SuppressWarnings("serial")

//Primary Game Class - builds a JPanel and custom Thread for engine to run in
public class Game extends JPanel implements Runnable{

    //CLASS VARIABLE DECLARATIONS//
    private int gameWidth;
    private int gameHeight;
    private Image gameImage;

    private InputHandler inputHandler;
    private Thread gameThread;
    private volatile boolean running;
    private volatile State currentState;

    //Game Method, sizes JPanel, sets color, requests OS focus
    public Game(int gameWidth, int gameHeight) {
        System.out.print("LOGGING:  Creating Game Object...");
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;

        setPreferredSize(new Dimension(gameWidth, gameHeight));
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocus();
        System.out.println("Done");
    }

    //Calls Garbage Collection and initializes new state
    public void setCurrentState(State newState) {
        System.gc();
        newState.init();
        System.out.print("LOGGING:  Changing State to " + newState + "...");
        currentState = newState;
        System.out.println("Done");
        inputHandler.setCurrentState(currentState);
    }

    //initializes new Thread for Game
    private void initGame() {
        running = true;
        gameThread = new Thread(this, "Game Thread");
        System.out.print("LOGGING:  Initializing Game...");
        System.out.println("Done");
        gameThread.start();
    }

    //This is where the main game loop is executed
    @Override
    public void run(){
        System.out.println("LOGGING:  Executing Game Loop...");

        //Framerate variables//
        long updateDurationMillis = 0;
        long sleepDurationMillis = 0;
        long beforeUpdateRender = System.nanoTime();
        long deltaMillis = updateDurationMillis + sleepDurationMillis;

        //Main Game Loop
        while (running) {

            updateAndRender(deltaMillis);
            updateDurationMillis = (System.nanoTime() - beforeUpdateRender) / 1000000L;
            sleepDurationMillis = Math.max(2, 17 - updateDurationMillis);
            try {
                Thread.sleep(14);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Done.  Exiting Game Loop.");
        System.exit(0);  //clean exit if running becomes false
    }

    private void updateAndRender(long deltaMillis) {
        currentState.update(deltaMillis / 1000f);
        prepareGameImage();
        currentState.render(gameImage.getGraphics());
        renderGameImage(getGraphics());
    }

    //prepares images for rendering
    private void prepareGameImage() {
        if (gameImage == null) {
            gameImage = createImage(gameWidth, gameHeight);
        }
        Graphics g = gameImage.getGraphics();
        g.clearRect(0, 0, gameWidth, gameHeight);
    }

    //required for multi-threading
    @Override
    public void addNotify() {
        super.addNotify();
        initInput();
        System.out.println("LOGGING:  Setting current State...");
        setCurrentState(new LoadState());
        System.out.println("LOGGING:  Executing initGame()...");
        initGame();
    }

    //calls the paintComponent method from the Graphics Superclass
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if (gameImage == null) {
            return;
        }
        g.drawImage(gameImage, 0, 0, null);
    }

    //Clean exit code
    public void exit() {
        System.out.print("LOGGING:  Exiting game...");
        running = false;
        System.out.println("Done");
    }

    private void renderGameImage(Graphics g) {
        if (gameImage != null) {
            g.drawImage(gameImage, 0, 0, null);
        }
        g.dispose();
    }

    private void initInput() {
        System.out.println("LOGGING:  Initializing input handlers...");
        inputHandler = new InputHandler();
        System.out.print("LOGGING:  Loading Keyboard Listener...");
        addKeyListener(inputHandler);
        System.out.println("Done");
        System.out.print("LOGGING:  Loading Mouse Listener...");
        addMouseListener(inputHandler);
        System.out.println("Done");
    }
}



