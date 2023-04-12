package com.notker.mcmmo_durability_viewer.mixin;


import com.notker.mcmmo_durability_viewer.McmmoDurabilityViewer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	@Shadow @Nullable public abstract NbtCompound getNbt();

	@Inject(at = @At("HEAD"), method = "isDamageable", cancellable = true)
	public void isDamageable(CallbackInfoReturnable<Boolean> cir) {
		NbtCompound nbt = getNbt();
		if (nbt != null && nbt.getInt(McmmoDurabilityViewer.MAX_DURABILITY_KEY) > 0) {
			cir.setReturnValue(true);
			cir.cancel();
		}
		// Default Behaviour
	}

	@Inject(at = @At("HEAD"), method = "getMaxDamage", cancellable = true)
	public void getMaxDamage(CallbackInfoReturnable<Integer> cir) {
		NbtCompound nbt = getNbt();
		if (nbt != null && nbt.getInt(McmmoDurabilityViewer.MAX_DURABILITY_KEY) > 0) {
			cir.setReturnValue(nbt.getInt(McmmoDurabilityViewer.MAX_DURABILITY_KEY));
			cir.cancel();
		}
		// Default Behaviour
	}

	@Inject(at = @At("HEAD"), method = "getDamage", cancellable = true)
	public void getDamage(CallbackInfoReturnable<Integer> cir) {
		NbtCompound nbt = getNbt();
		if (nbt != null && nbt.getInt(McmmoDurabilityViewer.MAX_DURABILITY_KEY) > 0) {

			if (nbt.contains(McmmoDurabilityViewer.DURABILITY_KEY)) {
				cir.setReturnValue(nbt.getInt(McmmoDurabilityViewer.MAX_DURABILITY_KEY) - nbt.getInt(McmmoDurabilityViewer.DURABILITY_KEY));
			} else {
				cir.setReturnValue(0);
			}

			cir.cancel();
		}
		// Default Behaviour
	}

	@Inject(at = @At("HEAD"), method = "isDamaged", cancellable = true)
	public void isDamaged(CallbackInfoReturnable<Boolean> cir) {
		NbtCompound nbt = getNbt();
		if (nbt != null && nbt.getInt(McmmoDurabilityViewer.MAX_DURABILITY_KEY) > 0) {

			if (nbt.contains(McmmoDurabilityViewer.DURABILITY_KEY)) {
				cir.setReturnValue(nbt.getInt(McmmoDurabilityViewer.DURABILITY_KEY) < nbt.getInt(McmmoDurabilityViewer.MAX_DURABILITY_KEY));
			} else {
				cir.setReturnValue(false);
			}

			cir.cancel();
		}
		// Default Behaviour
	}

}

