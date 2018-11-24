package io.kylepeeler.Game;

import io.kylepeeler.GameEngine.AbstractGame;
import io.kylepeeler.GameEngine.GameContainer;
import io.kylepeeler.GameEngine.GameRenderer;
import io.kylepeeler.GameEngine.gfx.BoundAction;
import io.kylepeeler.GameEngine.gfx.Font;
import io.kylepeeler.GameEngine.gfx.Sprite;

import java.awt.event.KeyEvent;

public class BasicGame extends AbstractGame{
    private Sprite spaceship, background, asteroid;
    private Sprite[] gems = new Sprite[10];
    private Sprite[] asteroids = new Sprite[5];
    private int score;
    private String gameText = "Current Score: 0";

    public BasicGame(){
        score = 0;
        background = new Sprite("/space-background.png");
        spaceship = new Sprite("/spaceship.png");
        asteroid = new Sprite("/asteroid.png");
        spaceship.setPosition(100, 100);
        createGems();
        createAsteroids();
        spaceship.setBoundAction(BoundAction.WRAP);
    }

    public void createGems(){
        for (int i = 0; i < 10; i++) {
            gems[i] = new Sprite("/gem.png");
            gems[i].setPosition((int) (Math.random() * 800), (int) (Math.random() * 600));
            while (gems[i].collidesWith(spaceship)) {
                gems[i].setPosition((int) (Math.random() * 800), (int) (Math.random() * 600));
            }
        }
    }

    public void createAsteroids(){
        for (int i = 0; i < 5; i++) {
            asteroids[i] = new Sprite("/asteroid.png");
            asteroids[i].setPosition((int) (Math.random() * 800), (int) (Math.random() * 600));
            while (asteroids[i].collidesWith(spaceship)) {
                asteroids[i].setPosition((int) (Math.random() * 800), (int) (Math.random() * 600));
            }
            asteroids[i].setSpeed(3);
            asteroids[i].setAngle((int) (Math.random() * 360));
            asteroids[i].setBoundAction(BoundAction.WRAP);
        }
    }

    @Override
    public void update(GameContainer gc, float dt) {

        if (gc.getInput().isKeyPressed(KeyEvent.VK_W)) {
           spaceship.setSpeed(3);
        }else if (gc.getInput().isKeyPressed(KeyEvent.VK_S)){
            spaceship.setSpeed(-3);
        }else{
            spaceship.setSpeed(0);
        }

        // Rotation
        if (gc.getInput().isKeyPressed(KeyEvent.VK_A)) {
            spaceship.changeAngleBy(-10);
        }
        if (gc.getInput().isKeyPressed(KeyEvent.VK_D)){
            spaceship.changeAngleBy(10);
        }

        spaceship.update(gc);

        for (Sprite gem: gems) {
            if (spaceship.collidesWith(gem)){
                score++;
                gameText = "Current Score: " + score;
                while (gem.collidesWith(spaceship)){
                    gem.setPosition((int) (Math.random() * 800), (int) (Math.random() * 600));
                }
            }
        }

        for (Sprite asteroid: asteroids){
            if (asteroid.collidesInclOffset(spaceship, 40)){
                gameText = "Game Over, Final Score: " + score + " - Press 'R' to restart game.";
                gc.stop();
            }
            asteroid.update(gc);
        }
        System.out.println("Spaceship position: " + spaceship.getX() + ", " + spaceship.getY());
    }

    @Override
    public void render(GameContainer gc, GameRenderer r) {
        r.renderSprite(background, gc.getWidth() / 2, gc.getHeight() / 2);
        r.renderSprite(spaceship);
        for (int i = 0; i < 10; i++){
            r.renderSprite(gems[i]);
        }
        for (int i = 0; i < 5; i++){
            r.renderSprite(asteroids[i]);
        }
        r.renderString(gameText, 5, gc.getHeight() - 20, 0xFFFFFFFF, Font.FontSize.LARGE);
    }

    @Override
    public void restart(GameContainer gc, GameRenderer r){
        // Recreate the asteroids and gems
        createGems();
        createAsteroids();
        // Reset the score and game text
        score = 0;
        gameText = "Current Score: " + score;
        gc.resume();
    }

    public static void main (String args[]){
        GameContainer gc = new GameContainer(new BasicGame());
        gc.setWindowTitle("Simple Asteroids - KPEngine v0.1");
        gc.start();
    }
}
