package util;

public class Color {
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
        BLACK
    }
    private int red, green, blue;
    private ColorName colorName;

    public Color() {
        setBlack();
        colorName = ColorName.BLACK;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public ColorName getColorName() {
        return colorName;
    }

    public void setRed() {
        red = 168;
        green = 20;
        blue = 20;
        colorName = ColorName.RED;
    }

    public void setOrange() {
        red = 209;
        green = 80;
        blue = 10;
        colorName = ColorName.ORANGE;
    }

    public void setYellow() {
        red = 240;
        green = 196;
        blue = 0;
        colorName = ColorName.YELLOW;
    }

    public void setGreen() {
        red = 0;
        green = 143;
        blue = 43;
        colorName = ColorName.GREEN;
    }

    public void setBlue() {
        red = 0;
        green = 83;
        blue = 143;
        colorName = ColorName.BLUE;
    }

    public void setPurple() {
        red = 92;
        green = 0;
        blue = 204;
        colorName = ColorName.PURPLE;
    }

    public void setPink() {
        red = 204;
        green = 0;
        blue = 136;
        colorName = ColorName.PINK;
    }

    public void setBrown() {
        red = 64;
        green = 18;
        blue = 0;
        colorName = ColorName.BROWN;
    }

    public void setBeige() {
        red = 181;
        green = 156;
        blue = 147;
        colorName = ColorName.BEIGE;
    }

    public void setWhite() {
        red = 240;
        green = 240;
        blue = 240;
        colorName = ColorName.WHITE;
    }

    public void setBlack() {
        red = 15;
        green = 15;
        blue = 15;
        colorName = ColorName.BLACK;
    }
}
