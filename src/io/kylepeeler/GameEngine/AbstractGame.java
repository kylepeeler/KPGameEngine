package io.kylepeeler.GameEngine;

public abstract class AbstractGame {
    public abstract void update (GameContainer gc, float dt);
    public abstract void render (GameContainer gc, GameRenderer r);
    public abstract void restart (GameContainer gc, GameRenderer r);
}
