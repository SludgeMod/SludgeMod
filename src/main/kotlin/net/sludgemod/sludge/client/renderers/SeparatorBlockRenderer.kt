package net.sludgemod.sludge.client.renderers

import alexiil.mc.lib.attributes.fluid.SingleFluidTank
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.texture.Sprite
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.client.util.math.MatrixStack
import net.sludgemod.sludge.shared.blockentities.SeparatorBlockEntity

class SeparatorBlockRenderer(dispatcher: BlockEntityRenderDispatcher) :
    BlockEntityRenderer<SeparatorBlockEntity>(dispatcher) {

    override fun render(
        entity: SeparatorBlockEntity,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumerProvider: VertexConsumerProvider,
        light: Int,
        overlay: Int
    ) {
        renderSurfaceFluid(
            entity.getInputTank(),
            vertexConsumerProvider,
            entity,
            matrices,
            3,
            13,
            4,
            12,
            1,
            11,
            light
        )
        renderSurfaceFluid(
            entity.getOutputTank(),
            vertexConsumerProvider,
            entity,
            matrices,
            5,
            11,
            4,
            10,
            12,
            15,
            light
        )
    }

    private fun renderSurfaceFluid(
        tank: SingleFluidTank,
        vertexConsumerProvider: VertexConsumerProvider,
        entity: SeparatorBlockEntity,
        matrices: MatrixStack,
        xMin: Int,
        xMax: Int,
        yMin: Int,
        yMax: Int,
        zMin: Int,
        zMax: Int,
        light: Int
    ) {
        val fluidVolume = tank.get()
        if (!fluidVolume.isEmpty) {
            val buffer = vertexConsumerProvider.getBuffer(RenderLayer.getSolid())
            val fluid = fluidVolume.rawFluid
            val handler = FluidRenderHandlerRegistry.INSTANCE[fluid]
            val sprite = handler.getFluidSprites(entity.world, entity.pos, fluid?.defaultState)[0]
            val color = handler.getFluidColor(entity.world, entity.pos, fluid?.defaultState)

            MinecraftClient.getInstance().textureManager.bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEX)
            val height = fluidVolume.amount_F.asInexactDouble().toFloat() / tank.maxAmount_F.asInexactDouble()
                .toFloat() * yMax + yMin - 0.5f

            val xPos = 1.0f / 16.0f * xMin
            val zPos = 1.0f / 16.0f * zMin
            val xEndPos = 1.0f / 16.0f * xMax
            val zEndPos = 1.0f / 16.0f * zMax
            val yPos = 1.0f / 16.0f * height

            val r = (color shr 16 and 255).toFloat() / 255.0f
            val g = (color shr 8 and 255).toFloat() / 255.0f
            val b = (color and 255).toFloat() / 255.0f

            val modelMatrix = matrices.peek().model
            val normalMatrix = matrices.peek().normal

            buffer.vertex(modelMatrix, xPos, yPos, zPos).color(r, g, b, 1f)
                .texture(
                    sprite.getFrameU(getXFromU(sprite, sprite.minU) + zMin),
                    sprite.getFrameV(getYFromV(sprite, sprite.minV) + xMin)
                ).light(light).normal(normalMatrix, 0.0f, 1.0f, 0.0f).next()
            buffer.vertex(modelMatrix, xPos, yPos, zEndPos).color(r, g, b, 1f)
                .texture(
                    sprite.getFrameU(getXFromU(sprite, sprite.maxU) - (16 - zMax)),
                    sprite.getFrameV(getYFromV(sprite, sprite.minV) + xMin)
                ).light(light).normal(normalMatrix, 0.0f, 1.0f, 0.0f).next()
            buffer.vertex(modelMatrix, xEndPos, yPos, zEndPos).color(r, g, b, 1f)
                .texture(
                    sprite.getFrameU(getXFromU(sprite, sprite.maxU) - (16 - zMax)),
                    sprite.getFrameV(getYFromV(sprite, sprite.maxV) - (16 - xMax))
                ).light(light).normal(normalMatrix, 0.0f, 1.0f, 0.0f).next()
            buffer.vertex(modelMatrix, xEndPos, yPos, zPos).color(r, g, b, 1f)
                .texture(
                    sprite.getFrameU(getXFromU(sprite, sprite.minU) + zMin),
                    sprite.getFrameV(getYFromV(sprite, sprite.maxV) - (16 - xMax))
                ).light(light).normal(normalMatrix, 0.0f, 1.0f, 0.0f).next()

        }
    }

    private fun getXFromU(sprite: Sprite, f: Float): Double {
        val g = sprite.maxU - sprite.minU
        return (f - sprite.minU) / g * 16.0
    }

    private fun getYFromV(sprite: Sprite, f: Float): Double {
        val g = sprite.maxV - sprite.minV
        return (f - sprite.minV) / g * 16.0
    }
}

