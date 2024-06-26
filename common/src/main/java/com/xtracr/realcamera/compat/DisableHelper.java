package com.xtracr.realcamera.compat;

import com.xtracr.realcamera.config.ConfigFile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public final class DisableHelper {
    private static final Map<String, Predicate<LivingEntity>> predicates = new HashMap<>();

    public static void initialize() {
        registerOr("mainFeature", LivingEntity::isSleeping);
        registerOr("renderModel", entity -> entity instanceof PlayerEntity player && player.isUsingSpyglass());
        registerOr("renderModel", entity -> ConfigFile.config().getDisableRenderItems().contains(getItemId(entity.getMainHandStack())));
        registerOr("renderModel", entity -> ConfigFile.config().getDisableRenderItems().contains(getItemId(entity.getOffHandStack())));
    }

    public static void registerOr(String type, Predicate<LivingEntity> predicate) {
        predicates.merge(type, predicate, Predicate::or);
    }

    public static boolean isDisabled(String type, Entity cameraEntity) {
        Predicate<LivingEntity> predicate = predicates.get(type);
        if (ConfigFile.config().isClassic() || predicate == null) return false;
        return cameraEntity instanceof LivingEntity entity && predicate.test(entity);
    }

    private static String getItemId(ItemStack stack) {
        return Registries.ITEM.getId(stack.getItem()).toString();
    }
}
