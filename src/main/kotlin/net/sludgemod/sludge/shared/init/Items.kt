package net.sludgemod.sludge.shared.init

import net.minecraft.item.BlockItem
import net.minecraft.item.BucketItem
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.sludgemod.sludge.shared.SludgeConstants

object Items {
    val SLUDGE_ITEM = Item(Item.Settings().group(ItemGroups.SLUDGE_ITEM_GROUP))
    val SLUDGE_BUCKET =
        BucketItem(
            Fluids.STILL_SLUDGE,
            Item.Settings().group(ItemGroups.SLUDGE_ITEM_GROUP).recipeRemainder(Items.BUCKET).maxCount(1)
        )
    private val SEPARATOR_BLOCK_ITEM =
        BlockItem(Blocks.SEPARATOR_BLOCK, Item.Settings().group(ItemGroups.SLUDGE_ITEM_GROUP))

    internal fun register() {
        Registry.register(Registry.ITEM, Identifier(SludgeConstants.MOD_ID, "sludge_item"), SLUDGE_ITEM)
        Registry.register(Registry.ITEM, Identifier(SludgeConstants.MOD_ID, "sludge_bucket"), SLUDGE_BUCKET)
        Registry.register(Registry.ITEM, SludgeConstants.BlockIds.SEPARATOR, SEPARATOR_BLOCK_ITEM)
    }
}
