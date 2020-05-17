package net.sludgemod.sludge.client.init

import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry
import net.sludgemod.sludge.client.screens.SeparatorHandledScreen
import net.sludgemod.sludge.shared.SludgeConstants

object Screens {
    internal fun register() {
        ScreenProviderRegistry.INSTANCE.registerFactory(SludgeConstants.BlockIds.SEPARATOR, ::SeparatorHandledScreen)
    }
}
