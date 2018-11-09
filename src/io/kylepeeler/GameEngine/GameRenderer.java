package io.kylepeeler.GameEngine;

import java.awt.image.DataBufferInt;

public class GameRenderer {

    private int pixelWidth, pixelHeight;
    private int[] pixels;

    public GameRenderer(GameContainer gc){
        pixelHeight = gc.getWidth();
        pixelHeight = gc.getHeight();
        DataBufferInt dataBuffer = (DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer();
        pixels = dataBuffer.getData();
    }

    public void clearScreen(){
        for (int i = 0; i < pixels.length; i++){
            pixels[i] = 0;
        }
    }
}
