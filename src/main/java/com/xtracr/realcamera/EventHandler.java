package com.xtracr.realcamera;

import com.xtracr.realcamera.camera.CameraController;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ViewportEvent.ComputeCameraAngles;
import net.minecraftforge.client.event.InputEvent.Key;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandler {
    
    @SubscribeEvent
    public static void onKeyInput(Key event) {
        KeyController.keyHandler();
    }

    @SubscribeEvent
    public static void onCameraSetup(ComputeCameraAngles event) {
        if (CameraController.isActive() && Minecraft.getInstance().player != null) {
            CameraController.setCameraOffset(event, Minecraft.getInstance(), event.getPartialTick());
        }
    }

}
