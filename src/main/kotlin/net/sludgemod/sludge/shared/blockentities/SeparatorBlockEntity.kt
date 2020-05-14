package net.sludgemod.sludge.shared.blockentities

import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.screen.ScreenHandler
import net.minecraft.text.Text
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.Direction
import net.sludgemod.sludge.shared.blockentities.base.BaseContainerBlockEntity
import net.sludgemod.sludge.shared.init.BlockEntities
import net.sludgemod.sludge.shared.init.Blocks
import net.sludgemod.sludge.shared.screenhandlers.SeparatorScreenHandler
import net.sludgemod.sludge.shared.utils.TankInventory

class SeparatorBlockEntity : BaseContainerBlockEntity(BlockEntities.SEPARATOR_BLOCK_ENTITY), SidedInventory {
    override val items: DefaultedList<ItemStack> = DefaultedList.ofSize(9, ItemStack.EMPTY)
    val tankInventory = TankInventory()

    //region BaseContainerBlockEntity
    public override fun createContainer(i: Int, playerInventory: PlayerInventory): ScreenHandler {
        return SeparatorScreenHandler(i, playerInventory, this)
    }

    override fun getContainerName(): Text = Blocks.SEPARATOR_BLOCK.name

    override fun fromTag(state: BlockState, tag: CompoundTag) {
        super.fromTag(state, tag)
        tankInventory.fromTag(tag)
    }

    override fun toTag(tag: CompoundTag): CompoundTag {
        tankInventory.toTag(tag)
        return super.toTag(tag)
    }
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
