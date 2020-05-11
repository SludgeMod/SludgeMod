package net.sludgemod.sludge

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry
import net.sludgemod.sludge.client.screens.SeparatorHandledScreen
import net.sludgemod.sludge.shared.SludgeConstants
import net.sludgemod.sludge.shared.screenhandlers.SeparatorScreenHandler

object SludgeInitClient : ClientModInitializer {
    override fun onInitializeClient() {

        ScreenProviderRegistry.INSTANCE.registerFactory(
            SludgeConstants.Ids.SEPARATOR
        ) { screenHandler: SeparatorScreenHandler ->
            SeparatorHandledScreen(
                screenHandler
            )
        }
    }
}
