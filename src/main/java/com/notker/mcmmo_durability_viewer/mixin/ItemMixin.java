package com.notker.mcmmo_durability_viewer.mixin;

import com.notker.mcmmo_durability_viewer.McmmoDurabilityViewer;
import com.notker.mcmmo_durability_viewer.config.McmmoDurabilityViewerConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(at = @At("HEAD"), method = "getItemBarStep", cancellable = true)
    public void getItemBarStep(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        NbtCompound nbt = stack.getNbt();

        McmmoDurabilityViewerConfig config = McmmoDurabilityViewer.config;
        // CHeck if the config is available, else use the default value
        String maxDurabilityTag = config != null ? config.durabilityBar.MAX_DURABILITY_TAG : McmmoDurabilityViewer.DEFAULT_MAX_DURABILITY_TAG;

        if (nbt != null && nbt.getInt(maxDurabilityTag) > 0) {
            cir.setReturnValue(Math.round(13.0f - (float)stack.getDamage() * 13.0f / (float)stack.getMaxDamage()));
            cir.cancel();
        }
        // Default Behaviour
    }

    @Inject(at = @At("HEAD"), method = "getItemBarColor", cancellable = true)
    public void getItemBarColor(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        NbtCompound nbt = stack.getOrCreateNbt();

        McmmoDurabilityViewerConfig config = McmmoDurabilityViewer.config;
        // CHeck if the config is available, else use the default value
        String maxDurabilityTag = config != null ? config.durabilityBar.MAX_DURABILITY_TAG : McmmoDurabilityViewer.DEFAULT_MAX_DURABILITY_TAG;

        if (nbt != null && nbt.getInt(maxDurabilityTag) > 0) {

            float f = Math.max(0.0f, ( (float)stack.getMaxDamage() - (float)stack.getDamage()) /  (float)stack.getMaxDamage());

            cir.setReturnValue(MathHelper.hsvToRgb(f / 3.0f, 1.0f, 1.0f));
            cir.cancel();
        }
        // Default Behaviour
    }

}
