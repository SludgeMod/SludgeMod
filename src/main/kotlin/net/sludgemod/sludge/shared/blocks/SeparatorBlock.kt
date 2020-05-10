package net.sludgemod.sludge.shared.blocks

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntity
import net.minecraft.world.BlockView
import net.sludgemod.sludge.shared.blockentities.SeparatorBlockEntity

class SeparatorBlock : Block(FabricBlockSettings.of(Material.METAL).nonOpaque()), BlockEntityProvider
{
    override fun createBlockEntity(world: BlockView) = SeparatorBlockEntity()
}
