package repulica.greatfortunes.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class MixinEnchantmentHelper {
	@Inject(method = "getLooting", at = @At("RETURN"), cancellable = true)
	private static void injectLuck(LivingEntity entity, CallbackInfoReturnable<Integer> info) {
		if (entity instanceof PlayerEntity player) {
			info.setReturnValue(info.getReturnValueI() + (int)player.getLuck());
		}
	}
}
