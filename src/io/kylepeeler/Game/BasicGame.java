package io.kylepeeler.Game;

import io.kylepeeler.GameEngine.AbstractGame;
import io.kylepeeler.GameEngine.GameContainer;
import io.kylepeeler.GameEngine.GameRenderer;
import io.kylepeeler.GameEngine.gfx.Sprite;

import java.awt.event.KeyEvent;

public class BasicGame extends AbstractGame{
    private Sprite sprite;

    public BasicGame(){
        sprite = new Sprite("/test-tall.png");
    }

    @Override
    public void update(GameContainer gc, float dt) {
        if (gc.getInput().isKeyPressed(KeyEvent.VK_DOWN)){
            sprite.setPosition(sprite.getX(), sprite.getY() + 1);
            System.out.println("key is down");
        }
        if (gc.getInput().isKeyPressed(KeyEvent.VK_UP)) {
            sprite.setPosition(sprite.getX(), sprite.getY() - 1);
        }
        if (gc.getInput().isKeyPressed(KeyEvent.VK_LEFT)) {
            sprite.setPosition(sprite.getX() - 1, sprite.getY());
        }
        if (gc.getInput().isKeyPressed(KeyEvent.VK_RIGHT)){
            sprite.setPosition(sprite.getX() + 1, sprite.getY());
        }
        if (gc.getInput().isKeyPressed(KeyEvent.VK_E)){
            sprite.rotate(10.0);
        }
        if (gc.getInput().isKeyPressed(KeyEvent.VK_Q)){
            sprite.rotate(-10.0);
        }
        System.out.println("Sprite position: " + sprite.getX() + ", " + sprite.getY());
            //if (gc.getInput().isKeyDown(KeyEvent.VK_KD))
        //sprite.setPosition(sprite.getX() + 1, sprite.getY());
    }

    @Override
    public void render(GameContainer gc, GameRenderer r) {

        r.renderSprite(sprite);
    }

    public static void main (String args[]){
        GameContainer gc = new GameContainer(new BasicGame());
        gc.start();
    }
}
