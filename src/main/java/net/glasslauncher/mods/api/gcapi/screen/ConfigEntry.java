package net.glasslauncher.mods.api.gcapi.screen;

import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.render.TextRenderer;

public abstract class ConfigEntry<T> extends ConfigBase {
    public T value;
    protected ScreenBase parent;
    protected TextRenderer textRenderer;

    public ConfigEntry(String name, String description, T value) {
        super(name, description);
        this.value = value;
    }

    public abstract void init(ScreenBase parent, TextRenderer textRenderer);
}
