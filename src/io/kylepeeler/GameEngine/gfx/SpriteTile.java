package io.kylepeeler.GameEngine.gfx;

public class SpriteTile extends Sprite {
    private int tileW;

    public int getTileW() {
        return tileW;
    }

    public void setTileW(int tileW) {
        this.tileW = tileW;
    }

    public int getTileH() {
        return tileH;
    }

    public void setTileH(int tileH) {
        this.tileH = tileH;
    }

    private int tileH;
    public SpriteTile(String path, int tileW, int tileH){
        super(path);
        this.tileW = tileW;
        this.tileH = tileH;
    }
}
