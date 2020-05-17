package net.sludgemod.sludge.shared.init

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Blocks
import net.minecraft.block.FluidBlock
import net.minecraft.util.registry.Registry
import net.sludgemod.sludge.shared.SludgeConstants

object Blocks {
    val SLUDGE_FLUID_BLOCK = object : FluidBlock(Fluids.STILL_SLUDGE, FabricBlockSettings.copy(Blocks.WATER)) {}

    internal fun register() {
        Registry.register(Registry.BLOCK, SludgeConstants.FluidIds.SLUDGE, SLUDGE_FLUID_BLOCK)
    }
}
