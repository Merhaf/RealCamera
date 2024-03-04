package com.xtracr.realcamera.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CyclingTexturedButton extends PressableWidget {
    protected final Identifier texture;
    protected final int textureWidth, textureHeight, u, v, vOffset, size;
    private int value;

    public CyclingTexturedButton(int u, int v, int value, int size) {
        this(0, 0, 16, 16, u, v, value, size);
    }

    public CyclingTexturedButton(int x, int y, int width, int height, int u, int v, int value, int size) {
        this(x, y, width, height, u, v, height, value, size, TexturedButton.ICON_TEXTURE, 256, 256);
    }

    public CyclingTexturedButton(int x, int y, int width, int height, int u, int v, int vOffset, int value, int size, Identifier texture, int textureWidth, int textureHeight) {
        super(x, y, width, height, Text.empty());
        this.u = u;
        this.v = v;
        this.vOffset = vOffset;
        this.size = size;
        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        setValue(value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = (value % size + size) % size;
    }

    @Override
    public void onPress() {
        if (Screen.hasShiftDown()) setValue(value - 1);
        else setValue(value + 1);
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0xFF646464);
        context.drawTexture(texture, getX(), getY(), u, v + value * vOffset, width, height, textureWidth, textureHeight);
        if (isSelected()) context.drawBorder(getX(), getY(), getWidth(), getHeight(), 0xFFFFFFFF);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        appendDefaultNarrations(builder);
    }
}
