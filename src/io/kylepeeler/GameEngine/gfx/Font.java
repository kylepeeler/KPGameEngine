package io.kylepeeler.GameEngine.gfx;

public class Font  {

    public static final Font STANDARD = new Font("/fonts/standard-font.png");
    public static final Font SMALL = new Font("/fonts/standard-font-micro.png");

    private Image fontImage;
    private int[] offsets;
    private int[] widths;

    public Image getFontImage() {
        return fontImage;
    }

    public int[] getOffsets() {
        return offsets;
    }

    public int[] getWidths() {
        return widths;
    }

    public Font(String path){
        fontImage = new Image(path);
        offsets = new int[59];
        widths = new int[59];

        int ascii = 0;
        for (int i = 0; i < fontImage.getWidth(); i++){
            if (fontImage.getPixels()[i] == 0xff0000ff){
                offsets[ascii] = i;
            }
            if (fontImage.getPixels()[i] == 0xffffff00){
                widths[ascii] = i - offsets[ascii];
                ascii++;
            }
        }
    }
}
