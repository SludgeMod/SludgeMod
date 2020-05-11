package net.sludgemod.sludge.shared.init

import net.fabricmc.fabric.api.container.ContainerProviderRegistry
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier
import net.sludgemod.sludge.shared.SludgeConstants
import net.sludgemod.sludge.shared.blockentities.SeparatorBlockEntity

object ScreenHandlers {
    internal fun register() {
        ContainerProviderRegistry.INSTANCE.registerFactory(
            SludgeConstants.BlockIds.SEPARATOR
        ) { syncId: Int, _: Identifier, player: PlayerEntity, buf: PacketByteBuf ->
            val blockEntity = player.world.getBlockEntity(buf.readBlockPos())
            (blockEntity as SeparatorBlockEntity?)?.createContainer(syncId, player.inventory)
        }
    }
}