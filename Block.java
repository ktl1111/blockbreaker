package com.vany;

import java.awt.*;

class Block extends Rectangle {

    private Image pic;
    int dx = 3;
    int dy = -3;
    Rectangle left, right;
    boolean destroyed = false;
    boolean powerup = false;
    Block(int a, int b, int w, int h, String s) {
        x = a;
        y = b;
        width = w;
        height = h;
        left = new Rectangle(a-1, b, 1, h);
        right = new Rectangle(a+w+1, b, 1, h);
        pic = Toolkit.getDefaultToolkit().getImage(s);
    }

    void draw(Graphics g, Component c){
        if(!destroyed){
            g.drawImage(pic, x, y, width, height, c);
        }
    }


}
