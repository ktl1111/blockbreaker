package com.vany;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Tools{

    private static MediaPlayer mediaPlayer;

    static Image getImage(String imageName) {
        return new ImageIcon("assets/images/" + imageName).getImage();
    }


    static void playAudio(String fileName) {
        Media sound = new Media(new File("assets/audios/" + fileName).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

}
