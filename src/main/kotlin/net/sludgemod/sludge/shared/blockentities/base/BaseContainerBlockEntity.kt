package net.sludgemod.sludge.shared.blockentities.base

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.entity.LockableContainerBlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.collection.DefaultedList

abstract class BaseContainerBlockEntity(blockEntityType: BlockEntityType<*>) :
    LockableContainerBlockEntity(blockEntityType), BlockEntityClientSerializable {

    abstract val items: DefaultedList<ItemStack>

    override fun clear() = items.clear()

    override fun setStack(slot: Int, stack: ItemStack) {
        items[slot] = stack
        if (stack.count > maxCountPerStack) {
            stack.count = maxCountPerStack
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
        return Inventories.removeStack(items, slot)
    }

    override fun getStack(slot: Int) = items[slot]

    override fun canPlayerUse(player: PlayerEntity) = true

    override fun size() = items.size

    override fun fromTag(state: BlockState, tag: CompoundTag) {
        super.fromTag(state, tag)
        Inventories.fromTag(tag, items)
        fromTag(tag, false)
    }

    override fun toTag(tag: CompoundTag): CompoundTag {
        Inventories.toTag(tag, items)
        toTag(tag, false)
        return super.toTag(tag)
    }

    override fun fromClientTag(tag: CompoundTag) {
        fromTag(tag, true)
    }

    override fun toClientTag(tag: CompoundTag): CompoundTag {
        return toTag(tag, true)
    }

    abstract fun fromTag(tag: CompoundTag, client: Boolean);
    abstract fun toTag(tag: CompoundTag, client: Boolean): CompoundTag;
}
