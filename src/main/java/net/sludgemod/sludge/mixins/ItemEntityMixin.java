package net.sludgemod.sludge.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sludgemod.sludge.SludgeInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {

    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract ItemStack getStack();

    @Inject(
            at = @At("HEAD"),
            method = "applyBuoyancy"
    )
    private void injectMethod(CallbackInfo info) {
        ItemStack itemStack = this.getStack();
        if (itemStack.getItem() == SludgeInit.INSTANCE.getSLUDGE_ITEM()) {
            if (itemStack.getCount() < 10) {
                return;
            }

            BlockPos blockPos = new BlockPos(this.getX(), this.getEyeY(), this.getZ());

            FluidState fluidState = this.world.getFluidState(blockPos);

            if (Fluids.WATER.getStill() == fluidState.getFluid()) {
                itemStack.decrement(10);
                this.world.setBlockState(blockPos, SludgeInit.INSTANCE.getSLUDGE_FLUID_BLOCK().getFluidState(fluidState.getBlockState()).getBlockState());
            }
        }
    }
}
