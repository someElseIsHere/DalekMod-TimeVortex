package org.theplaceholder.dmtv.client.vortex;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import org.theplaceholder.dmtv.client.config.VortexConfig;
import org.theplaceholder.dmtv.client.tardis.RenderVortexTardis;

import static com.swdteam.main.DalekMod.MODID;

public class VortexScreen extends Screen {
    public static ResourceLocation OVERLAY = new ResourceLocation(MODID, "textures/gui/overlay/flight_overlay.png");

    private Vortex vortex;
    public VortexScreen() {
        super(new TranslationTextComponent("dmsm.vortex.title"));
    }

    @Override
    protected void init() {
        super.init();
        vortex = VortexConfig.getVortex();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float tick) {
        vortex.renderAsBackground();
        //RenderVortexTardis.render();

        RenderSystem.pushMatrix();
        RenderSystem.translatef(0.0F, 0.0F, 200.0F);
        RenderSystem.enableBlend();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.9F);
        Minecraft.getInstance().getTextureManager().bind(OVERLAY);
        float size = Math.max(this.width, this.height);

        ++size;
        RenderSystem.translatef((float)(this.width / 2) - size / 2.0F, (float)(this.height / 2) - size / 2.0F, 0.0F);
        RenderSystem.translatef(-0.5F, -0.5F, 0.0F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.vertex(0.0, size, 90.0).uv(0.0F, 1.0F).endVertex();
        bufferbuilder.vertex(size, size, 90.0).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex(size, 0.0, 90.0).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(0.0, 0.0, 90.0).uv(0.0F, 0.0F).endVertex();
        tessellator.end();
        RenderSystem.disableBlend();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.popMatrix();
    }

    @Override
    public void tick() {
        super.tick();
        RenderVortexTardis.tick();
    }
}
