package net.bplaced.azoq.mixin.mixins;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.module.modules.render.ModuleXRay;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ Block.class })
public class MixinBlock {
    @Inject(method = "shouldSideBeRendered", at = { @At("HEAD") }, cancellable = true)
    public void shouldSideBeRendered(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (Client.INSTANCE.getModuleManager().getModule(ModuleXRay.class).isEnabled()) {
            callbackInfoReturnable.setReturnValue(ModuleXRay.xrayBlocks.contains((Block)(Object)this));
        }
    }
}
