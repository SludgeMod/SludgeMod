package net.sludgemod.sludge

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.sludgemod.sludge.shared.items.SludgeItem

@Suppress("unused")
object SludgeInit : ModInitializer {

    private const val MOD_ID = "sludge"
    val SLUDGE_ITEM_GROUP: ItemGroup = FabricItemGroupBuilder.create(Identifier(MOD_ID, "general"))
            .icon { ItemStack(SLUDGE_ITEM) }
            .build()
    private val SLUDGE_ITEM = SludgeItem()

    override fun onInitialize() {
        Registry.register(Registry.ITEM, Identifier(MOD_ID, "sludge_item"), SLUDGE_ITEM)
    }
}
