package com.retroactiv.game.state;

import com.retroactiv.game.main.GameMain;
import com.retroactiv.game.main.Resources;
import com.retroactiv.game.model.Paddle;
import com.retroactiv.game.model.Ball;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class PlayState extends State {

    private Paddle paddleLeft, paddleRight;
    private static final int PADDLE_WIDTH = 15;
    private static final int PADDLE_HEIGHT = 60;

    private Ball ball;
    private static final int BALL_DIAMETER = 20;

    private int playerScore = 0;
    private Font scoreFont;

    @Override
    public void init() {
        System.out.print("LOGGING:  Instantiating Paddles...");
        paddleLeft = new Paddle(0, 195, PADDLE_WIDTH, PADDLE_HEIGHT);
        paddleRight = new Paddle(785, 195, PADDLE_WIDTH, PADDLE_HEIGHT);
        System.out.println("done.");
        System.out.print("LOGGING:  Loading Fonts...");
        scoreFont = new Font("SansSerif", Font.BOLD, 25);
        System.out.println("Done.");
        System.out.print("LOGGING:  Instantiating Ball...");
        ball = new Ball(300, 200, BALL_DIAMETER, BALL_DIAMETER);
        System.out.println("Done.");
    }

    @Override
    public void update(float delta) {
        paddleLeft.update();
        paddleRight.update();
        ball.update();

        if(ballCollides(paddleLeft)) {
            playerScore++;
            ball.onCollideWith(paddleLeft);
            System.out.println("LOGGING:  Collision detected - PaddleLeft.");
            Resources.hit.play();
        }else if(ballCollides(paddleRight)) {
            playerScore++;
            ball.onCollideWith(paddleRight);
            System.out.println("LOGGING:  Collision detected - PaddleRight.");
            Resources.hit.play();
        }else if(ball.isDead()) {
            playerScore -= 3;
            System.out.println("LOGGING:  Ball left play area.");
            ball.reset();
        }
    }

    @Override
    public void render(Graphics g) {
        //Draw Background
        g.setColor(Resources.darkGreen);
        g.fillRect(0, 0, GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT);
        g.setColor(Resources.darkestGreen);
        g.fillRect(GameMain.GAME_WIDTH / 2, 0, GameMain.GAME_WIDTH / 2, GameMain.GAME_HEIGHT);

        //Draw Net
        g.drawImage(Resources.line, (GameMain.GAME_WIDTH / 2) - 2, 0, null);

        //Draw Paddles
        g.setColor(Resources.lighestGreen);
        g.fillRect(paddleLeft.getX(), paddleLeft.getY(), paddleLeft.getWidth(), paddleLeft.getHeight());
        g.fillRect(paddleRight.getX(), paddleRight.getY(), paddleRight.getWidth(), paddleRight.getHeight());

        //Draw Ball
        g.drawRect(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());

        //Draw UI
        g.setFont(scoreFont);
        g.drawString("" + playerScore, 350, 40);
    }

    public void onClick(MouseEvent e) {

    }

    public void onKeyPress(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            paddleLeft.accelUp();
            paddleRight.accelDown();
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            paddleLeft.accelDown();
            paddleRight.accelUp();
        }
    }

    public void onKeyRelease(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            paddleLeft.stop();
            paddleRight.stop();
        }
    }

    public boolean ballCollides(Paddle p) {
        return ball.getRect().intersects(p.getRect());
    }
}
