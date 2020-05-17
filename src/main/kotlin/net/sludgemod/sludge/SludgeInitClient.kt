package net.sludgemod.sludge

import net.fabricmc.api.ClientModInitializer
import net.sludgemod.sludge.client.init.Renderers
import net.sludgemod.sludge.client.init.Screens

object SludgeInitClient : ClientModInitializer {
    override fun onInitializeClient() {
        Renderers.register()
        Screens.register()
    }
}
