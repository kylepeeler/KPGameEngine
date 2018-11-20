package io.kylepeeler.Game;

import io.kylepeeler.GameEngine.AbstractGame;
import io.kylepeeler.GameEngine.GameContainer;
import io.kylepeeler.GameEngine.GameRenderer;
import io.kylepeeler.GameEngine.gfx.BoundAction;
import io.kylepeeler.GameEngine.gfx.Sprite;

import java.awt.event.KeyEvent;

public class BasicGame extends AbstractGame{
    private Sprite sprite, background, sprite2;

    public BasicGame(){
        background = new Sprite("/game-background.png");
        sprite = new Sprite("/test-circle.png");
        sprite2 = new Sprite("/ball2.png");
        sprite.setPosition(100, 100);
        sprite2.setPosition(300, 300);
        sprite.setBoundAction(BoundAction.WRAP);
    }

    @Override
    public void update(GameContainer gc, float dt) {
        if (gc.getInput().isKeyPressed(KeyEvent.VK_W)) {
           sprite.setSpeed(4);
        }
        if (gc.getInput().isKeyPressed(KeyEvent.VK_S)){
            sprite.setSpeed(0);
        }
        if (gc.getInput().isKeyPressed(KeyEvent.VK_A)) {
            sprite.changeAngleBy(-10);
        }
        if (gc.getInput().isKeyPressed(KeyEvent.VK_D)){
            sprite.changeAngleBy(10);
        }
        if (gc.getInput().isKeyPressed(KeyEvent.VK_E)){
            sprite.changeAngleBy(40.0);
        }
        if (gc.getInput().isKeyDown(KeyEvent.VK_Q)){
            sprite.changeAngleBy(-40.0);
        }
        if(gc.getInput().isKeyDown(KeyEvent.VK_SPACE)){
            sprite.setDx(0);
            sprite.setDy(0);
        }
        if (gc.getInput().isKeyDown(KeyEvent.VK_G)){
            //sprite.setDx(3);
        }
        if (gc.getInput().isKeyDown(KeyEvent.VK_R)){
            sprite.setPosition(gc.getWidth()/2, gc.getHeight()/2);
        }
        if (sprite.collidesWith(sprite2)){
            System.out.println("Detected collision!!!!");
        }

        sprite.update(gc);
        sprite2.setSpeed(0);
        sprite2.update(gc);
        System.out.println("Sprite position: " + sprite.getX() + ", " + sprite.getY());
    }

    @Override
    public void render(GameContainer gc, GameRenderer r) {
        r.renderSprite(background, gc.getWidth() / 2, gc.getHeight() / 2);
        r.renderSprite(sprite2);
        r.renderSprite(sprite);
    }

    public static void main (String args[]){
        GameContainer gc = new GameContainer(new BasicGame());
        gc.start();
    }
}
