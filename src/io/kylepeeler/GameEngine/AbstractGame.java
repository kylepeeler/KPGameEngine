package io.kylepeeler.GameEngine;

import javax.swing.*;

public abstract class AbstractGame {
    public abstract void update (GameContainer gc, float dt);
    public abstract void render (GameContainer gc, GameRenderer r);
}
