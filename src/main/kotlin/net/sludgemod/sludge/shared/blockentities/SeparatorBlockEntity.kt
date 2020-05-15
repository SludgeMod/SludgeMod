package net.sludgemod.sludge.shared.blockentities

import alexiil.mc.lib.attributes.Simulation
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount
import alexiil.mc.lib.attributes.fluid.filter.ExactFluidFilter
import alexiil.mc.lib.attributes.fluid.filter.FluidFilter
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.CompoundTag
import net.minecraft.screen.ScreenHandler
import net.minecraft.text.Text
import net.minecraft.util.Tickable
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.Direction
import net.sludgemod.sludge.shared.blockentities.base.BaseContainerBlockEntity
import net.sludgemod.sludge.shared.init.BlockEntities
import net.sludgemod.sludge.shared.init.Blocks
import net.sludgemod.sludge.shared.init.Fluids
import net.sludgemod.sludge.shared.screenhandlers.SeparatorScreenHandler
import net.sludgemod.sludge.shared.utils.TankInventory

class SeparatorBlockEntity : BaseContainerBlockEntity(BlockEntities.SEPARATOR_BLOCK_ENTITY), SidedInventory, Tickable {
    override val items: DefaultedList<ItemStack> = DefaultedList.ofSize(9, ItemStack.EMPTY)

    val tanks =
        TankInventory(listOf<FluidFilter>(ExactFluidFilter(Fluids.SLUDGE_FLUID_KEY), ExactFluidFilter(FluidKeys.WATER)))

    private var separatorCooldown = -1;

    //region BaseContainerBlockEntity
    public override fun createContainer(i: Int, playerInventory: PlayerInventory): ScreenHandler {
        return SeparatorScreenHandler(i, playerInventory, this)
    }

    override fun getContainerName(): Text = Blocks.SEPARATOR_BLOCK.name

    override fun fromTag(tag: CompoundTag, client: Boolean) {
        tanks.fromTag(tag)
    }

    override fun toTag(tag: CompoundTag, client: Boolean): CompoundTag {
        return tanks.toTag(tag)
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

    override fun tick() {
        if (
            world?.isClient === false
        ) {
            separatorCooldown--

            if (separatorCooldown <= 0) {
                val extractedAmount = tanks.getTank(0).attemptAnyExtraction(FluidAmount.of(1, 20), Simulation.ACTION)
                if (extractedAmount.amount_F.asInexactDouble() > 0.0) {
                    var output = getStack(0)
                    if (output.isEmpty) {
                        output = ItemStack(Items.DIRT)
                    } else if (output.item == Items.DIRT) {
                        output.increment(1)
                    }
                    setStack(0, output)
                }
                tanks.getTank(1)
                    .attemptInsertion(FluidKeys.WATER.withAmount(extractedAmount.amount_F / 2), Simulation.ACTION)
                separatorCooldown = 8
            }

            if (tanks.hasChanged()) {
                sync()
            }
        }
    }
    //endregion
}
