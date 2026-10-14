package virtuoel.pehkui.mixin.client.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.world.entity.player.Player;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ScreenEffectRenderer.class)
public abstract class ScreenEffectRendererMixin {
	// todo: check if something breaks
//	@ModifyExpressionValue(method = "getViewBlockingState", at = @At(value = "CONSTANT", args = "floatValue=0.1F"))
//	private static float pehkui$getInWallBlockState$offset(float value, Player player) {
//		final float scale = ScaleUtils.getEyeHeightScale(player);
//
//		return scale != 1.0F ? value * scale : value;
//	}
}
