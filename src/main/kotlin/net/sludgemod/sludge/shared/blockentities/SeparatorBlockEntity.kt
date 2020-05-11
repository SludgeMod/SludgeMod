package net.sludgemod.sludge.shared.blockentities

import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.text.Text
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.Direction
import net.sludgemod.sludge.SludgeInit
import net.sludgemod.sludge.shared.blockentities.base.BaseContainerBlockEntity
import net.sludgemod.sludge.shared.screenhandlers.SeparatorScreenHandler

class SeparatorBlockEntity : BaseContainerBlockEntity(SludgeInit.SEPARATOR_BLOCK_ENTITY), SidedInventory {
    override val items: DefaultedList<ItemStack> = DefaultedList.ofSize(9, ItemStack.EMPTY)

    //region BaseContainerBlockEntity
    public override fun createContainer(i: Int, playerInventory: PlayerInventory): ScreenHandler {
        return SeparatorScreenHandler(i, playerInventory, this)
    }

    override fun getContainerName(): Text = SludgeInit.SEPARATOR_BLOCK.name
    //endregion

    //region SidedInventory
    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction) = true

    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?) = true

    override fun getAvailableSlots(side: Direction): IntArray {
        val result = IntArray(items.size)
        for (i in result.indices) {
            result[i] = i
        }
        return result
    }
    //endregion
}
