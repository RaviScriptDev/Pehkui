package virtuoel.pehkui.mixin.client.renderer.extract;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.extract.LevelExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(LevelExtractor.class)
public abstract class LevelExtractorMixin {
	@ModifyExpressionValue(method = "getViewBlockingState", at = @At(value = "CONSTANT", args = "floatValue=0.1F"))
	private static float pehkui$getViewBlockingState$offset(float value, final LocalPlayer player) {
		final float scale = ScaleUtils.getEyeHeightScale(player);

		return scale != 1.0F ? value * scale : value;
	}
}
