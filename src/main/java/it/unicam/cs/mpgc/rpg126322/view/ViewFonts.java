package it.unicam.cs.mpgc.rpg126322.view;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

final class ViewFonts {
    private static final String[] EMOJI_FONT_CANDIDATES = {
            "Segoe UI Emoji",
            "Apple Color Emoji",
            "Noto Color Emoji",
            "Noto Emoji",
            "Segoe UI Symbol",
            "Twemoji Mozilla"
    };
    private static final String EMOJI_FONT_FAMILY = findFontFamily(EMOJI_FONT_CANDIDATES);

    private ViewFonts() {
    }

    static Font dialog(int style, int size) {
        return new Font(Font.DIALOG, style, size);
    }

    static Font emoji(int style, int size) {
        return new Font(EMOJI_FONT_FAMILY, style, size);
    }

    private static String findFontFamily(String... candidates) {
        try {
            String[] availableFonts = GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .getAvailableFontFamilyNames();

            for (String candidate : candidates) {
                for (String availableFont : availableFonts) {
                    if (availableFont.equalsIgnoreCase(candidate)) {
                        return availableFont;
                    }
                }
            }
        } catch (RuntimeException ignored) {
            return Font.DIALOG;
        }

        return Font.DIALOG;
    }
}
