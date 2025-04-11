package net.glasslauncher.mods.gcapi3.impl.object;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.ModContainer;
import net.glasslauncher.mods.gcapi3.api.HasDrawable;
import net.glasslauncher.mods.gcapi3.impl.TerribleOrderPreservingMultimap;
import net.glasslauncher.mods.gcapi3.impl.screen.RootScreenBuilder;
import net.glasslauncher.mods.gcapi3.impl.screen.ScreenBuilder;
import net.glasslauncher.mods.gcapi3.impl.screen.widget.FancyButtonWidget;
import net.glasslauncher.mods.gcapi3.impl.screen.widget.ResetConfigWidget;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ConfigCategoryHandler extends ConfigHandlerBase {

    public final boolean isRoot;
    public TerribleOrderPreservingMultimap<Class<?>, ConfigHandlerBase> values;

    private List<HasDrawable> button;

    public ConfigCategoryHandler(String id, String name, String nameKey, String description, String descriptionKey, Field parentField, Object parentObject, boolean multiplayerSynced, TerribleOrderPreservingMultimap<Class<?>, ConfigHandlerBase> values, boolean isRoot) {
        super(id, name, nameKey, description, descriptionKey, parentField, parentObject, multiplayerSynced);
        this.values = values;
        this.isRoot = isRoot;
    }

    /**
     * The ScreenBuilder for this category. Can only have config entries.
     * @return ScreenBuilder
     */
    @Environment(EnvType.CLIENT)
    public @NotNull ScreenBuilder getConfigScreen(Screen parent, ModContainer mod) {
        return isRoot ? new RootScreenBuilder(parent, mod, this) : new ScreenBuilder(parent, mod, this);
    }

    @Override
    public @NotNull List<HasDrawable> getDrawables() {
        if (button == null) {
            button = new ArrayList<>();
            button.add(new FancyButtonWidget(0, 0, 0, "Open"));
            button.add(new ResetConfigWidget(this));
        }
        return button;
    }

    @Override
    public void applyTranslations(AtomicInteger count) {
        super.applyTranslations(count);
        values.forEach((aClass, configHandlerBase) -> configHandlerBase.applyTranslations(count));
    }

    public void resetMultiplayerSafeRecursive() throws IllegalAccessException {
        for (ConfigHandlerBase configHandlerBase : values.values()) {
            configHandlerBase.resetMultiplayerSafeRecursive();
        }
    }
}
