package repulica.greatfortunes.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(Block.class)
public class MixinBlock {
	@Inject(method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;", at = @At(value = "INVOKE", target = "net/minecraft/block/BlockState.getDroppedStacks(Lnet/minecraft/loot/context/LootContext$Builder;)Ljava/util/List;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private static void hookBreakLuck(BlockState state, ServerWorld world, BlockPos pos, BlockEntity be, Entity entity, ItemStack stack, CallbackInfoReturnable<List<ItemStack>> info, LootContext.Builder builder) {
		if (entity instanceof LivingEntity living) {
			int fortune = EnchantmentHelper.getEquipmentLevel(Enchantments.FORTUNE, living);
			if (living instanceof PlayerEntity player) {
				builder.luck(fortune + player.getLuck());
			} else {
				builder.luck(fortune);
			}
			info.setReturnValue(state.getDroppedStacks(builder));
		}
	}
}
