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
    private int x, y;
    private int dx, dy;

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

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

    public void update(){
        this.x += dx;
        this.y += dy;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
        this.update();
    }


    public Sprite(String path){

        try {
            image = ImageIO.read(Sprite.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        width = image.getWidth();
        height = image.getHeight();
        this.setPosition(0, 0);
        this.setDx(0);
        this.setDy(0);
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
        int w = image.getWidth();
        int h = image.getHeight();

        AffineTransform at = AffineTransform.getRotateInstance(rads, w * 0.5, h * 0.5);
        Rectangle rotatedBounds = at.createTransformedShape(new Rectangle(0, 0, w, h)).getBounds();
        BufferedImage result = new BufferedImage(rotatedBounds.width, rotatedBounds.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();
        g.setColor(Color.MAGENTA); // Alpha value
        g.fillRect(0, 0, rotatedBounds.width, rotatedBounds.height);
        at.preConcatenate(AffineTransform.getTranslateInstance(-rotatedBounds.x, -rotatedBounds.y));
        g.setTransform(at);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        width = result.getWidth();
        height = result.getHeight();
        pixels = result.getRGB(0, 0, width, height, null, 0, width);
    }

}
