package net.bplaced.azoq.utils;

import java.awt.Color;

import net.bplaced.azoq.Client;

public class ColorUtils {
    public static Color getClickGUIColor() {
        return new Color((int)Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:GuiRed").getValDouble(), (int)Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:GuiGreen").getValDouble(), (int)Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:GuiBlue").getValDouble());
    }
}
