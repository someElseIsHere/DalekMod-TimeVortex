package org.theplaceholder.dmsm.mixins.client;

import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderSkybox;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.theplaceholder.dmsm.client.config.VortexConfig;

@Mixin(value = MainMenuScreen.class)
public abstract class MainMenuScreenMixin extends Screen {
    protected MainMenuScreenMixin(ITextComponent title) {super(title);}

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderSkybox;render(FF)V"))
    private void replaceBackground(RenderSkybox skybox, float x, float y) {
        VortexConfig.getVortex().renderBackground();
    }
}
