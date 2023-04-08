package com.xtracr.realcamera.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.Camera;

@Mixin(Camera.class)
public interface CameraAccessor {
	
	@Accessor("detached")
	void setThirdPerson(boolean thirdPerson);

	@Invoker("setRotation")
	void invokeSetRotation(float yaw, float pitch);

	@Invoker("move")
	void invokeMoveBy(double x, double y, double z);
	
}
