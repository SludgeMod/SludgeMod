package net.sludgemod.sludge

import net.fabricmc.api.ClientModInitializer
import net.sludgemod.sludge.client.init.Renderers

object SludgeInitClient : ClientModInitializer {
    override fun onInitializeClient() {
        Renderers.register()
    }
}
