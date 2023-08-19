package org.theplaceholder.dmsm.client.vortex;

import net.minecraft.client.renderer.RenderSkybox;
import net.minecraft.client.renderer.RenderSkyboxCube;
import net.minecraft.util.ResourceLocation;

public class VortexSkybox extends RenderSkybox {
    private Vortex vortex;
    public VortexSkybox(Vortex vortex) {
        super(new RenderSkyboxCube(new ResourceLocation("textures/gui/title/background/panorama")));
        this.vortex = vortex;
    }

    public void render(float x, float y) {
        vortex.render();
    }
}
