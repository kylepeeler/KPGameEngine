package io.kylepeeler.GameEngine;

import java.awt.event.*;

public class GameInput implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private GameContainer gc;
    private final int NUM_KEYBOARD_KEYS = 256;
    private boolean[] keys = new boolean[NUM_KEYBOARD_KEYS];
    private boolean[] keysLastFrame = new boolean[NUM_KEYBOARD_KEYS];

    private final int NUM_MOUSE_BTNS = 5;
    private boolean[] buttons = new boolean[NUM_MOUSE_BTNS];
    private  boolean[] buttonsLastFrame = new boolean[NUM_MOUSE_BTNS];

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getScroll() {
        return scroll;
    }

    public boolean isKey(int keyCode){
        return keys[keyCode];
    }

    public boolean isKeyUp(int keyCode){
        return !keys[keyCode] && keysLastFrame[keyCode];
    }

    public boolean isKeyDown(int keyCode){
        return keys[keyCode] && !keysLastFrame[keyCode];
    }

    public boolean isMouseButton(int button){
        return buttons[button];
    }

    public boolean isMouseButtonUp(int button){
        return !buttons[button] && buttonsLastFrame[button];
    }

    public boolean isMouseButtonDown(int button){
        return buttons[button] && !keysLastFrame[button];
    }

    private int mouseX, mouseY;
    private int scroll;

    public GameInput(GameContainer gc){
        this.gc = gc;
        mouseX = 0;
        mouseY = 0;
        scroll = 0;

        gc.getWindow().getCanvas().addKeyListener(this);
        gc.getWindow().getCanvas().addMouseMotionListener(this);
        gc.getWindow().getCanvas().addMouseListener(this);
        gc.getWindow().getCanvas().addMouseWheelListener(this);
    }

    public void updateInput(){
        scroll = 0;
        for(int i = 0; i < NUM_KEYBOARD_KEYS; i++){
            keysLastFrame[i] = keys[i];
        }

        for (int i = 0; i < NUM_MOUSE_BTNS; i++){
            buttonsLastFrame[i] = buttons[i];
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = (int)(e.getX() / gc.getScale());
        mouseY = (int)(e.getY() / gc.getScale());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();
    }
}
