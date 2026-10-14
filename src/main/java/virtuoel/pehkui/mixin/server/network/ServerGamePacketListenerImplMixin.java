package virtuoel.pehkui.mixin.server.network;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.world.entity.MoverType;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
	@Shadow
	ServerPlayer player;

//	todo: see if still works!
//	@ModifyArg(method = "handleMoveVehicle", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/AABB;deflate(D)Lnet/minecraft/world/phys/AABB;"))
//	private double pehkui$onVehicleMove$contract(double value) {
//		final float scale = ScaleUtils.getMotionScale(player);
//		return scale < 1.0F ? value * scale : value;
//	}

	@ModifyArg(method = "handleMoveVehicle", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V"))
	private Vec3 pehkui$onVehicleMove$move(MoverType moverType, Vec3 movement) {
		final float scale = ScaleUtils.getMotionScale(player.getRootVehicle());
		return scale != 1.0F ? movement.scale(1.0F / scale) : movement;
	}

	@ModifyArg(method = "handlePlayerPositionChange", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V"))
	private Vec3 pehkui$onPlayerMove$move(MoverType moverType, Vec3 movement) {
		final float scale = ScaleUtils.getMotionScale(player);
		return scale != 1.0F ? movement.scale(1.0F / scale) : movement;
	}
}
