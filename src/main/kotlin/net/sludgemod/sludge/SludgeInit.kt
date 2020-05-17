package net.sludgemod.sludge

import net.fabricmc.api.ModInitializer
import net.sludgemod.sludge.shared.init.*

object SludgeInit : ModInitializer {
    override fun onInitialize() {
        Items.register()
        Fluids.register()
        Blocks.register()
        BlockEntities.register()
        ScreenHandlers.register()
    }
}
