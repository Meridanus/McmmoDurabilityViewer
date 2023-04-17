package com.notker.mcmmo_durability_viewer.mixin;

import com.notker.mcmmo_durability_viewer.McmmoDurabilityViewer;
import com.notker.mcmmo_durability_viewer.config.McmmoDurabilityViewerConfig;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Inject(at = @At("HEAD"), method = "renderGuiItemOverlay(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V")
    public void renderGuiItemOverlay(MatrixStack matrices, TextRenderer textRenderer, ItemStack stack, int x, int y, @Nullable String countLabel, CallbackInfo ci) {
        NbtCompound nbt = stack.getOrCreateNbt();

        McmmoDurabilityViewerConfig config = McmmoDurabilityViewer.config;
        // CHeck if the config is available, else use the default value
        String maxConsumeTag = config != null ? config.consumeValue.MAX_CONSUME_TAG : McmmoDurabilityViewer.DEFAULT_MAX_CONSUME_TAG;
        Integer fullConsumeValue = config != null ? config.consumeValue.FULL_CONSUME_VALUE : McmmoDurabilityViewer.DEFAULT_FULL_CONSUME_VALUE;
        boolean staticColor = config != null ? config.consumeValue.SINGLE_COLOR : McmmoDurabilityViewer.DEFAULT_SINGLE_COLOR;
        Integer color = config != null ? config.consumeValue.CONSUME_COLOR : McmmoDurabilityViewer.DEFAULT_CONSUME_COLOR;

        Integer consumes = nbt.getInt(maxConsumeTag);

        if (!stack.isEmpty() && consumes > 0) {
            matrices.push();

            if (stack.getCount() == 1 ) {
                String string = String.valueOf(consumes);

                matrices.translate(0.0F, 0.0F, 200.0F);

                VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());

                // Checks if the player wants a singe color or a Gradient (Red...Green)
                if (!staticColor) {
                    int maxConsumes = Math.max(consumes  , fullConsumeValue);
                    float f = Math.max(0.0F, 1 - ((float)maxConsumes - (float)consumes) / (float)maxConsumes);
                    color = (MathHelper.hsvToRgb(f / 3.0F, 1.0F, 1.0F));
                }

                textRenderer.draw(string, (float)(x + 19 - 2 - textRenderer.getWidth(string)), (float)(y + 6 + 3), color, true, matrices.peek().getPositionMatrix(), immediate, TextRenderer.TextLayerType.NORMAL, 0, 15728880);

                immediate.draw();
            }
            matrices.pop();
        }

    }
}
