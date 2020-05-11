package net.sludgemod.sludge.shared.screenhandlers

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot

class SeparatorScreenHandler(syncId: Int, playerInventory: PlayerInventory, private val inventory: Inventory) :
    ScreenHandler(null, syncId) {

    init {
        val padding = 8;
        val slotSize = 18;

        for (y in 0..2) {
            for (x in 0..2) {
                this.addSlot(Slot(inventory, x + y * 3, padding + 63 + x * slotSize, 17 + y * slotSize));
            }
        }

        for (y in 0..2) {
            for (x in 0..8) {
                this.addSlot(Slot(playerInventory, x + y * 9 + 9, padding + x * slotSize, 84 + y * slotSize));
            }
        }

        for (x in 0..8) {
            this.addSlot(Slot(playerInventory, x, padding + x * slotSize, 142));
        }
    }

    override fun canUse(player: PlayerEntity) = inventory.canPlayerUse(player)

    override fun transferSlot(player: PlayerEntity?, invSlot: Int): ItemStack? {
        var newStack = ItemStack.EMPTY
        val slot: Slot? = slots[invSlot]
        if (slot != null && slot.hasStack()) {
            val originalStack = slot.stack
            newStack = originalStack.copy()
            if (invSlot < inventory.size()) {
                if (!insertItem(originalStack, inventory.size(), slots.size, true)) {
                    return ItemStack.EMPTY
                }
            } else if (!insertItem(originalStack, 0, inventory.size(), false)) {
                return ItemStack.EMPTY
            }
            if (originalStack.isEmpty) {
                slot.stack = ItemStack.EMPTY
            } else {
                slot.markDirty()
            }
        }
        return newStack
    }
}
