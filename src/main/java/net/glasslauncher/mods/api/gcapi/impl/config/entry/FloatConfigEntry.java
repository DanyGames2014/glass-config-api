package net.glasslauncher.mods.api.gcapi.impl.config.entry;

import net.glasslauncher.mods.api.gcapi.api.CharacterUtils;
import net.glasslauncher.mods.api.gcapi.api.HasDrawable;
import net.glasslauncher.mods.api.gcapi.impl.config.ConfigEntry;
import net.glasslauncher.mods.api.gcapi.screen.widget.ExtensibleTextbox;
import net.glasslauncher.mods.api.gcapi.screen.widget.Icon;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.render.TextRenderer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FloatConfigEntry extends ConfigEntry<Float> {
    private ExtensibleTextbox textbox;
    private final int maxLength;
    private List<HasDrawable> drawableList;

    public FloatConfigEntry(String id, String name, String description, Field parentField, Object parentObject, boolean isMultiplayerSynced, Float value, int maxLength) {
        super(id, name, description, parentField, parentObject, isMultiplayerSynced, value);
        this.maxLength = maxLength;
    }

    @Override
    public void init(ScreenBase parent, TextRenderer textRenderer) {
        textbox = new ExtensibleTextbox(textRenderer, CharacterUtils::isFloat);
        textbox.setMaxLength(maxLength);
        textbox.setText(value.toString());
        textbox.setEnabled(!multiplayerLoaded);
        drawableList = new ArrayList<HasDrawable>() {{add(textbox);}};
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
