package net.sludgemod.sludge.shared.init

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

    internal fun register() {
        Registry.register(Registry.ITEM, Identifier(SludgeConstants.MOD_ID, "sludge_item"), SLUDGE_ITEM)
        Registry.register(Registry.ITEM, Identifier(SludgeConstants.MOD_ID, "sludge_bucket"), SLUDGE_BUCKET)
    }
}
