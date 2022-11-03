package repulica.greatfortunes.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
	@Shadow @Nullable protected PlayerEntity attackingPlayer;

	@ModifyArg(method = "getLootContextBuilder", at = @At(value = "INVOKE", target = "Lnet/minecraft/loot/context/LootContext$Builder;luck(F)Lnet/minecraft/loot/context/LootContext$Builder;"))
	private float injectLuck(float orig) {
		//luck gotten in the enchantment helper mixin already
		return EnchantmentHelper.getLooting(this.attackingPlayer);
	}
}
