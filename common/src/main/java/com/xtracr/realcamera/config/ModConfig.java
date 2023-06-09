package com.xtracr.realcamera.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Pair;
import net.minecraft.util.math.MathHelper;

public class ModConfig {

    protected static final double MIN_DOUBLE = -64.0D;
    protected static final double MAX_DOUBLE = 64.0D;

    public General general = new General();
    public Binding binding = new Binding();
    public Classic classic = new Classic();
    public Compats compats = new Compats();
    public Disable disable = new Disable();

    public class General {

        public boolean enabled = false;
        public boolean classic = false;
        public boolean clipToSpace = true;
        public boolean dynamicCrosshair = false;
        public boolean renderModel = true;
        public double adjustStep = 0.25D;
        public double scale = 1.0D;

        private void clamp() {
            adjustStep = MathHelper.clamp(adjustStep, 0.0D, MAX_DOUBLE);
            scale = MathHelper.clamp(scale, 0.0D, MAX_DOUBLE);
        }
    }

    public class Binding {

        public VanillaModelPart vanillaModelPart = VanillaModelPart.head;
        public boolean adjustOffset = true;
        public double cameraX = 3.25D;
        public double cameraY = 2.0D;
        public double cameraZ = 0.0D;
        public double referX = 3.25D;
        public double referY = 2.0D;
        public double referZ = 0.0D;
        public boolean bindPitching = true;
        public boolean bindYawing = true;
        public boolean bindRolling = true;
        public float pitch = 0.0F;
        public float yaw = 0.0F;
        public float roll = 0.0F;

        private void clamp() {
            if (!(vanillaModelPart instanceof VanillaModelPart)) vanillaModelPart = VanillaModelPart.head;
            cameraX = MathHelper.clamp(cameraX, MIN_DOUBLE, MAX_DOUBLE);
            cameraY = MathHelper.clamp(cameraY, MIN_DOUBLE, MAX_DOUBLE);
            cameraZ = MathHelper.clamp(cameraZ, MIN_DOUBLE, MAX_DOUBLE);
            referX = MathHelper.clamp(referX, MIN_DOUBLE, MAX_DOUBLE);
            referY = MathHelper.clamp(referY, MIN_DOUBLE, MAX_DOUBLE);
            referZ = MathHelper.clamp(referZ, MIN_DOUBLE, MAX_DOUBLE);
            pitch = MathHelper.wrapDegrees(pitch);
            yaw = MathHelper.wrapDegrees(yaw);
            roll = MathHelper.wrapDegrees(roll);
        }
    }

    public class Classic {

        public AdjustMode adjustMode = AdjustMode.CAMERA;
        public double cameraX = -60.0D;
        public double cameraY = 2.0D;
        public double cameraZ = -16.0D;
        public double referX = 3.25D;
        public double referY = 2.0D;
        public double referZ = 0.0D;
        public double centerX = 0.0D;
        public double centerY = -3.4D;
        public double centerZ = 0.0D;
        public float pitch = 0.0F;
        public float yaw = 18.0F;
        public float roll = 0.0F;

        private void clamp() {
            if (!(adjustMode instanceof AdjustMode)) adjustMode = AdjustMode.CAMERA;
            cameraX = MathHelper.clamp(cameraX, MIN_DOUBLE, MAX_DOUBLE);
            cameraY = MathHelper.clamp(cameraY, MIN_DOUBLE, MAX_DOUBLE);
            cameraZ = MathHelper.clamp(cameraZ, MIN_DOUBLE, MAX_DOUBLE);
            referX = MathHelper.clamp(referX, MIN_DOUBLE, MAX_DOUBLE);
            referY = MathHelper.clamp(referY, MIN_DOUBLE, MAX_DOUBLE);
            referZ = MathHelper.clamp(referZ, MIN_DOUBLE, MAX_DOUBLE);
            centerX = MathHelper.clamp(centerX, MIN_DOUBLE, MAX_DOUBLE);
            centerY = MathHelper.clamp(centerY, MIN_DOUBLE, MAX_DOUBLE);
            centerZ = MathHelper.clamp(centerZ, MIN_DOUBLE, MAX_DOUBLE);
            pitch = MathHelper.wrapDegrees(pitch);
            yaw = MathHelper.wrapDegrees(yaw);
            roll = MathHelper.wrapDegrees(roll);
        }

        public enum AdjustMode {
            CAMERA, CENTER, ROTATION;

            private static final AdjustMode[] VALUES = values();

            public AdjustMode cycle() {
                return VALUES[(this.ordinal() + 1) % VALUES.length];
            }
        }
    }

    public class Compats {

