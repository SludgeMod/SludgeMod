package net.sludgemod.sludge

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.block.Blocks
import net.minecraft.block.FluidBlock
import net.minecraft.item.*
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.sludgemod.sludge.shared.fluids.FluidManager
import net.sludgemod.sludge.shared.fluids.SludgeFluid
import net.sludgemod.sludge.shared.items.SludgeItem

@Suppress("unused")
object SludgeInit : ModInitializer {
    private const val MOD_ID = "sludge"
    //Item groups
    val SLUDGE_ITEM_GROUP: ItemGroup = FabricItemGroupBuilder.create(Identifier(MOD_ID, "general"))
            .icon { ItemStack(SLUDGE_ITEM) }
            .build()

    //Fluids
    val STILL_SLUDGE = SludgeFluid.Still()
    val FLOWING_SLUDGE = SludgeFluid.Flowing()

    //Items
    val SLUDGE_ITEM = SludgeItem()
    val SLUDGE_BUCKET = BucketItem(STILL_SLUDGE, Item.Settings().group(SLUDGE_ITEM_GROUP).recipeRemainder(Items.BUCKET).maxCount(1))

    //Blocks
    val SLUDGE_FLUID_BLOCK = object: FluidBlock(STILL_SLUDGE, FabricBlockSettings.copy(Blocks.WATER)) {}

    override fun onInitialize() {
        Registry.register(Registry.ITEM, Identifier(MOD_ID, "sludge_item"), SLUDGE_ITEM)
        Registry.register(Registry.ITEM, Identifier(MOD_ID, "sludge_bucket"), SLUDGE_BUCKET)

        Registry.register(Registry.FLUID, Identifier(MOD_ID, "sludge"), STILL_SLUDGE)
        Registry.register(Registry.FLUID, Identifier(MOD_ID, "flowing_sludge"), FLOWING_SLUDGE)

        Registry.register(Registry.BLOCK, Identifier(MOD_ID, "sludge"), SLUDGE_FLUID_BLOCK)

        FluidManager.setupFluidRendering(STILL_SLUDGE, FLOWING_SLUDGE, Identifier("minecraft", "water"), 0x964b13)
    }
}
