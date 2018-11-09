package io.kylepeeler.Game;

import io.kylepeeler.GameEngine.AbstractGame;
import io.kylepeeler.GameEngine.GameContainer;
import io.kylepeeler.GameEngine.GameRenderer;
import io.kylepeeler.GameEngine.gfx.Image;

import java.awt.event.KeyEvent;

public class GameController extends AbstractGame{

    private Image image;
    public GameController(){
        image = new Image("/test-circle.png");
    }

    @Override
    public void update(GameContainer gc, float dt) {
        if (gc.getInput().isKeyDown(KeyEvent.VK_SPACE)){
            System.out.println("A was pressed");
        }
    }

    @Override
    public void render(GameContainer gc, GameRenderer r) {
        r.drawImage(image, gc.getInput().getMouseX() - 32, gc.getInput().getMouseY() - 32);
    }

    public static void main (String args[]){
        GameContainer gc = new GameContainer(new GameController());
        gc.start();
    }
}
