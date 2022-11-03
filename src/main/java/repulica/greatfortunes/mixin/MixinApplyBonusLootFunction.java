package repulica.greatfortunes.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ApplyBonusLootFunction.class)
public class MixinApplyBonusLootFunction {
	@Shadow @Final Enchantment enchantment;

	@Shadow @Final ApplyBonusLootFunction.Formula formula;

	@Inject(method = "process", at = @At("HEAD"), cancellable = true)
	private void useLuckInstead(ItemStack stack, LootContext context, CallbackInfoReturnable<ItemStack> info) {
		if (this.enchantment == Enchantments.LOOTING || this.enchantment == Enchantments.FORTUNE && context.getLuck() != 0) {
			stack.setCount(this.formula.getValue(context.getRandom(), stack.getCount(), (int) context.getLuck()));
			info.setReturnValue(stack);
		}
	}
}
