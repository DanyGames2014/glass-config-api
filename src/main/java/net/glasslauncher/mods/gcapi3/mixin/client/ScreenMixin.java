package net.glasslauncher.mods.gcapi3.mixin.client;

import net.glasslauncher.mods.gcapi3.api.CharacterUtils;
import net.glasslauncher.mods.gcapi3.api.HasToolTip;
import net.glasslauncher.mods.gcapi3.impl.screen.ScreenAccessor;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Mixin(Screen.class)
public class ScreenMixin extends DrawContext implements ScreenAccessor {

    @Shadow protected TextRenderer textRenderer;

    @SuppressWarnings("rawtypes")
    @Shadow protected List buttons;

    @Shadow private ButtonWidget selectedButton;

    @Inject(method = "render", at = @At(value = "TAIL"))
    public void drawTooltipStuff(int mouseX, int mouseY, float delta, CallbackInfo ci) {
        List<String> tooltip = glass_config_api$getMouseTooltip(mouseX, mouseY, new ArrayList<>());
        if (tooltip != null) {
            CharacterUtils.renderTooltip(textRenderer, tooltip, mouseX, mouseY, (Screen) (Object) this);
        }
    }

    @Override
    public List<String> glass_config_api$getMouseTooltip(int mouseX, int mouseY, List<?> extraObjectsToCheck) {
        AtomicReference<List<String>> tooltip = new AtomicReference<>(null);
        //noinspection unchecked
        Stream.of(buttons, extraObjectsToCheck).flatMap(Collection::stream).forEach((widget) -> {
            if (widget instanceof HasToolTip && glass_config_api$isMouseInBounds(((HasToolTip) widget).getXYWH(), mouseX, mouseY)) {
                tooltip.set(((HasToolTip) widget).getTooltip());
            }
        });
        return tooltip.get();
    }

    @Override
    public void glass_config_api$setSelectedButton(ButtonWidget value) {
        selectedButton = value;
    }

    @Override
    public boolean glass_config_api$isMouseInBounds(int[] xywh, int mouseX, int mouseY) {
        return mouseX >= xywh[0] && mouseX <= xywh[0] + xywh[2] && mouseY >= xywh[1] && mouseY <= xywh[1] + xywh[3];
    }

}
