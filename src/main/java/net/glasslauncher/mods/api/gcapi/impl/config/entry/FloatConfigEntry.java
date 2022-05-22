package net.glasslauncher.mods.api.gcapi.impl.config.entry;

import net.glasslauncher.mods.api.gcapi.api.CharacterUtils;
import net.glasslauncher.mods.api.gcapi.api.HasDrawable;
import net.glasslauncher.mods.api.gcapi.api.MaxLength;
import net.glasslauncher.mods.api.gcapi.impl.config.ConfigEntry;
import net.glasslauncher.mods.api.gcapi.screen.widget.ExtensibleTextbox;
import net.glasslauncher.mods.api.gcapi.screen.widget.Icon;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.render.TextRenderer;
import org.jetbrains.annotations.NotNull;
import uk.co.benjiweber.expressions.tuple.BiTuple;

import java.lang.reflect.*;
import java.util.*;

public class FloatConfigEntry extends ConfigEntry<Float> {
    private ExtensibleTextbox textbox;
    private List<HasDrawable> drawableList;

    public FloatConfigEntry(String id, String name, String description, Field parentField, Object parentObject, boolean isMultiplayerSynced, Float value, MaxLength maxLength) {
        super(id, name, description, parentField, parentObject, isMultiplayerSynced, value, maxLength);
    }

    @Override
    public void init(ScreenBase parent, TextRenderer textRenderer) {
        textbox = new ExtensibleTextbox(textRenderer);
        textbox.setValidator(str -> BiTuple.of(CharacterUtils.isFloat(str) && Float.parseFloat(str) <= maxLength.value(), multiplayerLoaded? Collections.singletonList("Server synced, you cannot change this value") : CharacterUtils.isFloat(str)? Float.parseFloat(str) > maxLength.value()? Collections.singletonList("Value is too high") : null : Collections.singletonList("Value is not a decimal number")));
        textbox.setMaxLength(maxLength.value());
        textbox.setText(value.toString());
        textbox.setEnabled(!multiplayerLoaded);
        drawableList = new ArrayList<>() {{
            add(textbox);
        }};
        if (multiplayerSynced) {
            drawableList.add(new Icon(10, 0, 0, 0, "/assets/gcapi/server_synced.png"));
        }
    }

    @Override
    public Float getDrawableValue() {
        return textbox == null? null : Float.parseFloat(textbox.getText());
    }

    @Override
    public void setDrawableValue(Float value) {
        textbox.setText(value.toString());
    }

    @Override
    public boolean isValueValid() {
        return textbox.isValueValid();
    }

    @Override
    public @NotNull List<HasDrawable> getDrawables() {
        return drawableList;
    }
}
