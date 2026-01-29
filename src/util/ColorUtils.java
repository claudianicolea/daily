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

    public static final ColorUtils RED    = new ColorUtils(255, 140, 140, ColorName.RED);
    public static final ColorUtils ORANGE = new ColorUtils(255, 176, 140, ColorName.ORANGE);
    public static final ColorUtils YELLOW = new ColorUtils(255, 230, 140, ColorName.YELLOW);
    public static final ColorUtils GREEN  = new ColorUtils(163, 212, 144, ColorName.GREEN);
    public static final ColorUtils BLUE   = new ColorUtils(114, 212, 224, ColorName.BLUE);
    public static final ColorUtils PURPLE = new ColorUtils(144, 131, 201, ColorName.PURPLE);
    public static final ColorUtils PINK   = new ColorUtils(224, 146, 215, ColorName.PINK);
    public static final ColorUtils BROWN  = new ColorUtils(92, 71, 53, ColorName.BROWN);
    public static final ColorUtils BEIGE  = new ColorUtils(196, 169, 145, ColorName.BEIGE);
    public static final ColorUtils WHITE  = new ColorUtils(240, 240, 240, ColorName.WHITE);
    public static final ColorUtils GREY  = new ColorUtils(145, 145, 145, ColorName.GREY);

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