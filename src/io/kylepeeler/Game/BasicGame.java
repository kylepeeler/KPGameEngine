package io.kylepeeler.Game;

import io.kylepeeler.GameEngine.AbstractGame;
import io.kylepeeler.GameEngine.GameContainer;
import io.kylepeeler.GameEngine.GameRenderer;
import io.kylepeeler.GameEngine.gfx.Sprite;

import java.awt.event.KeyEvent;

public class BasicGame extends AbstractGame{
    private Sprite sprite, background;

    public BasicGame(){
        background = new Sprite("/game-background.png");
        sprite = new Sprite("/test-tall.png");
        sprite.setDx(3);
    }

    @Override
    public void update(GameContainer gc, float dt) {
        if (gc.getInput().isKeyPressed(KeyEvent.VK_S)){
            sprite.setPosition(sprite.getX(), sprite.getY() + 1);
        }
        if (gc.getInput().isKeyPressed(KeyEvent.VK_W)) {
            sprite.setPosition(sprite.getX(), sprite.getY() - 1);
        }
        if (gc.getInput().isKeyPressed(KeyEvent.VK_A)) {
            sprite.setPosition(sprite.getX() - 1, sprite.getY());
        }
        if (gc.getInput().isKeyPressed(KeyEvent.VK_D)){
            sprite.setPosition(sprite.getX() + 1, sprite.getY());
        }
        if (gc.getInput().isKeyPressed(KeyEvent.VK_E)){
            sprite.rotate(40.0);
        }
        if (gc.getInput().isKeyDown(KeyEvent.VK_Q)){
            sprite.rotate(-40.0);
        }
        if(gc.getInput().isKeyDown(KeyEvent.VK_SPACE)){
            sprite.setDx(0);
        }
        if (gc.getInput().isKeyDown(KeyEvent.VK_G)){
            sprite.setDx(3);
        }
        if (gc.getInput().isKeyDown(KeyEvent.VK_R)){
            sprite.setPosition(gc.getWidth()/2, gc.getHeight()/2);
        }

        sprite.update();
        System.out.println("Sprite position: " + sprite.getX() + ", " + sprite.getY());
            //if (gc.getInput().isKeyDown(KeyEvent.VK_KD))
        //sprite.setPosition(sprite.getX() + 1, sprite.getY());
    }

    @Override
    public void render(GameContainer gc, GameRenderer r) {
        r.renderSprite(background, gc.getWidth() / 2, gc.getHeight() / 2);
        r.renderSprite(sprite);
    }

    public static void main (String args[]){
        GameContainer gc = new GameContainer(new BasicGame());
        gc.start();
    }
}
