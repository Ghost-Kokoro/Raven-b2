package net.bplaced.azoq.module.modules.render;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventRenderPlayer;
import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.module.modules.combat.ModuleAntiBots;
import net.bplaced.azoq.module.modules.player.ModuleNoFriends;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

@Module.Information(moduleName = "NameTags", displayName = "NameTags", category = Module.Category.RENDER)
public class ModuleNameTags extends Module {
    @EventTarget
    public void onRenderNameTags(final EventRenderPlayer event) {
        if (event.getEntity() instanceof EntityPlayer && !ModuleAntiBots.isBot((Entity)event.getEntity())) {
            String str = event.getEntity().getName();
            if (!Client.INSTANCE.getModuleManager().getModule((Class)ModuleNoFriends.class).isEnabled() && Client.INSTANCE.getFriendManager().isFriend(str)) {
                str += " \247a[F]\247r";
            }
            int intping = 0;
            try {
                intping = ModuleNameTags.mc.getNetHandler().getPlayerInfo(event.getEntity().getUniqueID()).getResponseTime();
            }
            catch (Exception ex) {}
            final String ping = "\247e" + intping + " ms\247r | ";
            final String health = " | \247c" + event.getEntity().getHealth() / 2.0f + " \u2764\247r";
            str = ping + str + health;
            final double d0 = event.getEntity().getDistanceSqToEntity(ModuleNameTags.mc.getRenderManager().livingPlayer);
            final FontRenderer fontrenderer = ModuleNameTags.mc.fontRendererObj;
            final float f = (float)Math.sqrt(Math.sqrt(d0));
            final float f2 = 0.016666668f * f;
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)event.getX() + 0.0f, (float)event.getY() + event.getEntity().height + 0.5f, (float)event.getZ());
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-ModuleNameTags.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(ModuleNameTags.mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
            GlStateManager.scale(-f2, -f2, f2);
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            final Tessellator tessellator = Tessellator.getInstance();
            final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            final int i = 0;
            final int j = fontrenderer.getStringWidth(str) / 2;
            GlStateManager.disableTexture2D();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos((double)(-j - 1), (double)(-1 + i), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldrenderer.pos((double)(-j - 1), (double)(8 + i), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldrenderer.pos((double)(j + 1), (double)(8 + i), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldrenderer.pos((double)(j + 1), (double)(-1 + i), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, -1);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
        }
    }
}
