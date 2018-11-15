package io.kylepeeler.GameEngine.gfx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Sprite {
    private BufferedImage image = null;
    private int width, height;
    private int[] pixels;
    private int x;
    private int curAngle = 0;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    private int y;

    public Sprite(String path){

        try {
            image = ImageIO.read(Sprite.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        width = image.getWidth();
        height = image.getHeight();
        this.setPosition(0, 0);
        System.out.println("width: " + image.getWidth());
        System.out.println("height: " + image.getHeight());
        pixels = image.getRGB(0, 0, width, height, null, 0, width);
    }

    public Sprite(String path, int x, int y){
        image = null;

        try {
            image = ImageIO.read(Sprite.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        width = image.getWidth();
        height = image.getHeight();
        this.setPosition(x, y);
        System.out.println("width: " + image.getWidth());
        System.out.println("height: " + image.getHeight());
        pixels = image.getRGB(0, 0, width, height, null, 0, width);
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[] getPixels() {
        return pixels;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    public void rotate(double angle){
        curAngle += angle;
        double rads = Math.toRadians(curAngle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = image.getWidth();
        int h = image.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);
        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        //at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(image, 0, 0, null);
        g2d.setColor(Color.MAGENTA);
        g2d.drawRect(0, 0, newWidth - 1, newHeight - 1);
        g2d.dispose();
        width = rotated.getWidth();
        height = rotated.getHeight();
        pixels = rotated.getRGB(0, 0, width, height, null, 0, width);
    }

}
