package org.theplaceholder.dmtv.mixins;

import com.swdteam.client.init.DMTileEntityRenderRegistry;
import com.swdteam.common.init.DMBlockEntities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = DMTileEntityRenderRegistry.class, remap = false)
public class DMTileEntityRenderRegistryMixin {
    @Inject(method = "init", at = @At("TAIL"))
    private static void init(CallbackInfo ci) {
        DMTileEntityRenderRegistry.registerModel(DMBlockEntities.TILE_TARDIS_DOOR_HITBOX.get(), TardisDoorHitboxTileEntityRenderer::new);
    }
}
