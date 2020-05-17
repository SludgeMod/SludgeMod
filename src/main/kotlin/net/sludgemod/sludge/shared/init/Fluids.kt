package net.sludgemod.sludge.shared.init

import alexiil.mc.lib.attributes.fluid.volume.FluidKey
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys
import alexiil.mc.lib.attributes.fluid.volume.SimpleFluidKey
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.sludgemod.sludge.shared.SludgeConstants
import net.sludgemod.sludge.shared.fluids.SludgeFluid

object Fluids {
    const val SLUDGE_COLOR = 0x964b13
    val STILL_SLUDGE = SludgeFluid.Still()
    val FLOWING_SLUDGE = SludgeFluid.Flowing()

    lateinit var SLUDGE_FLUID_KEY: FluidKey

    internal fun register() {
        Registry.register(Registry.FLUID, SludgeConstants.FluidIds.SLUDGE, STILL_SLUDGE)
        Registry.register(Registry.FLUID, Identifier(SludgeConstants.MOD_ID, "flowing_sludge"), FLOWING_SLUDGE)

        val fluidKeyBuilder = FluidKey.FluidKeyBuilder(STILL_SLUDGE)
            .setRenderColor(SLUDGE_COLOR)
            .setName(TranslatableText("block.sludge.sludge_fluid"))
        SLUDGE_FLUID_KEY = SimpleFluidKey(fluidKeyBuilder)
        FluidKeys.put(STILL_SLUDGE, SLUDGE_FLUID_KEY);
    }
}
