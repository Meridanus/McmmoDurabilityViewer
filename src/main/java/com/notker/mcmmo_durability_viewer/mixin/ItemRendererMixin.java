package com.notker.mcmmo_durability_viewer.mixin;

import com.notker.mcmmo_durability_viewer.McmmoDurabilityViewer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow public float zOffset;

    @Inject(at = @At("HEAD"), method = "renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V")
    public void renderGuiItemOverlay(TextRenderer renderer, ItemStack stack, int x, int y, String countLabel, CallbackInfo ci) {
        NbtCompound nbt = stack.getOrCreateNbt();
        Integer consumes = nbt.getInt(McmmoDurabilityViewer.MAX_CONSUME_KEY);

        if (!stack.isEmpty() && consumes > 0) {
            MatrixStack matrixStack = new MatrixStack();

            if (stack.getCount() == 1 ) {
                String string = String.valueOf(consumes);

                matrixStack.translate(0.0D, 0.0D, (this.zOffset + 200.0F));

                VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());

                int maxConsumes = Math.max(consumes  , McmmoDurabilityViewer.FULL_CONSUME_VALUE);

                float f = Math.max(0.0F, 1 - ((float)maxConsumes - (float)consumes) / (float)maxConsumes);

                int color = (MathHelper.hsvToRgb(f / 3.0F, 1.0F, 1.0F));

                renderer.draw(string, (float)(x + 19 - 2 - renderer.getWidth(string)), (float)(y + 6 + 3), color, true, matrixStack.peek().getPositionMatrix(), immediate, false, 0, 15728880);

                immediate.draw();
            }

        }

    }
}
