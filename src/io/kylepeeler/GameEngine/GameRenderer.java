package io.kylepeeler.GameEngine;

import io.kylepeeler.GameEngine.gfx.Font;
import io.kylepeeler.GameEngine.gfx.Sprite;
import io.kylepeeler.GameEngine.gfx.SpriteTile;

import java.awt.image.DataBufferInt;

public class GameRenderer {

    private int pixelWidth, pixelHeight;
    private int[] pixels;
    private Font font = Font.STANDARD;

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
        if (x < 0 || x >= pixelWidth || y < 0 || y >= pixelHeight) {
            //out of bounds, dont draw it
            return;
        }else if (value == 0xffff00ff){ // This is our alpha mask, #ff00ff
            return;
        }
        pixels[x + y * pixelWidth] = value;
    }

    public void renderString(String text, int offX, int offY, int color){
        text = text.toUpperCase();
        int offset = 0;
        for (int i = 0; i < text.length(); i++){
            int ascii = text.codePointAt(i) - 32;
            for(int y = 0; y < font.getFontSprite().getHeight(); y++){
                for (int x = 0; x < font.getWidths()[ascii]; x++){
                    if(font.getFontSprite().getPixels()[(x + font.getOffsets()[ascii]) + y * font.getFontSprite().getWidth()] == 0xffffffff){
                        setPixel(x + offX + offset, y + offY, color);
                    }
                }
            }
            offset += font.getWidths()[ascii];
        }
    }

    public void renderSprite(Sprite sprite){
        renderSprite(sprite, sprite.getX(), sprite.getY());
    }

    public void renderSprite(Sprite sprite, int offX, int offY){
        int newX, newY;
        newX = newY = 0;
        int newWidth = sprite.getWidth();
        int newHeight = sprite.getHeight();
//
//        // if sprite is completely off screen, don't need to draw
//        if (offX < -newWidth || offY < -newHeight || offX >= pixelWidth || offY >= pixelHeight) return;
//
//        // Only account for visible pixels that are on the screen, handle if it clips in any direction
//        if (offX < 0){ newX -= offX; }
//        if (offY < 0){ newY -= offY; }
//        if (newWidth + offX > pixelWidth){ newWidth -= newWidth + offX - pixelWidth; }
//        if (newHeight + offY > pixelHeight){ newHeight -= newHeight + offY - pixelHeight; }

        // Draw the pixels to the graphic buffer
        for (int x = newX; x < newWidth; x++){
            for (int y = newY; y < newHeight; y++){
                setPixel(x + offX, y + offY, sprite.getPixels()[x + y * sprite.getWidth()]);
            }
        }
    }

    public void renderSpriteTile(SpriteTile image, int offX, int offY, int tileX, int tileY){
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
