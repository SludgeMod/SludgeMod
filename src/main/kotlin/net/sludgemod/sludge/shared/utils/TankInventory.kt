package net.sludgemod.sludge.shared.utils

import alexiil.mc.lib.attributes.fluid.FluidVolumeUtil
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount
import alexiil.mc.lib.attributes.fluid.filter.FluidFilter
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv
import alexiil.mc.lib.attributes.fluid.volume.FluidKey
import net.minecraft.util.collection.DefaultedList

class TankInventory(tankCount: Int, tankCapacity: Long, private val fluidFilter: FluidFilter) :
    SimpleFixedFluidInv(tankCount, FluidAmount(tankCapacity)) {
    private var serverFluidCaches = DefaultedList.ofSize(tankCount, FluidVolumeUtil.EMPTY)

    fun hasChanged(): Boolean {
        var anyChanged = false;
        for ((index, serverFluidCache) in serverFluidCaches.withIndex()) {
            if (serverFluidCache != getInvFluid(index)) {
                serverFluidCaches[index] = getInvFluid(index).copy()
                anyChanged = true
            }
        }
        return anyChanged
    }

    override fun isFluidValidForTank(tank: Int, fluid: FluidKey): Boolean {
        return fluid.isEmpty || fluidFilter.matches(fluid)
    }

    override fun getFilterForTank(tank: Int) = fluidFilter
}
