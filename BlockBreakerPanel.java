package com.vany;

import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class BlockBreakerPanel extends JPanel implements KeyListener {

    private ArrayList<Block> blocks;
    private ArrayList<Block> ball;
    private ArrayList<Block> powerup;
    private Block paddle;
    private Thread thread;
    private Animate animate;
    private int size = 25;
    private boolean isPlayGameOverMusic = true;
    private boolean isStart = false;
    private boolean isGameOver;
    MediaPlayer m;
    BlockBreakerPanel(){
        init();
    }

    public void init(){
        m = Tools.initGameOverAudio("game_background.mp3");
        blocks = new ArrayList<Block>();
        ball = new ArrayList<Block>();
        powerup = new ArrayList<Block>();

        paddle = new Block(175, 480, 150, 25, "paddle.png");
        for(int i = 0; i < 8; i++){
            blocks.add(new Block((i*60+2), 0,60,25, "blue.png"));
        }
        for(int i = 0; i < 8; i++){
            blocks.add(new Block((i*60+2), 25,60,25, "red.png"));
        }
        for(int i = 0; i < 8; i++){
            blocks.add(new Block((i*60+2), 50,60,25, "green.png"));
        }
        for(int i = 0; i < 8; i++){
            blocks.add(new Block((i*60+2), 75,60,25, "yellow.png"));
        }
        Random random = new Random();
        blocks.get(random.nextInt(32)).powerup = true;
        blocks.get(random.nextInt(32)).powerup = true;
        blocks.get(random.nextInt(32)).powerup = true;
        blocks.get(random.nextInt(32)).powerup = true;
        blocks.get(random.nextInt(32)).powerup = true;
        blocks.get(random.nextInt(32)).powerup = true;
        ball.add(new Block(237, 437, 25, 25, "ball.png"));
        addKeyListener(this);
        setFocusable(true);
        isPlayGameOverMusic =true;
    }

    public void paintComponent(Graphics g){
        isGameOver = true;
        for(Block ba : ball){
            if(ba.y <= getHeight()){
                isGameOver = false;
            }
        }
        if(isGameOver){
            g.drawImage(Tools.getImage("wall.jpg"),0,0,490,600,null);
            g.setColor(Color.ORANGE);
            g.setFont(new Font("Dialog", Font.BOLD, 60));
            g.drawString("GAME OVER", 66, 250);
            g.setFont(new Font("Dialog", Font.BOLD, 40));
            g.drawString("PRESS ENTER", 100, 350);
            g.drawString("TO RESTART", 105, 400);

            if(isPlayGameOverMusic){
                m.play();
                isPlayGameOverMusic =false;
            }
        } else{
            super.paintComponent(g);
            g.drawImage(Tools.getImage("wall.jpg"),0,0,490,600,null);
            if(!isStart){
                g.setColor(Color.YELLOW);
                g.setFont(new Font("Dialog", Font.BOLD, 60));
                g.drawString("PRESS ENTER", 40, 250);
                g.setFont(new Font("Dialog", Font.BOLD, 60));
                g.drawString("TO START", 70, 350);
            }
            for(Block b : blocks){
                b.draw(g, this);
            }
            for(Block b : ball){
                b.draw(g, this);
            }
            for(Block p : powerup){
                p.draw(g, this);
            }
            paddle.draw(g, this);
        }

    }

    public void update() throws InterruptedException{

        for(Block p : powerup){
            if(!isGameOver){
                p.y+=1;
            }
            if(p.intersects(paddle) && !p.destroyed){
                p.destroyed = true;
                Tools.playAudio("shootingstar.wav");
                ball.add(new Block(paddle.x+75, 437, 25, 25, "ball.png"));
            }
        }
        for(Block ba : ball) {
            ba.x += ba.dx;
            if (ba.x > (getWidth() - size) && ba.dx > 0 || ba.x < 0) {
                ba.dx *= -1;
            }
            if (ba.y < 0 || ba.intersects(paddle)) {
                ba.dy *= -1;
            }
            ba.y += ba.dy;

            for (Block b : blocks) {
                if ((b.left.intersects(ba) || b.right.intersects(ba)) && !b.destroyed) {
                    ba.dx *= -1;
                    b.destroyed = true;
                    Tools.playAudio("pingpong.wav");
                    if (b.powerup) {
                        powerup.add(new Block(b.x, b.y, 25, 19, "extra.png"));
                    }
                } else if (ba.intersects(b) && !b.destroyed) {
                    b.destroyed = true;
                    Tools.playAudio("pingpong.wav");
                    ba.dy *= -1;
                    if (b.powerup) {
                        powerup.add(new Block(b.x, b.y, 25, 19, "extra.png"));
                    }
                }
            }
        }
        repaint();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(isGameOver){
                m.stop();
                init();
            }
            else {
                if(!isStart){
                    animate = new Animate(this);
                    thread = new Thread(animate);
                    thread.start();
                    isStart = true;
                }
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT && paddle.x > 0){
            paddle.x -= 25;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT && paddle.x < (getWidth()-paddle.width)){
            paddle.x += 25;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
