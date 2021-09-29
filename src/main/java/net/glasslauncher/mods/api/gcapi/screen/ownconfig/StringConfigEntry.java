package net.glasslauncher.mods.api.gcapi.screen.ownconfig;

import net.glasslauncher.mods.api.gcapi.impl.ExtensibleTextbox;
import net.glasslauncher.mods.api.gcapi.screen.ConfigEntry;
import net.glasslauncher.mods.api.gcapi.screen.HasDrawable;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.render.TextRenderer;
import org.jetbrains.annotations.NotNull;

public class StringConfigEntry extends ConfigEntry<String> {
    private ExtensibleTextbox textbox;

    public StringConfigEntry(String id, String name, String description, String value) {
        super(id, name, description, value);
    }

    @Override
    public void init(ScreenBase parent, TextRenderer textRenderer) {
        textbox = new ExtensibleTextbox(textRenderer);
        textbox.setText(value);
    }

    @Override
    public String getDrawableValue() {
        return textbox == null? null : textbox.getText();
    }

    @Override
    public void setDrawableValue(String value) {
        textbox.setText(value);
    }

    @Override
    public @NotNull HasDrawable getDrawable() {
        return textbox;
    }
}
