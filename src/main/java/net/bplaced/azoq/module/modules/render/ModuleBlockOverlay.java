package net.bplaced.azoq.module.modules.render;

import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventRenderWorld;
import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.utils.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.opengl.GL11;

@Module.Information(moduleName = "BlockOverlay", displayName = "BlockOverlay", category = Module.Category.RENDER)
public class ModuleBlockOverlay extends Module {
    @EventTarget
    public void onRenderWorld(final EventRenderWorld event) {
        final MovingObjectPosition rayTraceResult = ModuleBlockOverlay.mc.objectMouseOver;
        if (rayTraceResult.entityHit != null) {
            return;
        }
        final Block block = ModuleBlockOverlay.mc.theWorld.getBlockState(rayTraceResult.getBlockPos()).getBlock();
        final BlockPos blockPos = rayTraceResult.getBlockPos();
        if (Block.getIdFromBlock(block) == 0) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glEnable(2884);
        GL11.glDisable(2929);
        GL11.glTranslated(-ModuleBlockOverlay.mc.getRenderManager().viewerPosX, -ModuleBlockOverlay.mc.getRenderManager().viewerPosY, -ModuleBlockOverlay.mc.getRenderManager().viewerPosZ);
        GL11.glTranslated((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ());
        GL11.glColor4f(0.0f, 0.0f, 255.0f, 0.65f);
        RenderUtils.drawBlockOverlay(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0));
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
}
