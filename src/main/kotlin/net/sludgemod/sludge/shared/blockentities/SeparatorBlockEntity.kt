package net.sludgemod.sludge.shared.blockentities

import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.Direction
import net.sludgemod.sludge.SludgeInit
import net.sludgemod.sludge.shared.utils.BaseContainerBlockEntity

class SeparatorBlockEntity : BaseContainerBlockEntity(SludgeInit.SEPARATOR_BLOCK_ENTITY), SidedInventory
{
    override val items: DefaultedList<ItemStack> = DefaultedList.ofSize(2, ItemStack.EMPTY);

    //region SidedInventory
    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction) = true

    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?) = false

    override fun getAvailableSlots(side: Direction): IntArray {
        val result = IntArray(items.size)
        for (i in result.indices) {
            result[i] = i
        }
        return result
    }
    //endregion
}
