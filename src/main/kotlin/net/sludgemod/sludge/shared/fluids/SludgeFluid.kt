package net.sludgemod.sludge.shared.fluids

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.FluidBlock
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.state.StateManager
import net.minecraft.tag.FluidTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.IWorld
import net.minecraft.world.WorldView
import net.sludgemod.sludge.shared.init.Blocks
import net.sludgemod.sludge.shared.init.Fluids
import net.sludgemod.sludge.shared.init.Items

abstract class SludgeFluid : FlowableFluid() {
    override fun toBlockState(state: FluidState): BlockState {
        return Blocks.SLUDGE_FLUID_BLOCK.defaultState.with(FluidBlock.LEVEL, method_15741(state))
    }

    override fun getBucketItem() = Items.SLUDGE_BUCKET

    override fun getLevelDecreasePerBlock(world: WorldView) = 1

    override fun matchesType(fluid: Fluid) = fluid == still || fluid == flowing

    override fun getTickRate(world: WorldView) = 5

    override fun getFlowing() = Fluids.FLOWING_SLUDGE

    override fun isInfinite() = true

    override fun getFlowSpeed(world: WorldView) = 3

    override fun canBeReplacedWith(
        state: FluidState,
        world: BlockView,
        pos: BlockPos,
        fluid: Fluid,
        direction: Direction
    ) = direction == Direction.DOWN && !fluid.isIn(FluidTags.WATER)

    override fun getBlastResistance() = 100.0F

    override fun getStill() = Fluids.STILL_SLUDGE

    override fun beforeBreakingBlock(world: IWorld, pos: BlockPos, state: BlockState) {
        val blockEntity = if (state.block.hasBlockEntity()) world.getBlockEntity(pos) else null
        Block.dropStacks(state, world.world, pos, blockEntity)
    }

    class Flowing : SludgeFluid() {
        override fun appendProperties(builder: StateManager.Builder<Fluid, FluidState>) {
            super.appendProperties(builder)
            builder.add(FlowableFluid.LEVEL)
        }

        override fun getLevel(state: FluidState): Int = state.get(FlowableFluid.LEVEL)

        override fun isStill(state: FluidState) = false
    }

    class Still : SludgeFluid() {
        override fun getLevel(state: FluidState) = 8

        override fun isStill(state: FluidState) = true
    }
}
