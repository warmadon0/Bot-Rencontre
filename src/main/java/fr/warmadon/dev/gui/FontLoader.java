package fr.warmadon.dev.gui;

import javafx.scene.text.Font;

public class FontLoader {
    public FontLoader() {
    }

    public void loadFont(GUIJFX res, String s) {
        Font.loadFont(this.getClass().getResourceAsStream(String.valueOf(GUIJFX.getResourceLocation()) + s), 14.0D);
    }

    public static void setFont(String fontName, float size) {
        Font.font(fontName, (double)size);
    }

    public static Font loadFont(String fullFont, String fontName, float size) {
        Font.loadFont(FontLoader.class.getResourceAsStream(String.valueOf(GUIJFX.getResourceLocation()) + fullFont), 14.0D);
        Font font = Font.font(fontName, (double)size);
        return font;
    }

    public static Font loadFontLocal(String fullFont, String fontName, float size) {
        Font.loadFont(FontLoader.class.getResourceAsStream(String.valueOf("/resources/") + fullFont), 14.0D);
        Font font = Font.font(fontName, (double)size);
        return font;
    }
}

