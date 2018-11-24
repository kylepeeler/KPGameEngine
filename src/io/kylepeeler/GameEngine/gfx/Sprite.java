package io.kylepeeler.GameEngine.gfx;

import io.kylepeeler.GameEngine.GameContainer;

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
    private double dx = 0;
    private double dy = 0;
    private double imgAngle = 0;
    private double moveAngle = 0;
    private double speed = 0;
    private double scale = 1.0;
    private boolean visible = true;
    private BoundAction boundAction = BoundAction.WRAP;

    public Sprite(String path) {
        try {
            image = ImageIO.read(Sprite.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        width = image.getWidth();
        height = image.getHeight();
        this.setPosition(0, 0);
        pixels = image.getRGB(0, 0, width, height, null, 0, width);
    }

    public Sprite(String path, int x, int y) {
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

    // Sprite width & height setters/getters
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

    // Pixel array setters/getters
    public int[] getPixels() {
        return pixels;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    // X & Y setters/getters
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

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Change x & change y setters/getters
    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    // Update function called in update loop
    public void update(GameContainer gc) {
        this.x += dx;
        this.y += dy;
        this.checkBounds(gc);
    }

    // Movement functions
    public void calculateVector() {
        this.dx = this.speed * Math.cos(this.moveAngle);
        this.dy = this.speed * Math.sin(this.moveAngle);
    }

    public void calculateSpeedAngle() {
        this.speed = Math.sqrt(Math.pow(this.dx, 2) + Math.pow(this.dy, 2));
        this.moveAngle = Math.atan2(this.dy, this.dx);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
        this.calculateVector();
    }

    public double getSpeed() {
        this.speed = Math.sqrt(Math.pow(this.dx, 2) + Math.pow(this.dy, 2));
        return speed;
    }

    public void changeSpeedBy(double diff) {
        this.speed += diff;
        this.calculateVector();
    }

    public void setImgAngle(double degrees) {
        this.imgAngle = Math.toRadians(degrees);
        this.rotate(imgAngle);
    }

    public double getImgAngle() {
        return Math.toDegrees(this.imgAngle);
    }

    public void changeImgAngleBy(double degrees) {
        double rad = Math.toRadians(degrees);
        this.imgAngle += rad;
        rotate(imgAngle);
    }

    public void setMoveAngle(double degrees) {
        this.moveAngle = Math.toRadians(degrees);
        this.calculateVector();
    }

    public void changeMoveAngleBy(double degrees) {
        double diffRad = Math.toRadians(degrees);
        this.moveAngle += diffRad;
        this.calculateVector();
    }

    public double getMoveAngle() {
        return Math.toDegrees(this.moveAngle);
    }

    public void setAngle(double degrees) {
        this.setMoveAngle(degrees);
        this.setImgAngle(degrees);
    }

    public void changeAngleBy(double degrees) {
        this.changeMoveAngleBy(degrees);
        this.changeImgAngleBy(degrees);
    }

    public void addVector(double degrees, double thrust) {
        double rad = Math.toRadians(degrees);
        this.dx += thrust * Math.cos(rad);
        this.dy += thrust * Math.sin(rad);
        this.calculateSpeedAngle();
    }

    //Visibility setter/getters
    public boolean isVisible() {
        return visible;
    }

    public void hide() {
        this.visible = false;
    }

    public void show() {
        this.visible = true;
    }

    // BoundAction setters/getters
    public BoundAction getBoundAction() {
        return boundAction;
    }

    public void setBoundAction(BoundAction boundAction) {
        this.boundAction = boundAction;
    }

    public void checkBounds(GameContainer gc) {
        int rightBorder = gc.getWidth();
        int leftBorder = 0;
        int topBorder = 0;
        int bottomBorder = gc.getHeight();

        boolean offRight = false;
        boolean offLeft = false;
        boolean offTop = false;
        boolean offBottom = false;

        if (this.x > rightBorder) {
            offRight = true;
        }
        if (this.x < leftBorder) {
            offLeft = true;
        }
        if (this.y > bottomBorder) {
            offBottom = true;
        }
        if (this.y < topBorder) {
            offTop = true;
        }
        if (this.boundAction == BoundAction.BOUNCE) {
            if (offTop || offBottom) {
                this.dy *= -1;
                this.calculateSpeedAngle();
                this.imgAngle = this.moveAngle;
                rotate(imgAngle);
            }
            if (offLeft || offRight) {
                this.dx *= -1;
                this.calculateSpeedAngle();
                this.imgAngle = this.moveAngle;
                rotate(imgAngle);
            }
        } else if (this.boundAction == BoundAction.STOP) {
            if (offBottom || offLeft || offRight || offTop) {
                this.setSpeed(0);
            }
        } else if (this.boundAction == BoundAction.DIE) {
            if (offBottom || offLeft || offRight || offTop) {
                this.setSpeed(0);
                this.hide();
            }
        } else if (this.boundAction == BoundAction.WRAP) {
            if (offRight) {
                this.x = leftBorder;
            }
            if (offBottom) {
                this.y = topBorder;
            }
            if (offTop) {
                this.y = bottomBorder;
            }
            if (offLeft) {
                this.x = rightBorder;
            }
        }
    }

    // Collision accessors and checker method
    public int getLeftBoundary() {
        return this.getX() - (this.getWidth() / 2);
    }

    public int getRightBoundary() {
        return this.getX() + (this.getWidth() / 2);
    }

    public int getTopBoundary() {
        return getY() - (this.getHeight() / 2);
    }

    public int getBottomBoundary() {
        return getY() + (this.getHeight() / 2);
    }

    public boolean collidesWith(Sprite otherSprite){
        return collidesInclOffset(otherSprite, 0);
    }

    public boolean collidesInclOffset(Sprite otherSprite, int offset){
        boolean collision = false;
        if (this.visible){
            if (otherSprite.visible){
                //assume collision
                collision = true;
                if (this.getBottomBoundary() < otherSprite.getTopBoundary() + offset) {
                    collision = false;
                } else if (this.getTopBoundary() + offset > otherSprite.getBottomBoundary()) {
                    collision = false;
                } else if (this.getRightBoundary() < otherSprite.getLeftBoundary() + offset) {
                    collision = false;
                } else if (this.getLeftBoundary() + offset > otherSprite.getRightBoundary()) {
                    collision = false;
                }
            }
        }
        return collision;
    }

    // Sprite image rotation method, physically rotates the sprite pixels
    public void rotate(double rads) {
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
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        width = result.getWidth();
        height = result.getHeight();
        pixels = result.getRGB(0, 0, width, height, null, 0, width);
    }

}
