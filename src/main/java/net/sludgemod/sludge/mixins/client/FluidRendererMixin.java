package net.sludgemod.sludge.mixins.client;

import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.fluid.Fluid;
import net.minecraft.tag.FluidTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FluidRenderer.class)
public class FluidRendererMixin {

    @Redirect(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/Fluid;matchesType(Lnet/minecraft/fluid/Fluid;)Z"),
            method = "getNorthWestCornerFluidHeight"
    )
    private boolean matchesType(Fluid first, Fluid second) {
        return first.matchesType(second) || (first.isIn(FluidTags.WATER) && second.isIn(FluidTags.WATER));
    }
}
