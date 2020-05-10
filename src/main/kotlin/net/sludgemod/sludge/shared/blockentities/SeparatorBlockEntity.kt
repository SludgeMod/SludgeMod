package net.sludgemod.sludge.shared.blockentities

import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList
import net.sludgemod.sludge.SludgeInit
import net.sludgemod.sludge.shared.utils.BaseContainerBlockEntity

class SeparatorBlockEntity : BaseContainerBlockEntity(SludgeInit.SEPARATOR_BLOCK_ENTITY)
{
    override val items: DefaultedList<ItemStack> = DefaultedList.ofSize(2, ItemStack.EMPTY);
}