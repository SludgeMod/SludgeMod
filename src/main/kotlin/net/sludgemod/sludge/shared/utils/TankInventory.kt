package net.sludgemod.sludge.shared.utils

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv

class TankInventory : SimpleFixedFluidInv(1, FluidAmount(5))
