package net.glasslauncher.mods.gcapi.impl.screen;

import net.glasslauncher.mods.gcapi.api.ConfigEntry;
import net.glasslauncher.mods.gcapi.impl.object.ConfigEntryHandler;
import net.minecraft.client.gui.screen.Screen;

import java.util.*;
import java.util.function.*;

public class FloatListScreenBuilder extends BaseListScreenBuilder<Float> {

    public FloatListScreenBuilder(Screen parent, ConfigEntry configAnnotation, ConfigEntryHandler<Float[]> configEntry, Function<String, List<String>> validator) {
        super(parent, configAnnotation, configEntry, validator);
    }

    @Override
    Float convertStringToValue(String value) {
        return Float.parseFloat(value);
    }
}
