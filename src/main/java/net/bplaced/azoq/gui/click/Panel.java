package net.bplaced.azoq.gui.click;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import net.bplaced.azoq.Client;
import net.bplaced.azoq.file.files.FileClickGui;
import net.bplaced.azoq.gui.click.ClickGUI;
import net.bplaced.azoq.gui.click.elements.ModuleButton;
import net.bplaced.azoq.utils.ColorUtils;
import net.bplaced.azoq.utils.FontUtils;
import net.bplaced.azoq.utils.RenderUtils;
public class Panel {
    public String title;
    public double x;
    public double y;
    private double x2;
    private double y2;
    public double width;
    public double height;
    public boolean dragging;
    public boolean extended;
    public boolean visible;
    public ArrayList<ModuleButton> Elements;
    public ClickGUI clickgui;
    
    public Panel(final String ititle, final double ix, final double iy, final double iwidth, final double iheight, final boolean iextended, final ClickGUI parent) {
        this.Elements = new ArrayList<ModuleButton>();
        this.title = ititle;
        this.x = ix;
        this.y = iy;
        this.width = iwidth;
        this.height = iheight;
        this.extended = iextended;
        this.dragging = false;
        this.visible = true;
        this.clickgui = parent;
        this.setup();
    }
    
    public void setup() {
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (!this.visible) {
            return;
        }
        if (this.dragging) {
            this.x = this.x2 + mouseX;
            this.y = this.y2 + mouseY;
            for (int i = 0; i < FileClickGui.clickGui.size(); ++i) {
                final String[] args = FileClickGui.clickGui.get(i).split(":");
                if (this.title.equalsIgnoreCase(args[0])) {
                    FileClickGui.clickGui.set(i, args[0] + ":" + (int)this.x + ":" + (int)this.y + ":" + args[3]);
                }
            }
            Client.INSTANCE.getFileManager().getFile((Class)FileClickGui.class).save();
        }
        final Color temp = ColorUtils.getClickGUIColor().darker();
        final int outlineColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 170).getRGB();
        RenderUtils.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -15592942);
        if (Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:Design").getValString().equalsIgnoreCase("New")) {
            RenderUtils.drawRect(this.x - 2.0, this.y, this.x, this.y + this.height, outlineColor);
            FontUtils.drawStringWithShadow(this.title, this.x + 2.0, this.y + this.height / 2.0 - FontUtils.getFontHeight() / 2, -1052689);
        }
        else if (Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:Design").getValString().equalsIgnoreCase("JellyLike")) {
            RenderUtils.drawRect(this.x + 4.0, this.y + 2.0, this.x + 4.3, this.y + this.height - 2.0, -5592406);
            RenderUtils.drawRect(this.x - 4.0 + this.width, this.y + 2.0, this.x - 4.3 + this.width, this.y + this.height - 2.0, -5592406);
            FontUtils.drawTotalCenteredStringWithShadow(this.title, this.x + this.width / 2.0, this.y + this.height / 2.0, -1052689);
        }
        if (this.extended && !this.Elements.isEmpty()) {
            double startY = this.y + this.height;
            final int epanelcolor = Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:Design").getValString().equalsIgnoreCase("New") ? -14474461 : (Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:Design").getValString().equalsIgnoreCase("JellyLike") ? -1156246251 : 0);
            for (final ModuleButton et : this.Elements) {
                if (Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:Design").getValString().equalsIgnoreCase("New")) {
                    RenderUtils.drawRect(this.x - 2.0, startY, this.x + this.width, startY + et.height + 1.0, outlineColor);
                }
                RenderUtils.drawRect(this.x, startY, this.x + this.width, startY + et.height + 1.0, epanelcolor);
                et.x = this.x + 2.0;
                et.y = startY;
                et.width = this.width - 4.0;
                et.drawScreen(mouseX, mouseY, partialTicks);
                startY += et.height + 1.0;
            }
            RenderUtils.drawRect(this.x, startY + 1.0, this.x + this.width, startY + 1.0, epanelcolor);
        }
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (!this.visible) {
            return false;
        }
        if (mouseButton == 0 && this.isHovered(mouseX, mouseY)) {
            this.x2 = this.x - mouseX;
            this.y2 = this.y - mouseY;
            return this.dragging = true;
        }
        if (mouseButton == 1 && this.isHovered(mouseX, mouseY)) {
            this.extended = !this.extended;
            for (int i = 0; i < FileClickGui.clickGui.size(); ++i) {
                final String[] args = FileClickGui.clickGui.get(i).split(":");
                if (this.title.equalsIgnoreCase(args[0])) {
                    FileClickGui.clickGui.set(i, args[0] + ":" + args[1] + ":" + args[2] + ":" + this.extended);
                }
            }
            Client.INSTANCE.getFileManager().getFile((Class)FileClickGui.class).save();
            return true;
        }
        if (this.extended) {
            for (final ModuleButton et : this.Elements) {
                if (et.mouseClicked(mouseX, mouseY, mouseButton)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (!this.visible) {
            return;
        }
        if (state == 0) {
            this.dragging = false;
        }
    }
    
    public boolean isHovered(final int mouseX, final int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
    }
}
