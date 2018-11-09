package io.kylepeeler.GameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameContainer implements Runnable{

    private GameRenderer renderer;
    private Thread thread;
    private boolean isRunning = false;
    private final double FPS_CAP = 1.0 / 60.0;

    public GameInput getInput() {
        return input;
    }

    private  GameInput input;
    private AbstractGame game;

    public  GameContainer(AbstractGame game){
        this.game = game;
    }

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

                game.update(this, (float) FPS_CAP);
                input.updateInput();


                if (frameTime >= 1.0){
                    frameTime = 0;
                    fps = framesPassed;
                    framesPassed = 0;
                    System.out.println("FPS: " + fps);
                }
            }

            if (shouldRender){
                renderer.clearScreen();
                game.render(this, renderer);
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

    private int width = 320;
    private int height = 240;
    private float scale = 4f;
    private String title = "KPEngine v1.0";

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


}
