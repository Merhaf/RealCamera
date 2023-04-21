package com.xtracr.realcamera.command;

import org.jetbrains.annotations.Nullable;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.xtracr.realcamera.RealCamera;
import com.xtracr.realcamera.camera.CameraController;
import com.xtracr.realcamera.config.ConfigFile;
import com.xtracr.realcamera.config.ModConfig;
import com.xtracr.realcamera.utils.VirtualRenderer;

import net.minecraft.command.CommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public abstract class ClientCommand<S extends CommandSource> {

    private static final ModConfig config = ConfigFile.modConfig;

    @Nullable
    public static Exception virtualRenderException = null;
    
    public void register(CommandDispatcher<S> dispatcher) {
        final LiteralArgumentBuilder<S> builder = literal(RealCamera.MODID);
        builder.then(literal("debug")
                .then(literal("config").executes(this::config)
                    .then(literal("detail").executes(this::detail)))
                .then(literal("camera").executes(this::camera)));

        dispatcher.register(builder);
    }
    
    private int config(CommandContext<S> context) throws CommandSyntaxException {
        final S source = context.getSource();
        this.sendFeedback(source, new LiteralText("Target Mod Model Part: [" + config.getModelModID() + ":" + config.getModModelPartName() + "]\n")
            .append("Mapped Model Part Name: [" + VirtualRenderer.getModelPartFieldName() + "]\n"));
        
        if (virtualRenderException != null) {
            this.sendFeedback(source, new LiteralText("Failed to get model part: " + virtualRenderException.getClass().getSimpleName() + "\n"));
        }
        return 0;
    }

    private int detail(CommandContext<S> context) throws CommandSyntaxException {
        final S source = context.getSource();
        String interim = "";
        this.sendFeedback(source, new LiteralText("Camera Mode: " + (config.isClassic() ? "[classic]" : "[binding]") + "\n")
            .append("Vanilla Model Part: [" + config.getVanillaModelPart().name() + "]\n")
            .append("Target Mod Model Part: [" + config.getModelModID() + ":" + config.getModModelPartName() + "]\n")
            .append("Mapped Model Part Name: [" + VirtualRenderer.getModelPartFieldName() + "]\n"));
        
        for (String modid : VirtualRenderer.getFunctionsKeys()) {
            interim += " [" + modid + "]";
        }
        this.sendFeedback(source, new LiteralText("Mods with function registered:" + interim));
        interim = "";
        for (String modid : VirtualRenderer.getMethodsKeys()) {
            interim += " [" + modid + "]";
        }
        this.sendFeedback(source, new LiteralText("Mods with method registered:" + interim + "\n"));
        interim = "";
        
        if (virtualRenderException != null) {
            this.sendFeedback(source, new LiteralText("Failed to get model part: " + virtualRenderException.getClass().getSimpleName() + "\n"));
        }
        return 0;
    }

    private int camera(CommandContext<S> context) throws CommandSyntaxException {
        final S source = context.getSource();
        this.sendFeedback(source, new LiteralText("Camera offset: " + CameraController.getCameraOffset().toString() + "\n")
            .append("Camera rotation: " + CameraController.getCameraRotation().toString()));
        return 0;
    }
    
    protected abstract void sendFeedback(S source, Text message);
    
    private LiteralArgumentBuilder<S> literal(final String name) {
        return LiteralArgumentBuilder.literal(name);
    }

}