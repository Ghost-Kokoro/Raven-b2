package net.bplaced.azoq.gui.click.elements.menu;

import java.awt.Color;
import net.bplaced.azoq.Client;
import net.bplaced.azoq.gui.click.elements.Element;
import net.bplaced.azoq.gui.click.elements.ModuleButton;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.utils.ColorUtils;
import net.bplaced.azoq.utils.FontUtils;
import net.bplaced.azoq.utils.RenderUtils;
import net.minecraft.client.Minecraft;

public class ElementComboBox extends Element {
    public ElementComboBox(final ModuleButton iparent, final Setting iset) {
        this.parent = iparent;
        this.set = iset;
        super.setup();
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final Color temp = ColorUtils.getClickGUIColor();
        final int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150).getRGB();
        RenderUtils.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -15066598);
        FontUtils.drawTotalCenteredString(this.setstrg, this.x + this.width / 2.0, this.y + 7.0, -1);
        final int clr1 = color;
        final int clr2 = temp.getRGB();
        RenderUtils.drawRect(this.x, this.y + 14.0, this.x + this.width, this.y + 15.0, 1996488704);
        if (this.comboextended) {
            RenderUtils.drawRect(this.x, this.y + 15.0, this.x + this.width, this.y + this.height, -1441656302);
            double ay = this.y + 15.0;
            for (final String sld : this.set.getOptions()) {
                final String elementtitle = sld.substring(0, 1).toUpperCase() + sld.substring(1, sld.length());
                FontUtils.drawCenteredString(elementtitle, this.x + this.width / 2.0, ay + 2.0, -1);
                if (sld.equalsIgnoreCase(this.set.getValString())) {
                    RenderUtils.drawRect(this.x, ay, this.x + 1.5, ay + FontUtils.getFontHeight() + 2.0, clr1);
                }
                if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= ay && mouseY < ay + FontUtils.getFontHeight() + 2.0) {
                    RenderUtils.drawRect(this.x + this.width - 1.2, ay, this.x + this.width, ay + FontUtils.getFontHeight() + 2.0, clr2);
                }
                ay += FontUtils.getFontHeight() + 2;
            }
        }
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0) {
            if (this.isButtonHovered(mouseX, mouseY)) {
                this.comboextended = !this.comboextended;
                return true;
            }
            if (!this.comboextended) {
                return false;
            }
            double ay = this.y + 15.0;
            for (final String slcd : this.set.getOptions()) {
                if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= ay && mouseY <= ay + FontUtils.getFontHeight() + 2.0) {
                    if (Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:Sound").getValBoolean()) {
                        Minecraft.getMinecraft().thePlayer.playSound("tile.piston.in", 20.0f, 20.0f);
                    }
                    if (this.clickgui != null && this.clickgui.setmgr != null) {
                        this.clickgui.setmgr.getSettingByName(this.set.getName()).setValString(slcd.toLowerCase());
                    }
                    return true;
                }
                ay += FontUtils.getFontHeight() + 2;
            }
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    public boolean isButtonHovered(final int mouseX, final int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + 15.0;
    }
}
