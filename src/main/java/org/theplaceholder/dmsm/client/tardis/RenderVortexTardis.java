package org.theplaceholder.dmsm.client.tardis;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.client.render.tileentity.RenderBlockTardis;
import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.client.tardis.data.ExteriorModels;
import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.model.javajson.JSONModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.math.vector.Vector3f;
import org.lwjgl.opengl.GL11;

public class RenderVortexTardis {
    private static float rotation;
    private static TardisData tardisData;

    public static void render(){
        if (tardisData == null) return;

        float width = Minecraft.getInstance().getWindow().getWidth();
        float height = Minecraft.getInstance().getWindow().getHeight();
        float scale = Math.max(width, height) / 512;

        JSONModel tardisModel = ExteriorModels.getModel(tardisData.getTardisExterior().getData().getModel(tardisData.getSkinID()));
        IRenderTypeBuffer buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        IVertexBuilder builder = buffer.getBuffer(RenderType.entityTranslucent(tardisModel.getModelData().getTexture()));

        MatrixStack matrixStack = new MatrixStack();
        matrixStack.pushPose();

        matrixStack.translate(0, 0, 1);
        matrixStack.scale(scale, scale, scale);
        RenderSystem.translatef(0f, 0f, 0f);

        RenderBlockTardis.renderTardisModel(
                tardisModel,
                rotation,
                builder,
                tardisData,
                matrixStack,
                buffer,
                8
        );

        matrixStack.popPose();
    }

    public static void tick(){
        rotation += 0.5f;
        TardisData tardisData = ClientTardisCache.getTardisData(Minecraft.getInstance().player.blockPosition());
        if (tardisData != null)
            RenderVortexTardis.tardisData = tardisData;
    }
}
