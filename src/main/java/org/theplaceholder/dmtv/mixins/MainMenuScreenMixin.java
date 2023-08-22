package org.theplaceholder.dmtv.mixins;

import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.renderer.RenderSkybox;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.theplaceholder.dmtv.client.config.VortexConfig;
import org.theplaceholder.dmtv.client.vortex.VortexSkybox;

@Mixin(value = MainMenuScreen.class)
public abstract class MainMenuScreenMixin{
    @Mutable
    @Shadow @Final private RenderSkybox panorama;

    @Inject(method = "init", at = @At(value = "TAIL"))
    private void init(CallbackInfo ci) {
        panorama = new VortexSkybox(VortexConfig.getVortex());
    }
}
