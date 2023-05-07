package net.bplaced.azoq.gui.click.elements;

import net.bplaced.azoq.gui.click.ClickGUI;
import net.bplaced.azoq.gui.click.elements.ModuleButton;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.utils.FontUtils;

public class Element {
    public ClickGUI clickgui;
    public ModuleButton parent;
    public Setting set;
    public double offset;
    public double x;
    public double y;
    public double width;
    public double height;
    public String setstrg;
    public boolean comboextended;
    
    public void setup() {
        this.clickgui = this.parent.parent.clickgui;
    }
    
    public void update() {
        this.x = this.parent.x + this.parent.width + 2.0;
        this.y = this.parent.y + this.offset;
        this.width = this.parent.width + 10.0;
        this.height = 15.0;
        final String sname = this.set.getName().split(":")[1];
        if (this.set.isCheck()) {
            this.setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
            final double textx = this.x + this.width - FontUtils.getStringWidth(this.setstrg);
            if (textx < this.x + 13.0) {
                this.width += this.x + 13.0 - textx + 1.0;
            }
        }
        else if (this.set.isCombo()) {
            this.height = (this.comboextended ? (this.set.getOptions().length * (FontUtils.getFontHeight() + 2) + 15) : 15.0);
            this.setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
            int longest = FontUtils.getStringWidth(this.setstrg);
            for (final String s : this.set.getOptions()) {
                final int temp = FontUtils.getStringWidth(s);
                if (temp > longest) {
                    longest = temp;
                }
            }
            final double textx2 = this.x + this.width - longest;
            if (textx2 < this.x) {
                this.width += this.x - textx2 + 1.0;
            }
        }
        else if (this.set.isSlider()) {
            this.setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
            final String displayval = "" + Math.round(this.set.getValDouble() * 100.0) / 100.0;
            final String displaymax = "" + Math.round(this.set.getMax() * 100.0) / 100.0;
            final double textx3 = this.x + this.width - FontUtils.getStringWidth(this.setstrg) - FontUtils.getStringWidth(displaymax) - 4.0;
            if (textx3 < this.x) {
                this.width += this.x - textx3 + 1.0;
            }
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        return this.isHovered(mouseX, mouseY);
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
    }
    
    public boolean isHovered(final int mouseX, final int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
    }
}
