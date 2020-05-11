package net.sludgemod.sludge.shared.init

import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.registry.Registry
import net.sludgemod.sludge.shared.SludgeConstants
import net.sludgemod.sludge.shared.blockentities.SeparatorBlockEntity
import java.util.function.Supplier

object BlockEntities {
    lateinit var SEPARATOR_BLOCK_ENTITY: BlockEntityType<SeparatorBlockEntity>

    internal fun register() {
        SEPARATOR_BLOCK_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            SludgeConstants.BlockIds.SEPARATOR,
            BlockEntityType.Builder.create(Supplier { SeparatorBlockEntity() }, Blocks.SEPARATOR_BLOCK).build(null)
        )
    }
}
