package util;

public class MyColor {
    public enum ColorName {
        RED,
        ORANGE,
        YELLOW,
        GREEN,
        BLUE,
        PURPLE,
        PINK,
        BROWN,
        BEIGE,
        WHITE,
        GRAY
    }

    private final int red, green, blue;
    private final ColorName colorName;

    public MyColor(int red, int green, int blue, ColorName colorName) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.colorName = colorName;
    }

    public static final MyColor RED    = new MyColor(168, 20, 20, ColorName.RED);
    public static final MyColor ORANGE = new MyColor(209, 80, 10, ColorName.ORANGE);
    public static final MyColor YELLOW = new MyColor(240, 196, 0, ColorName.YELLOW);
    public static final MyColor GREEN  = new MyColor(0, 143, 43, ColorName.GREEN);
    public static final MyColor BLUE   = new MyColor(0, 83, 143, ColorName.BLUE);
    public static final MyColor PURPLE = new MyColor(92, 0, 204, ColorName.PURPLE);
    public static final MyColor PINK   = new MyColor(204, 0, 136, ColorName.PINK);
    public static final MyColor BROWN  = new MyColor(64, 18, 0, ColorName.BROWN);
    public static final MyColor BEIGE  = new MyColor(181, 156, 147, ColorName.BEIGE);
    public static final MyColor WHITE  = new MyColor(240, 240, 240, ColorName.WHITE);
    public static final MyColor GRAY  = new MyColor(100, 100, 100, ColorName.GRAY);

    public int getRed()   { return red; }
    public int getGreen() { return green; }
    public int getBlue()  { return blue; }
    public ColorName getColorName() { return colorName; }
    public java.awt.Color toAwtColor() {
        return new java.awt.Color(red, green, blue);
    }
    public static MyColor fromName(ColorName name) {
        return switch (name) {
            case RED -> MyColor.RED;
            case ORANGE -> MyColor.ORANGE;
            case YELLOW -> MyColor.YELLOW;
            case GREEN -> MyColor.GREEN;
            case BLUE -> MyColor.BLUE;
            case PURPLE -> MyColor.PURPLE;
            case PINK -> MyColor.PINK;
            case BROWN -> MyColor.BROWN;
            case BEIGE -> MyColor.BEIGE;
            case WHITE -> MyColor.WHITE;
            default -> MyColor.GRAY;
        };
    }
}