package net.sludgemod.sludge.shared.utils

import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList
import net.sludgemod.sludge.shared.blockentities.SeparatorBlockEntity

abstract class BaseContainerBlockEntity(blockEntityType: BlockEntityType<*>?): BlockEntity(blockEntityType), Inventory {

    abstract val items: DefaultedList<ItemStack>

    override fun clear() = items.clear()

    override fun setStack(slot: Int, stack: ItemStack) {
        items[slot] = stack;
        if (stack.count > maxCountPerStack) {
            stack.count = maxCountPerStack;
        }
    }

    override fun isEmpty(): Boolean {
        for (i in 0 until size()) {
            val stack: ItemStack = getStack(i)
            if (!stack.isEmpty) {
                return false
            }
        }
        return true
    }

    override fun removeStack(slot: Int, amount: Int): ItemStack {
        val result = Inventories.splitStack(items, slot, amount)
        if (!result.isEmpty) {
            markDirty()
        }
        return result
    }

    override fun removeStack(slot: Int): ItemStack {
        return Inventories.removeStack(items, slot);
    }

    override fun getStack(slot: Int) = items[slot]

    override fun canPlayerUse(player: PlayerEntity?) = true

    override fun size() = items.size
}