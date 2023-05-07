package net.bplaced.azoq.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StringUtils;

public class FontUtils {
    private static FontRenderer fontRenderer;
    
    public static void setupFontUtils() {
        FontUtils.fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    }
    
    public static int getStringWidth(final String text) {
        return FontUtils.fontRenderer.getStringWidth(StringUtils.stripControlCodes(text));
    }
    
    public static int getFontHeight() {
        return FontUtils.fontRenderer.FONT_HEIGHT;
    }
    
    public static void drawString(final String text, final double x, final double y, final int color) {
        FontUtils.fontRenderer.drawString(text, (int)x, (int)y, color);
    }
    
    public static void drawStringWithShadow(final String text, final double x, final double y, final int color) {
        FontUtils.fontRenderer.drawStringWithShadow(text, (float)x, (float)y, color);
    }
    
    public static void drawCenteredString(final String text, final double x, final double y, final int color) {
        drawString(text, x - FontUtils.fontRenderer.getStringWidth(text) / 2, y, color);
    }
    
    public static void drawCenteredStringWithShadow(final String text, final double x, final double y, final int color) {
        drawStringWithShadow(text, x - FontUtils.fontRenderer.getStringWidth(text) / 2, y, color);
    }
    
    public static void drawTotalCenteredString(final String text, final double x, final double y, final int color) {
        drawString(text, x - FontUtils.fontRenderer.getStringWidth(text) / 2, y - FontUtils.fontRenderer.FONT_HEIGHT / 2, color);
    }
    
    public static void drawTotalCenteredStringWithShadow(final String text, final double x, final double y, final int color) {
        drawStringWithShadow(text, x - FontUtils.fontRenderer.getStringWidth(text) / 2, y - FontUtils.fontRenderer.FONT_HEIGHT / 2.0f, color);
    }
}