        public boolean useModModel = false;
        public String modelModID = "minecraft";
        public String modModelPart = "head";
        public boolean doABarrelRoll = true;
        public boolean pehkui = true;
        public boolean physicsMod = true;
    }

    public class Disable {

        protected static final List<String> defaultParts = Arrays.asList("head", "hat", "helmet");
        protected static final List<Pair<Pair<String, String>, List<String>>> defaultConditions = Arrays.asList(
            new Pair<>(new Pair<>("minecraft:spyglass", "using"), Arrays.asList("disable_rendering")), 
            new Pair<>(new Pair<>("Example--minecraft:filled_map", "holding"), Arrays.asList(
                "allow_rendering_hand", "leftArm", "rightArm", "leftSleeve", "rightSleeve", "heldItem")));
        protected static final String[] behaviors = {"holding", "using"};

        public static final Set<String> optionalParts = new HashSet<>(Set.of("head", "hat", "helmet"));

        public boolean onlyInBinding = true;
        public boolean renderModelPart = false;
        public List<String> disabledModelParts = defaultParts;
        public List<Pair<Pair<String, String>, List<String>>> customConditions = defaultConditions;
        public boolean fallFlying = true;
        public boolean swiming = false;
        public boolean crawling = false;
        public boolean sneaking = false;
        public boolean sleeping = false;

        private void clamp() {
            if (this.disabledModelParts == null) this.disabledModelParts = defaultParts;
            if (this.customConditions == null) this.customConditions = defaultConditions;
        }
    }

    public void set(ModConfig modConfig) {
        this.general = modConfig.general;
        this.binding = modConfig.binding;
        this.classic = modConfig.classic;
        this.compats = modConfig.compats;
        this.disable = modConfig.disable;
    }

    public void clamp() {
        this.general.clamp();
        this.binding.clamp();
        this.classic.clamp();
        this.disable.clamp();
    }

    public boolean isEnabled() {
        return this.general.enabled;
    }
    public boolean isClassic() {
        return this.general.classic;
    }
    public boolean doClipToSpace() {
        return this.general.clipToSpace;
    }
    public boolean isCrosshairDynamic() {
        return this.general.dynamicCrosshair;
    }
    public boolean isRendering() {
        return this.general.renderModel;
    }
    public double getAdjustStep() {
        return this.general.adjustStep;
    }
    public double getScale() {
        return this.general.scale * 0.0625D;
    }

    public void setEnabled(boolean value) {
        this.general.enabled = value;
    }
    public void setClassic(boolean value) {
        this.general.classic = value;
    }

    // binding
    public VanillaModelPart getVanillaModelPart() {
        return this.binding.vanillaModelPart;
    }
    public boolean isAdjustingOffset() {
        return this.binding.adjustOffset;
    }
    public double getBindingX() {
        return this.binding.cameraX;
    }
    public double getBindingY() {
        return this.binding.cameraY;
    }
    public double getBindingZ() {
        return this.binding.cameraZ;
    }
    public double getBindingRX() {
        return this.binding.referX;
    }
    public double getBindingRY() {
        return this.binding.referY;
    }
    public double getBindingRZ() {
        return this.binding.referZ;
    }
    public boolean isPitchingBound() {
        return this.binding.bindPitching;
    }
    public boolean isYawingBound() {
        return this.binding.bindYawing;
    }
    public boolean isRollingBound() {
        return this.binding.bindRolling;
    }
    public float getBindingPitch() {
        return this.binding.pitch;
    }
    public float getBindingYaw() {
        return this.binding.yaw;
    }
    public float getBindingRoll() {
        return this.binding.roll;
    }

    public void setAdjustOffset(boolean value) {
        this.binding.adjustOffset = value;
    }
    public void adjustBindingX(boolean add) {
        int s = add ? 1 : -1;
        if (this.isAdjustingOffset()) this.binding.cameraX += s * getAdjustStep();
        else this.binding.roll += s * 4*(float)getAdjustStep();
        this.binding.clamp();
    }
    public void adjustBindingY(boolean add) {
        int s = add ? 1 : -1;
        if (this.isAdjustingOffset()) this.binding.cameraY += s * getAdjustStep();
        else this.binding.yaw += s * 4*(float)getAdjustStep();
        this.binding.clamp();
    }
    public void adjustBindingZ(boolean add) {
        int s = add ? 1 : -1;
        if (this.isAdjustingOffset()) this.binding.cameraZ += s * getAdjustStep();
        else this.binding.pitch += s * 4*(float)getAdjustStep();
        this.binding.clamp();
    }

