package net.bplaced.azoq.gui.click.elements.menu;

import java.awt.Color;
import net.bplaced.azoq.gui.click.elements.Element;
import net.bplaced.azoq.gui.click.elements.ModuleButton;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.utils.ColorUtils;
import net.bplaced.azoq.utils.FontUtils;
import net.bplaced.azoq.utils.RenderUtils;

public class ElementCheckBox extends Element {
    public ElementCheckBox(final ModuleButton iparent, final Setting iset) {
        this.parent = iparent;
        this.set = iset;
        super.setup();
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final Color temp = ColorUtils.getClickGUIColor();
        final int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 200).getRGB();
        RenderUtils.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -15066598);
        FontUtils.drawString(this.setstrg, this.x + this.width - FontUtils.getStringWidth(this.setstrg), this.y + FontUtils.getFontHeight() / 2 - 0.5, -1);
        RenderUtils.drawRect(this.x + 1.0, this.y + 2.0, this.x + 12.0, this.y + 13.0, this.set.getValBoolean() ? color : -16777216);
        if (this.isCheckHovered(mouseX, mouseY)) {
            RenderUtils.drawRect(this.x + 1.0, this.y + 2.0, this.x + 12.0, this.y + 13.0, 1427181841);
        }
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isCheckHovered(mouseX, mouseY)) {
            this.set.setValBoolean(!this.set.getValBoolean());
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    public boolean isCheckHovered(final int mouseX, final int mouseY) {
        return mouseX >= this.x + 1.0 && mouseX <= this.x + 12.0 && mouseY >= this.y + 2.0 && mouseY <= this.y + 13.0;
    }
}
