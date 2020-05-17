package net.sludgemod.sludge.shared.init

import net.fabricmc.fabric.api.container.ContainerProviderRegistry
import net.sludgemod.sludge.shared.SludgeConstants
import net.sludgemod.sludge.shared.blockentities.SeparatorBlockEntity

object ScreenHandlers {
    internal fun register() {
        ContainerProviderRegistry.INSTANCE.registerFactory(
            SludgeConstants.BlockIds.SEPARATOR
        ) { syncId, _, player, buf ->
            val separatorBlockEntity = player.world.getBlockEntity(buf.readBlockPos()) as SeparatorBlockEntity
            separatorBlockEntity.createContainer(syncId, player.inventory)
        }
    }
}
