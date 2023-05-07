package net.bplaced.azoq.mixin.mixins;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.module.modules.movement.ModuleVelocity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.IThreadListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ NetHandlerPlayClient.class })
public class MixinNetHandlerPlayClient {
    @Shadow
    private WorldClient clientWorldController;
    
    @Overwrite
    public void handleEntityVelocity(final S12PacketEntityVelocity packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)Minecraft.getMinecraft().getNetHandler(), (IThreadListener)Minecraft.getMinecraft());
        final Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityID());
        if (entity != null) {
            if (Client.INSTANCE.getModuleManager().getModule(ModuleVelocity.class).isEnabled() && Client.INSTANCE.getSettingsManager().getSettingByName("Velocity:Mode").getValString().equalsIgnoreCase("Legit AAC") && entity == Minecraft.getMinecraft().thePlayer) {
                entity.setVelocity(packetIn.getMotionX() / 8000.0 * 0.8, packetIn.getMotionY() / 8000.0, packetIn.getMotionZ() / 8000.0 * 0.8);
            }
            else {
                entity.setVelocity(packetIn.getMotionX() / 8000.0, packetIn.getMotionY() / 8000.0, packetIn.getMotionZ() / 8000.0);
            }
        }
    }
}
