package net.sludgemod.sludge.client.screens

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import net.sludgemod.sludge.SludgeInit
import net.sludgemod.sludge.shared.SludgeConstants
import net.sludgemod.sludge.shared.screenhandlers.SeparatorScreenHandler

class SeparatorHandledScreen(private val separatorScreenHandler: SeparatorScreenHandler) :
    HandledScreen<SeparatorScreenHandler>(
        separatorScreenHandler,
        MinecraftClient.getInstance().player?.inventory,
        SludgeInit.SEPARATOR_BLOCK.name
    ) {
    private val texture = Identifier(SludgeConstants.MOD_ID, "textures/gui/separator.png")

    override fun drawBackground(matrixStack: MatrixStack, f: Float, mouseY: Int, i: Int) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f)
        client?.textureManager?.bindTexture(texture)
        val y = (height - backgroundHeight) / 2
        this.drawTexture(matrixStack, x, y, 0, 0, backgroundWidth, backgroundHeight)
    }
}
