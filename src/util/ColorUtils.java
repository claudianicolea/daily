package util;

public class ColorUtils {
    public enum ColorName {
        GREY,
        RED,
        ORANGE,
        YELLOW,
        GREEN,
        BLUE,
        PURPLE,
        PINK,
        BROWN,
        BEIGE,
        WHITE
    }

    private final int red, green, blue;
    private final ColorName colorName;

    public ColorUtils(int red, int green, int blue, ColorName colorName) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.colorName = colorName;
    }

    public static final ColorUtils RED    = new ColorUtils(168, 20, 20, ColorName.RED);
    public static final ColorUtils ORANGE = new ColorUtils(209, 80, 10, ColorName.ORANGE);
    public static final ColorUtils YELLOW = new ColorUtils(240, 196, 0, ColorName.YELLOW);
    public static final ColorUtils GREEN  = new ColorUtils(0, 143, 43, ColorName.GREEN);
    public static final ColorUtils BLUE   = new ColorUtils(0, 83, 143, ColorName.BLUE);
    public static final ColorUtils PURPLE = new ColorUtils(92, 0, 204, ColorName.PURPLE);
    public static final ColorUtils PINK   = new ColorUtils(204, 0, 136, ColorName.PINK);
    public static final ColorUtils BROWN  = new ColorUtils(64, 18, 0, ColorName.BROWN);
    public static final ColorUtils BEIGE  = new ColorUtils(181, 156, 147, ColorName.BEIGE);
    public static final ColorUtils WHITE  = new ColorUtils(240, 240, 240, ColorName.WHITE);
    public static final ColorUtils GREY  = new ColorUtils(100, 100, 100, ColorName.GREY);

    public int getRed()   { return red; }
    public int getGreen() { return green; }
    public int getBlue()  { return blue; }
    public ColorName getColorName() { return colorName; }
    public java.awt.Color toAwtColor() {
        return new java.awt.Color(red, green, blue);
    }
    public static ColorUtils fromName(ColorName name) {
        return switch (name) {
            case RED -> ColorUtils.RED;
            case ORANGE -> ColorUtils.ORANGE;
            case YELLOW -> ColorUtils.YELLOW;
            case GREEN -> ColorUtils.GREEN;
            case BLUE -> ColorUtils.BLUE;
            case PURPLE -> ColorUtils.PURPLE;
            case PINK -> ColorUtils.PINK;
            case BROWN -> ColorUtils.BROWN;
            case BEIGE -> ColorUtils.BEIGE;
            case WHITE -> ColorUtils.WHITE;
            default -> ColorUtils.GREY;
        };
    }
}