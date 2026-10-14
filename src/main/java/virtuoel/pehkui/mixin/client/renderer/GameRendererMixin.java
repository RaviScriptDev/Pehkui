package virtuoel.pehkui.mixin.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.ScaleRenderUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Shadow
	@Final
	@Mutable
	private Minecraft minecraft;

	@Unique
	boolean pehkui$isBobbing = false;

	@Inject(method = "renderLevel", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lnet/minecraft/client/renderer/state/level/CameraRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;)V"))
	private void pehkui$renderWorld$before(CallbackInfo ci) {
		pehkui$isBobbing = true;
	}

	@Inject(method = "renderLevel", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lnet/minecraft/client/renderer/state/level/CameraRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;)V"))
	private void pehkui$renderWorld$after(CallbackInfo ci) {
		pehkui$isBobbing = false;
	}

	@ModifyVariable(method = "bobView", at = @At("STORE"), name = "backwardsInterpolatedWalkDistance")
	private float pehkui$bobView$distance(float value){
		return value / ScaleUtils.getViewBobbingScale(minecraft.getCameraEntity(), ScaleRenderUtils.getTickDelta(minecraft));
	}

	@WrapOperation(method = "bobView", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
	private void pehkui$bobView$translate(PoseStack obj, float x, float y, float z, Operation<Void> original) {
		if (pehkui$isBobbing) {
			final float scale = ScaleUtils.getViewBobbingScale(minecraft.getCameraEntity(), ScaleRenderUtils.getTickDelta(minecraft));

			if (scale != 1.0F) {
				x *= scale;
				y *= scale;
				z *= scale;
			}
		}

		original.call(obj, x, y, z);
	}
}
