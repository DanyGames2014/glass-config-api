package net.glasslauncher.mods.api.gcapi.impl.config.entry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.glasslauncher.mods.api.gcapi.api.CharacterUtils;
import net.glasslauncher.mods.api.gcapi.api.ConfigEntryWithButton;
import net.glasslauncher.mods.api.gcapi.api.HasDrawable;
import net.glasslauncher.mods.api.gcapi.impl.config.ConfigEntry;
import net.glasslauncher.mods.api.gcapi.screen.widget.FancyButton;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.render.TextRenderer;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.lang.reflect.Field;

public class BooleanConfigEntry extends ConfigEntry<Boolean> implements ConfigEntryWithButton {
    private FancyButton button;

    public BooleanConfigEntry(String id, String name, String description, Field parentField, Object parentObject, boolean isMultiplayerSynced, Boolean value) {
        super(id, name, description, parentField, parentObject, isMultiplayerSynced, value);
    }

    @Override
    public void init(ScreenBase parent, TextRenderer textRenderer) {
        button = new FancyButton(10, 0, 0, 0, 0, value.toString(), CharacterUtils.getIntFromColour(new Color(255, 202, 0, 255)));
        button.active = !multiplayerLoaded;
    }

    @Override
    public Boolean getDrawableValue() {
        return value;
    }

    @Override
    public void setDrawableValue(Boolean value) {
        this.value = value;
        button.text = value.toString();
    }

    @Override
    public boolean isValueValid() {
        return true;
    }

    @Override
    public @NotNull HasDrawable getDrawable() {
        return (HasDrawable) button;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void onClick() {
        value = !value;
        button.text = value.toString();
    }
}
