package io.kylepeeler.GameEngine.gfx;

public class Font  {

    public enum FontSize {
        STANDARD, SMALL, LARGE;
    }

    public static final Font STANDARD = new Font("/fonts/standard-font.png");
    public static final Font SMALL = new Font("/fonts/standard-font-micro.png");
    public static final Font LARGE = new Font("/fonts/large-font.png");

    private Sprite fontSprite;
    private int[] offsets;
    private int[] widths;

    public Sprite getFontSprite() {
        return fontSprite;
    }

    public int[] getOffsets() {
        return offsets;
    }

    public int[] getWidths() {
        return widths;
    }

    public Font(String path){
        fontSprite = new Sprite(path);
        offsets = new int[59];
        widths = new int[59];

        int ascii = 0;
        for (int i = 0; i < fontSprite.getWidth(); i++){
            if (fontSprite.getPixels()[i] == 0xff0000ff){
                offsets[ascii] = i;
            }
            if (fontSprite.getPixels()[i] == 0xffffff00){
                widths[ascii] = i - offsets[ascii];
                ascii++;
            }
        }
    }
}
