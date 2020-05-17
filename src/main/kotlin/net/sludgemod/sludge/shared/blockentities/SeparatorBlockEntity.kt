package net.sludgemod.sludge.shared.blockentities

import alexiil.mc.lib.attributes.Simulation
import alexiil.mc.lib.attributes.fluid.SingleFluidTank
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount
import alexiil.mc.lib.attributes.fluid.filter.ExactFluidFilter
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume
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

    private val inputTank = TankInventory(1, 5, ExactFluidFilter(Fluids.SLUDGE_FLUID_KEY))
    private val outputTank = TankInventory(1, 2, ExactFluidFilter(FluidKeys.WATER))

    private var separatorCooldown = -1

    private val INPUT_FLUIDS_KEY = "InputFluids"
    private val OUTPUT_FLUIDS_KEY = "OutputFluids"

    fun getInputTank(): SingleFluidTank = inputTank.getTank(0)
    fun getOutputTank(): SingleFluidTank = outputTank.getTank(0)

    //region BaseContainerBlockEntity
    public override fun createContainer(i: Int, playerInventory: PlayerInventory): ScreenHandler {
        return SeparatorScreenHandler(i, playerInventory, this)
    }

    override fun getContainerName(): Text = Blocks.SEPARATOR_BLOCK.name

    override fun fromTag(tag: CompoundTag, client: Boolean) {
        if (tag.contains(INPUT_FLUIDS_KEY)) {
            val fluid = FluidVolume.fromTag(tag.getCompound(INPUT_FLUIDS_KEY))
            inputTank.setInvFluid(0, fluid, Simulation.ACTION)
        }
        if (tag.contains(OUTPUT_FLUIDS_KEY)) {
            val fluid = FluidVolume.fromTag(tag.getCompound(OUTPUT_FLUIDS_KEY))
            outputTank.setInvFluid(0, fluid, Simulation.ACTION)
        }
    }

    override fun toTag(tag: CompoundTag, client: Boolean): CompoundTag {
        val inputFluid = inputTank.getInvFluid(0)
        if (!inputFluid.isEmpty) {
            tag.put(INPUT_FLUIDS_KEY, inputFluid.toTag())
        }
        val outputFluid = outputTank.getInvFluid(0)
        if (!outputFluid.isEmpty) {
            tag.put(OUTPUT_FLUIDS_KEY, outputFluid.toTag())
        }
        return tag;
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
        if (world?.isClient === false) {
            separatorCooldown--

            if (separatorCooldown <= 0 && craft(Simulation.SIMULATE)) {
                craft(Simulation.ACTION)
                separatorCooldown = 100
            }

            if (inputTank.hasChanged() || outputTank.hasChanged()) {
                sync()
            }
        }
    }

    private fun craft(simulation: Simulation): Boolean {
        val sludgeExtractionAmount = FluidAmount.of(6, 10)
        val waterInsertionAmount = FluidAmount.of(3, 10)

        val canInsertItem = insertItem(ItemStack(Items.DIRT), simulation)

        val canExtractSludge = inputTank.getTank(0)
            .attemptAnyExtraction(sludgeExtractionAmount, simulation).amount_F == sludgeExtractionAmount

        val canInsertWater = outputTank.getTank(0)
            .attemptInsertion(FluidKeys.WATER.withAmount(waterInsertionAmount), simulation).isEmpty

        return canInsertItem && canExtractSludge && canInsertWater
    }
    //endregion
}
