package net.glasslauncher.mods.api.gcapi.impl.config.entry;

import com.google.common.collect.Iterables;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.glasslauncher.mods.api.gcapi.api.CharacterUtils;
import net.glasslauncher.mods.api.gcapi.api.ConfigEntryWithButton;
import net.glasslauncher.mods.api.gcapi.api.HasDrawable;
import net.glasslauncher.mods.api.gcapi.impl.config.ConfigEntry;
import net.glasslauncher.mods.api.gcapi.impl.example.ExampleConfigEnum;
import net.glasslauncher.mods.api.gcapi.screen.widget.FancyButtonWidget;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.List;

/**
 * This class is a bit of a crapshoot cause java's generic type handling is pitifully bad.
 * @param <T> The enum you want to use. Must have toString implemented. Also must be passed into the constructor.
 */
public class EnumConfigEntry<T extends Enum<?>> extends ConfigEntry<Integer> implements ConfigEntryWithButton {
    private FancyButtonWidget button;
    public final Enum<?>[] parentEnumArray;

    public EnumConfigEntry(String id, String name, String description, Field parentField, Object parentObject, boolean multiplayerSynced, Integer value, Integer defaultValue, @SuppressWarnings("rawtypes") Class parentEnum) {
        super(id, name, description, parentField, parentObject, multiplayerSynced, value, defaultValue, null);
        //noinspection unchecked Fuck off
        parentEnumArray = (Enum<?>[]) Iterables.toArray(EnumSet.allOf(parentEnum), parentEnum);
    }

    @Override
    public void init(Screen parent, TextRenderer textRenderer) {
        super.init(parent, textRenderer);
        button = new FancyButtonWidget(10, 0, 0, 0, 0, getButtonText(), CharacterUtils.getIntFromColour(new Color(255, 202, 0, 255)));
        drawableList.add(button);
        button.active = !multiplayerLoaded;
    }

    @Override
    public Integer getDrawableValue() {
        return value;
    }

    @Override
    public void setDrawableValue(Integer value) {
        this.value = value;
        button.text = getButtonText();
    }

    @Override
    public boolean isValueValid() {
        return true;
    }

    @Override
    public @NotNull List<HasDrawable> getDrawables() {
        return drawableList;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void onClick() {
        value++;
        if(value > parentEnumArray.length - 1) {
            value = 0;
        }
        button.text = getButtonText();
    }

    @Override
    public void reset(Object defaultValue, boolean dontSave) throws IllegalAccessException {
        if(defaultValue instanceof ExampleConfigEnum exampleConfigEnum) {
            defaultValue = exampleConfigEnum.ordinal();
        }
        if (!dontSave) {
            parentField.set(parentObject, defaultValue);
        }
        value = (Integer) defaultValue;
        setDrawableValue((Integer) defaultValue);
    }

    @Override
    public void saveToField() throws IllegalAccessException {
        parentField.set(parentObject, parentEnumArray[value]);
    }

    public String getButtonText() {
        return parentEnumArray[value].toString() + " (" + (value + 1) + "/" + parentEnumArray.length + ")";
    }
}
