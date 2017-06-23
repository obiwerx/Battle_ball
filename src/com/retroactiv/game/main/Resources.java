package com.retroactiv.game.main;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.awt.Color;

import javax.imageio.ImageIO;

public class Resources {

    public static BufferedImage welcome, iconimage, line;
    public static AudioClip hit, bounce;
    public static Color darkGreen, darkestGreen, lighestGreen;

    public static void load() {
        System.out.println("LOGGING:  Initializing assets...");
        welcome = loadImage("welcome.png");
        iconimage = loadImage("iconimage.png");
        line = loadImage("line.png");
        hit = loadSound("hit.wav");
        bounce = loadSound("bounce.wav");
        darkGreen = new Color(48, 98, 48);
        darkestGreen = new Color(15, 56, 15);
        lighestGreen = new Color(155, 188, 15);

        System.out.println("LOGGING:  All assets loaded.");
    }

    private static AudioClip loadSound(String filename) {
        URL fileURL = Resources.class.getResource("/resources/" + filename);
        return Applet.newAudioClip(fileURL);
    }

    private static BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
                img = ImageIO.read(Resources.class.getResourceAsStream("/resources/" + filename));
        } catch (Exception e) {
                System.out.println("ERROR:  Error while reading: " + filename);
                e.printStackTrace();
        }
        System.out.println("LOGGING:  Image: " + filename + " loaded successfully!");
        return img;
    }
}
