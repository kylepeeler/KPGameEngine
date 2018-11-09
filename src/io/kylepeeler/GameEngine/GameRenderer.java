package io.kylepeeler.GameEngine;

import io.kylepeeler.GameEngine.gfx.Image;
import io.kylepeeler.GameEngine.gfx.ImageTile;

import java.awt.image.DataBufferInt;

public class GameRenderer {

    private int pixelWidth, pixelHeight;
    private int[] pixels;

    public GameRenderer(GameContainer gc){
        pixelWidth = gc.getWidth();
        pixelHeight = gc.getHeight();
        pixels = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }

    public void clearScreen(){
        for (int i = 0; i < pixels.length; i++){
            pixels[i] = 0;
        }
    }

    public void setPixel(int x, int y, int value) {
        //System.out.println("pixel width is " + pixelWidth + "pixelHeight is " + pixelHeight);
        if (x < 0 || x >= pixelWidth || y < 0 || y >= pixelHeight) {
            //out of bounds, dont draw it
            return;
        }else if (value == 0xffff00ff){ // This is our alpha mask, #ff00ff
            return;
        }
        pixels[x + y * pixelWidth] = value;
    }

    public void drawImage(Image image, int offX, int offY){
        int newX = 0;
        int newY = 0;
        int newWidth = image.getWidth();
        int newHeight = image.getHeight();

        // image is completely off screen, don't need to draw
        if (offX < -newWidth || offY < -newHeight || offX >= pixelWidth || offY >= pixelHeight) return;

        if (offX < 0){
            // Only account for visible pixels that are on the screen, handle if it goes off the screen to far left
            newX -= offX;
        }

        if (offY < 0){
            // Only account for visible pixels that are on the screen, handle if it goes off the screen past the top
            newY -= offY;
        }

        if (newWidth + offX > pixelWidth){
            // Only account for visible pixels that are on the screen, handle if it goes off the screen to far right
            newWidth -= newWidth + offX - pixelWidth;
        }

        if (newHeight + offY > pixelHeight){
            // Only account for visible pixels that are on the screen, handle if it goes off the screen past bottom
            newHeight -= newHeight + offY - pixelHeight;
        }

        for (int x = newX; x < newWidth; x++){
            for (int y = newY; y < newHeight; y++){
                setPixel(x + offX, y + offY, image.getPixels()[x + y * image.getWidth()]);
            }
        }
    }

    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY){
        int newX = 0;
        int newY = 0;
        int newWidth = image.getTileW();
        int newHeight = image.getTileH();

        // image is completely off screen, don't need to draw
        if (offX < -newWidth || offY < -newHeight || offX >= pixelWidth || offY >= pixelHeight) return;

        if (offX < 0){
            // Only account for visible pixels that are on the screen, handle if it goes off the screen to far left
            newX -= offX;
        }

        if (offY < 0){
            // Only account for visible pixels that are on the screen, handle if it goes off the screen past the top
            newY -= offY;
        }

        if (newWidth + offX > pixelWidth){
            // Only account for visible pixels that are on the screen, handle if it goes off the screen to far right
            newWidth -= newWidth + offX - pixelWidth;
        }

        if (newHeight + offY > pixelHeight){
            // Only account for visible pixels that are on the screen, handle if it goes off the screen past bottom
            newHeight -= newHeight + offY - pixelHeight;
        }

        for (int x = newX; x < newWidth; x++){
            for (int y = newY; y < newHeight; y++){
                setPixel(x + offX, y + offY, image.getPixels()[(x + tileX * image.getTileW()) + (y + tileY * image.getTileH()) * image.getWidth()]);
            }
        }
    }
}
