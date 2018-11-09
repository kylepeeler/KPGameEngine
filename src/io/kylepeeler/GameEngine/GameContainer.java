package io.kylepeeler.GameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameContainer implements Runnable{

    private GameRenderer renderer;
    private Thread thread;
    private boolean isRunning = false;
    private final double FPS_CAP = 1.0 / 60.0;
    private  GameInput input;

    public GameWindow getWindow() {
        return window;
    }

    private GameWindow window;

    public GameContainer(){

    }

    public void start(){
        window = new GameWindow(this);
        renderer = new GameRenderer(this);
        input = new GameInput(this);
        thread = new Thread(this);
        thread.run();

    }

    public void stop(){

    }

    public void run(){
        isRunning = true;

        boolean shouldRender = false;
        double firstTime = 0;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime = 0;
        double unprocessedTime = 0;
        double frameTime = 0;
        int framesPassed = 0;
        int fps = 0;

        while (isRunning) {
            shouldRender = false;
            firstTime = System.nanoTime() / 1000000000.0; // Convert system time to seconds
            passedTime = firstTime - lastTime;
            lastTime = firstTime;
            unprocessedTime += passedTime;
            frameTime += passedTime;
            while(unprocessedTime >= FPS_CAP){
                unprocessedTime -= FPS_CAP;
                shouldRender = true;

                //TODO: update game
                if (input.isMouseButton(MouseEvent.BUTTON1)){
                    System.out.println("Button 1 is pressed");
                }
                System.out.println("Scroll is " + input.getScroll());
                input.updateInput();


                if (frameTime >= 1.0){
                    frameTime = 0;
                    fps = framesPassed;
                    framesPassed = 0;
                    System.out.println("FPS: " + fps);
                }
            }

            if (shouldRender){
                // TODO: render game
                renderer.clearScreen();
                window.update();
                framesPassed++;
            }else{
                try{
                    Thread.sleep(1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
        cleanUp();
    }

    public void cleanUp(){

    }

    public static void main(String args[]){
        GameContainer gc = new GameContainer();
        gc.start();
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

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private int width = 320, height = 240;
    private float scale = 4f;
    private String title = "KPEngine v1.0";


}
