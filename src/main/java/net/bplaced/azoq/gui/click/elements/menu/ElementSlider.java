package net.bplaced.azoq.gui.click.elements.menu;

import java.awt.Color;
import net.bplaced.azoq.gui.click.elements.Element;
import net.bplaced.azoq.gui.click.elements.ModuleButton;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.utils.ColorUtils;
import net.bplaced.azoq.utils.FontUtils;
import net.bplaced.azoq.utils.RenderUtils;
import net.minecraft.util.MathHelper;

public class ElementSlider extends Element {
    public boolean dragging;
    
    public ElementSlider(final ModuleButton iparent, final Setting iset) {
        this.parent = iparent;
        this.set = iset;
        this.dragging = false;
        super.setup();
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final String displayval = "" + Math.round(this.set.getValDouble() * 100.0) / 100.0;
        final boolean hoveredORdragged = this.isSliderHovered(mouseX, mouseY) || this.dragging;
        final Color temp = ColorUtils.getClickGUIColor();
        final int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), hoveredORdragged ? 250 : 200).getRGB();
        final int color2 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), hoveredORdragged ? 255 : 230).getRGB();
        final double percentBar = (this.set.getValDouble() - this.set.getMin()) / (this.set.getMax() - this.set.getMin());
        RenderUtils.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -15066598);
        FontUtils.drawString(this.setstrg, this.x + 1.0, this.y + 2.0, -1);
        FontUtils.drawString(displayval, this.x + this.width - FontUtils.getStringWidth(displayval), this.y + 2.0, -1);
        RenderUtils.drawRect(this.x, this.y + 12.0, this.x + this.width, this.y + 13.5, -15724528);
        RenderUtils.drawRect(this.x, this.y + 12.0, this.x + percentBar * this.width, this.y + 13.5, color);
        if (percentBar > 0.0 && percentBar < 1.0) {
            RenderUtils.drawRect(this.x + percentBar * this.width - 1.0, this.y + 12.0, this.x + Math.min(percentBar * this.width, this.width), this.y + 13.5, color2);
        }
        if (this.dragging) {
            final double diff = this.set.getMax() - this.set.getMin();
            final double val = this.set.getMin() + MathHelper.clamp_double((mouseX - this.x) / this.width, 0.0, 1.0) * diff;
            this.set.setValDouble(val);
        }
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isSliderHovered(mouseX, mouseY)) {
            return this.dragging = true;
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        this.dragging = false;
    }
    
    public boolean isSliderHovered(final int mouseX, final int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y + 11.0 && mouseY <= this.y + 14.0;
    }
}
