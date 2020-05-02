package net.sludgemod.sludge.shared.fluids

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.FluidBlock
import net.minecraft.fluid.BaseFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.item.Item
import net.minecraft.state.StateManager
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.IWorld
import net.minecraft.world.WorldView
import net.sludgemod.sludge.SludgeInit

abstract class SludgeFluid : BaseFluid() {
    override fun toBlockState(state: FluidState?): BlockState {
        return SludgeInit.SLUDGE_FLUID_BLOCK.defaultState.with(FluidBlock.LEVEL, method_15741(state))
    }

    override fun getBucketItem(): Item {
        return SludgeInit.SLUDGE_BUCKET
    }

    override fun getLevelDecreasePerBlock(world: WorldView?): Int {
        return 1
    }

    override fun matchesType(fluid: Fluid?): Boolean {
        return fluid == still || fluid == flowing
    }

    override fun getTickRate(world: WorldView?): Int {
        return 5
    }

    override fun getFlowing(): Fluid {
        return SludgeInit.FLOWING_SLUDGE
    }

    override fun isInfinite(): Boolean {
        return true
    }

    override fun getFlowSpeed(world: WorldView?): Int {
        return 3
    }

    override fun canBeReplacedWith(
        state: FluidState?,
        world: BlockView?,
        pos: BlockPos?,
        fluid: Fluid?,
        direction: Direction?
    ): Boolean {
        return false
    }

    override fun getBlastResistance(): Float {
        return 100.0F
    }

    override fun getStill(): Fluid {
        return SludgeInit.STILL_SLUDGE
    }

    override fun beforeBreakingBlock(world: IWorld, pos: BlockPos, state: BlockState) {
        val blockEntity = if (state.block.hasBlockEntity()) world.getBlockEntity(pos) else null
        Block.dropStacks(state, world.world, pos, blockEntity)
    }

    class Flowing : SludgeFluid() {
        override fun appendProperties(builder: StateManager.Builder<Fluid, FluidState>) {
            super.appendProperties(builder)
            builder.add(BaseFluid.LEVEL)
        }

        override fun getLevel(state: FluidState): Int {
            return state.get(BaseFluid.LEVEL) as Int
        }

        override fun isStill(state: FluidState): Boolean {
            return false
        }
    }

    class Still : SludgeFluid() {
        override fun getLevel(state: FluidState): Int {
            return 8
        }

        override fun isStill(state: FluidState): Boolean {
            return true
        }
    }
}
