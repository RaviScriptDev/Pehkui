package virtuoel.pehkui.mixin.server.level;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Prediction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
	@Inject(at = @At("HEAD"), method = "restoreFrom")
	private void pehkui$copyFrom(ServerPlayer oldPlayer, boolean alive, CallbackInfo info) {
		ScaleUtils.loadScaleOnRespawn((ServerPlayer) (Object) this, oldPlayer, alive);
	}

	@Inject(at = @At("RETURN"), method = "drop(Lnet/minecraft/world/item/ItemStack;ZLnet/minecraft/util/Prediction;)Lnet/minecraft/world/entity/item/ItemEntity;")
	private void pehkui$dropItem(ItemStack itemStack, boolean thrownFromHand, Prediction prediction, CallbackInfoReturnable<ItemEntity> cir) {
		final ItemEntity entity = cir.getReturnValue();

		if (entity != null) {
			Entity self = (Entity) (Object) this;
			ScaleUtils.setScaleOfDrop(entity, self);

			final float scale = ScaleUtils.getEyeHeightScale(self);

			if (scale != 1.0F) {
				final Vec3 pos = entity.position();

				entity.setPos(pos.x, pos.y + ((1.0F - scale) * 0.3D), pos.z);
			}
		}
	}
}
