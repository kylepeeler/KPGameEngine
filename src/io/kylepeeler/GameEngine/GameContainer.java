package io.kylepeeler.GameEngine;

public class GameContainer implements Runnable{

    private GameRenderer renderer;
    private Thread thread;
    private boolean isRunning = false;
    private GameInput input;
    private AbstractGame game;
    private int width = 320;
    private int height = 240;
    private float scale = 4f;
    private final double FPS_CAP = 1 / 60.0;
    private String windowTitle = "KPEngine v0.1";
    private GameWindow window;



    public GameContainer(AbstractGame game){
        this.game = game;
    }

    public GameInput getInput() {
        return input;
    }
    public GameWindow getWindow() {
        return window;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public float getScale() {
        return scale;
    }
    public String getWindowTitle() {
        return windowTitle;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setScale(float scale) {
        this.scale = scale;
    }
    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }


    public void start(){
        window = new GameWindow(this);
        renderer = new GameRenderer(this);
        input = new GameInput(this);
        thread = new Thread(this);
        thread.run();
    }

    public void stop(){
        //TODO: implement
    }

    public void run(){
        isRunning = true;

        boolean shouldRender;
        double firstTime, passedTime, unprocessedTime, frameTime;
        int framesPassed = 0;
        int fps = 0;
        double lastTime = System.nanoTime() / 1000000000.0;
        unprocessedTime = frameTime = 0;

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
                }
            }

            if (shouldRender){
                renderer.clearScreen();
                game.render(this, renderer);
                renderer.drawString("FPS:" + fps, 0, 0, 0xff00ffff);
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


}
