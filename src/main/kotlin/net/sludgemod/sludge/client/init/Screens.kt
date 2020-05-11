package net.sludgemod.sludge.client.init

import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry
import net.sludgemod.sludge.client.screen.SeparatorHandledScreen
import net.sludgemod.sludge.shared.SludgeConstants
import net.sludgemod.sludge.shared.screenhandlers.SeparatorScreenHandler

object Screens {
    internal fun register() {
        ScreenProviderRegistry.INSTANCE.registerFactory(
            SludgeConstants.BlockIds.SEPARATOR
        ) { screenHandler: SeparatorScreenHandler ->
            SeparatorHandledScreen(
                screenHandler
            )
        }
    }
}
