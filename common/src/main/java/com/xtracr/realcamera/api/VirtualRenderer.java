package com.xtracr.realcamera.api;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

import com.xtracr.realcamera.command.ClientCommand;
import com.xtracr.realcamera.config.ConfigFile;

import net.minecraft.client.util.math.MatrixStack;

/**
 * @see CompatExample
 */
public class VirtualRenderer {

    private static final Map<String, BiPredicate<Float, MatrixStack>> functionProvider = new HashMap<>();

    public static void register(String modid, BiPredicate<Float, MatrixStack> function) {
        functionProvider.put(modid, function);
    }

    /**
     * 
     * @param modid    {@code mandatory}
     * @param function {@code mandatory} turn to vanilla rendering if return true.
     *                 {@link CompatExample#virtualRender See example here}
     * @param feedback {@code optional}
     *                 sent when command {@code \realcamera feedback} is executed
     * 
     */
    public static void register(String modid, BiPredicate<Float, MatrixStack> function, Supplier<String> feedback) {
        functionProvider.put(modid, function);
        ClientCommand.registerFeedback(() -> "[" + modid + "]: " + feedback.get());
    }

    /**
     * 
     * @return the value of {@link com.xtracr.realcamera.config.ModConfig.Compats#modModelPart modModelPart} 
     *         option in the config
     * 
     */
    public static String getModelPartName() {
        return ConfigFile.modConfig.getModModelPartName();
    }

    public static boolean virtualRender(float tickDelta, MatrixStack matrixStack) {
        return functionProvider.get(ConfigFile.modConfig.getModelModID()).test(tickDelta, matrixStack);
    }

    public static String[] getModidList() {
        return functionProvider.keySet().toArray(new String[functionProvider.size()]);
    }

}
