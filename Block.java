package com.vany;

import java.awt.*;

class Block extends Rectangle {

    private Image pic;

    Block(int a, int b, int w, int h, String s) {
        x = a;
        y = b;
        width = w;
        height = h;
        pic = Toolkit.getDefaultToolkit().getImage(s);
    }

    void draw(Graphics g, Component c){
        g.drawImage(pic, x, y, width, height, c);
    }


}
