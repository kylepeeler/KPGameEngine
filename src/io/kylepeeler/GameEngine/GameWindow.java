package io.kylepeeler.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GameWindow {
    public JFrame getFrame() {
        return frame;
    }

    private JFrame frame;
    private BufferedImage image;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    private Canvas canvas;
    private BufferStrategy bufferStrat;
    private Graphics graphics;

    public GameWindow(GameContainer gc){
        image = new BufferedImage(gc.getWidth(), gc.getHeight(), BufferedImage.TYPE_INT_RGB);
        canvas = new Canvas();
        Dimension s = new Dimension((int)(gc.getWidth() * gc.getScale()), (int)(gc.getHeight() * gc.getScale()));
        canvas.setPreferredSize(s);
        canvas.setMaximumSize(s);
        canvas.setMinimumSize(s);

        frame = new JFrame(gc.getTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack(); // Set frame to the size of the canvas
        frame.setLocationRelativeTo(null); // Start in middle of screen
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrat = canvas.getBufferStrategy();
        graphics = bufferStrat.getDrawGraphics();
    }

    public void update(){
        graphics.drawImage(image,0, 0, canvas.getWidth(), canvas.getHeight(), null);
        bufferStrat.show();
    }
}