    // classic
    public Classic.AdjustMode getClassicAdjustMode() {
        return this.classic.adjustMode;
    }
    public double getClassicX() {
        return this.classic.cameraX;
    }
    public double getClassicY() {
        return this.classic.cameraY;
    }
    public double getClassicZ() {
        return this.classic.cameraZ;
    }
    public double getClassicRX() {
        return this.classic.referX;
    }
    public double getClassicRY() {
        return this.classic.referY;
    }
    public double getClassicRZ() {
        return this.classic.referZ;
    }
    public double getCenterX() {
        return this.classic.centerX;
    }
    public double getCenterY() {
        return this.classic.centerY;
    }
    public double getCenterZ() {
        return this.classic.centerZ;
    }
    public float getClassicPitch() {
        return this.classic.pitch;
    }
    public float getClassicYaw() {
        return this.classic.yaw;
    }
    public float getClassicRoll() {
        return this.classic.roll;
    }

    public void cycleClassicAdjustMode() {
        this.classic.adjustMode = this.classic.adjustMode.cycle();
    }
    public void adjustClassicX(boolean add) {
        int s = add ? 1 : -1;
        switch (this.classic.adjustMode) {
            case CENTER:
                this.classic.centerX += s * getAdjustStep();
                break;
            case ROTATION:
                this.classic.roll += s * 4*(float)getAdjustStep();
                break;
            default:
                this.classic.cameraX += s * getAdjustStep();
        }
        this.classic.clamp();
    }
    public void adjustClassicY(boolean add) {
        int s = add ? 1 : -1;
        switch (this.classic.adjustMode) {
            case CENTER:
                this.classic.centerY += s * getAdjustStep();
                break;
            case ROTATION:
                this.classic.yaw += s * 4*(float)getAdjustStep();
                break;
            default:
                this.classic.cameraY += s * getAdjustStep();
        }
        this.classic.clamp();
    }
    public void adjustClassicZ(boolean add) {
        int s = add ? 1 : -1;
        switch (this.classic.adjustMode) {
            case CENTER:
                this.classic.centerZ += s * getAdjustStep();
                break;
            case ROTATION:
                this.classic.pitch += s * 4*(float)getAdjustStep();
                break;
            default:
                this.classic.cameraZ += s * getAdjustStep();
        }
        this.classic.clamp();
    }

    // compats
    public boolean isUsingModModel() {
        return this.compats.useModModel;
    }
    public String getModelModID() {
        return this.compats.modelModID;
    }
    public String getModModelPartName() {
        return this.compats.modModelPart;
    }
    public boolean compatDoABarrelRoll() {
        return this.compats.doABarrelRoll;
    }
    public boolean compatPehkui() {
        return this.compats.pehkui;
    }
    public boolean compatPhysicsMod() {
        return this.compats.physicsMod;
    }

    // disable
    private boolean shouldDisable(ClientPlayerEntity player, String action) {
        Set<Boolean> set = new HashSet<>();
        this.disable.customConditions.forEach(pair -> {
            if (!pair.getRight().contains(action)) return;
            String left = pair.getLeft().getLeft();
            String middle = pair.getLeft().getRight();
            if (middle.equals("using") && player.isUsingItem()) {
                set.add(left.equals(Registries.ITEM.getId(player.getActiveItem().getItem()).toString()));
            } else if (middle.equals("holding")) {
                set.add(player.isHolding(stack -> left.equals(Registries.ITEM.getId(stack.getItem()).toString())));
            }
        });
        return set.contains(true);
    }
    public boolean shouldDisableRender(String modelPartName) {
        if (this.disable.onlyInBinding && this.general.classic) return false;
        return (this.disable.renderModelPart && this.disable.disabledModelParts.contains(modelPartName))
            || this.shouldDisable(MinecraftClient.getInstance().player, modelPartName);
    }
    public boolean allowRenderingHandWhen(ClientPlayerEntity player) {
        if (this.disable.onlyInBinding && this.general.classic) return false;
        return this.shouldDisable(player, "allow_rendering_hand");
    }
    public boolean disableModWhen(ClientPlayerEntity player) {
        if (this.disable.onlyInBinding && this.general.classic) return false;
        return (player.isFallFlying() && this.disable.fallFlying)
            || (player.isSwimming() && this.disable.swiming)
            || (player.isCrawling() && this.disable.crawling)
            || (player.isSneaking() && this.disable.sneaking)
            || (player.isSleeping() && this.disable.sleeping)
            || this.shouldDisable(player, "disable_mod");
    }
    public boolean disableRenderingWhen(ClientPlayerEntity player) {
        if (this.disable.onlyInBinding && this.general.classic) return false;
        return this.shouldDisable(player, "disable_rendering");
    }

}
