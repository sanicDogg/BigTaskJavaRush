package com.javarush.test.level34.lesson15.big01.model;

import java.awt.*;

public class Wall extends CollisionObject {
    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.GRAY);

        int leftUpperCornerX = getX() - getWidth() / 2;
        int leftUpperCornerY = getY() - getHeight() / 2;

        graphics.fillRect(leftUpperCornerX, leftUpperCornerY, getWidth(), getHeight());
    }
}
