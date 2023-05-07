package net.bplaced.azoq.module.modules.render;

import java.util.Iterator;
import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventRenderWorld;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

@Module.Information(moduleName = "ChestEsp", displayName = "ChestEsp", category = Module.Category.RENDER)
public class ModuleChestEsp extends Module {
	
	@Override
    public void setup() {
        super.setup();
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("ChestEsp:Chest", (Module)this, true));
        Client.INSTANCE.getSettingsManager().rSetting(new Setting("ChestEsp:EnderChest", (Module)this, true));
    }
    
    @EventTarget
    public void onRenderWorld(final EventRenderWorld event) {
        for (final TileEntity e : ModuleChestEsp.mc.theWorld.loadedTileEntityList) {
            if (Client.INSTANCE.getSettingsManager().getSettingByName("ChestEsp:Chest").getValBoolean() && e instanceof TileEntityChest) {
                renderChest(e.getPos());
            }
            if (Client.INSTANCE.getSettingsManager().getSettingByName("ChestEsp:EnderChest").getValBoolean() && e instanceof TileEntityEnderChest) {
                renderEnderChest(e.getPos());
            }
        }
    }
    
    public static void renderChest(final BlockPos blockPos) {
        final double x = blockPos.getX() - ModuleChestEsp.mc.getRenderManager().viewerPosX;
        final double y = blockPos.getY() - ModuleChestEsp.mc.getRenderManager().viewerPosY;
        final double z = blockPos.getZ() - ModuleChestEsp.mc.getRenderManager().viewerPosZ;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(true);
        GL11.glColor4d(255.0, 170.0, 0.0, 1.0);
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void renderEnderChest(final BlockPos blockPos) {
        final double x = blockPos.getX() - ModuleChestEsp.mc.getRenderManager().viewerPosX;
        final double y = blockPos.getY() - ModuleChestEsp.mc.getRenderManager().viewerPosY;
        final double z = blockPos.getZ() - ModuleChestEsp.mc.getRenderManager().viewerPosZ;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(true);
        GL11.glColor4d(170.0, 0.0, 170.0, 1.0);
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
}
