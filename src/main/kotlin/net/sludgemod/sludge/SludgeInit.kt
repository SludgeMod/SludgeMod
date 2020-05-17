package net.sludgemod.sludge

import net.fabricmc.api.ModInitializer
import net.sludgemod.sludge.shared.init.Blocks
import net.sludgemod.sludge.shared.init.Fluids
import net.sludgemod.sludge.shared.init.Items

object SludgeInit : ModInitializer {
    override fun onInitialize() {
        Items.register()
        Fluids.register()
        Blocks.register()
    }
}
