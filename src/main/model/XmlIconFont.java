package main.model;

import java.util.HashMap;

public class XmlIconFont {
    private HashMap<String, XmlIconFontModel> fonts;
    private int minValue;
    private int maxValue;

    public XmlIconFont(HashMap<String, XmlIconFontModel> fonts, int maxValue, int minValue) {
        this.fonts = fonts;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public HashMap<String, XmlIconFontModel> getFonts() {
        return fonts;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }
}