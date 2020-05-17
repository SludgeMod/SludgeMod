package net.sludgemod.sludge.shared.init

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.sludgemod.sludge.shared.SludgeConstants

object ItemGroups {
    val SLUDGE_ITEM_GROUP: ItemGroup = FabricItemGroupBuilder.create(Identifier(SludgeConstants.MOD_ID, "general"))
        .icon { ItemStack(Items.SLUDGE_ITEM) }
        .build()
}
