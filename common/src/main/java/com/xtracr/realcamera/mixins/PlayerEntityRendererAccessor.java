package com.xtracr.realcamera.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(PlayerEntityRenderer.class)
public interface PlayerEntityRendererAccessor {
    
    @Invoker("setModelPose")
    void invokeSetModelPose(AbstractClientPlayerEntity player);

    @Invoker("scale")
    void invokeScale(AbstractClientPlayerEntity abstractClientPlayerEntity, MatrixStack matrixStack, float tickDelta);

    @Invoker("setupTransforms")
    void invokeSetupTransforms(AbstractClientPlayerEntity abstractClientPlayerEntity, MatrixStack matrixStack, float f, float g, float tickDelta);
}