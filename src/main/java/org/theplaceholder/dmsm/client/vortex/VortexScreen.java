package org.theplaceholder.dmsm.client.vortex;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.swdteam.client.gui.GuiTardisViewport;
import com.swdteam.client.gui.viewport.TardisViewportRenderer;
import com.swdteam.client.overlay.OverlayFlightMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.opengl.GL11;
import org.theplaceholder.dmsm.client.config.VortexConfig;

public class VortexScreen extends Screen {
    private Vortex vortex;
    private OverlayFlightMode olverlay;
    public VortexScreen() {
        super(new TranslationTextComponent("dmsm.vortex.title"));
    }

    @Override
    protected void init() {
        super.init();
        vortex = VortexConfig.getVortex();
        olverlay = new OverlayFlightMode();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float tick) {
        GlStateManager._clearColor(0.0F, 0.0F, 0.0F, 0.0F);
        GlStateManager._clear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, true);

        vortex.render();
        olverlay.render(matrixStack);
    }
}
