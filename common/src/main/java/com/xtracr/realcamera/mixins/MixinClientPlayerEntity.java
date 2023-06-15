package com.xtracr.realcamera.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mojang.authlib.GameProfile;
import com.xtracr.realcamera.RealCameraCore;
import com.xtracr.realcamera.config.ConfigFile;
import com.xtracr.realcamera.utils.RaycastUtils;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;

@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity extends AbstractClientPlayerEntity {

    public MixinClientPlayerEntity(ClientWorld world, GameProfile profile, PlayerPublicKey publicKey) {
        super(world, profile, publicKey);
    }

    @Redirect(method = "getLeashPos", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/client/option/Perspective;isFirstPerson()Z"))
    private boolean returnFalse(Perspective perspective) {
        if (ConfigFile.modConfig.isRendering() &&  RealCameraCore.isActive()) return false;
        return perspective.isFirstPerson();
    }

    @Override
    public HitResult raycast(double maxDistance, float tickDelta, boolean includeFluids) {
        if (!ConfigFile.modConfig.isCrosshairDynamic() && RealCameraCore.isActive()) {
            RaycastUtils.update(this, maxDistance*maxDistance, tickDelta);
            return this.world.raycast(RaycastUtils.getRaycastContext(RaycastContext.ShapeType.OUTLINE, 
                includeFluids ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE, this));
        }
        return super.raycast(maxDistance, tickDelta, includeFluids);
    }
}
