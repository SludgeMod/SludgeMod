package net.sludgemod.sludge.client.screens

import alexiil.mc.lib.attributes.fluid.SingleFluidTank
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import net.sludgemod.sludge.shared.SludgeConstants
import net.sludgemod.sludge.shared.init.Blocks
import net.sludgemod.sludge.shared.screenhandlers.SeparatorScreenHandler

class SeparatorHandledScreen(private val separatorScreenHandler: SeparatorScreenHandler) :
    HandledScreen<SeparatorScreenHandler>(
        separatorScreenHandler,
        MinecraftClient.getInstance().player?.inventory,
        Blocks.SEPARATOR_BLOCK.name
    ) {
    private val texture = Identifier(SludgeConstants.MOD_ID, "textures/gui/separator.png")

    override fun drawBackground(matrixStack: MatrixStack, f: Float, mouseY: Int, i: Int) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f)
        client?.textureManager?.bindTexture(texture)
        this.drawTexture(matrixStack, x, y, 0, 0, backgroundWidth, backgroundHeight)

        drawTank(separatorScreenHandler.separatorBlockEntity.getInputTank(), 8.0, -25.0)
        drawTank(separatorScreenHandler.separatorBlockEntity.getOutputTank(), 152.0, -25.0)
    }

    private fun drawTank(fluidTank: SingleFluidTank, xOffset: Double, yOffset: Double) {
        val volume: FluidVolume = fluidTank.get()
        val capacity = fluidTank.maxAmount_F.asInexactDouble()
        val xPos = x.toDouble() + xOffset
        val yPos = y + yOffset
        val width = 16.0
        val height = 94.0

        if (!volume.isEmpty) {
            val percentFull = volume.amount_F.asInexactDouble() / capacity
            val fluidHeight = 52 * percentFull
            volume.renderGuiRect(xPos, yPos + height - fluidHeight, xPos + width, yPos + height)
        }
    }
}
