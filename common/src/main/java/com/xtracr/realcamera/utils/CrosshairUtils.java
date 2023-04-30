package com.xtracr.realcamera.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

public class CrosshairUtils {

    public static EntityHitResult capturedEntityHitResult;

    private static Vec3d offset = new Vec3d(0, 0, 0);
    
    public static void translateMatrices(MatrixStack matrixStack) {
        matrixStack.translate(offset.getX(), -offset.getY(), 0.0D);
    }

    public static void update(MinecraftClient client, Camera camera, Matrix4f... projectionMatrices) {
        HitResult hitResult = client.crosshairTarget;
        if (client.targetedEntity != null) {
            hitResult = capturedEntityHitResult;
        }
        if (hitResult == null) {
            offset = new Vec3d(0, 0, 0);
            return;
        }
        offset = MathUtils.projectToVec2d(hitResult.getPos().subtract(camera.getPos()), projectionMatrices);
        Window window = client.getWindow();
        offset = offset.multiply(0.5*window.getScaledWidth(), 0.5*window.getScaledHeight(), 0.0D);
    }

}