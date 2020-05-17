package net.sludgemod.sludge.shared.init

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.sludgemod.sludge.shared.SludgeConstants
import net.sludgemod.sludge.shared.fluids.SludgeFluid

object Fluids {
    const val SLUDGE_COLOR = 0x964b13
    val STILL_SLUDGE = SludgeFluid.Still()
    val FLOWING_SLUDGE = SludgeFluid.Flowing()

    internal fun register() {
        Registry.register(Registry.FLUID, SludgeConstants.FluidIds.SLUDGE, STILL_SLUDGE)
        Registry.register(Registry.FLUID, Identifier(SludgeConstants.MOD_ID, "flowing_sludge"), FLOWING_SLUDGE)
    }
}
