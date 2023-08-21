package org.theplaceholder.dmsm.client.vortex;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.theplaceholder.dmsm.client.config.VortexConfig;

import java.nio.FloatBuffer;

import static org.theplaceholder.dmsm.DMTV.MODID;

public class Vortex{
	public ResourceLocation TEXTURE_LOCATION;
	private float distortionSpeed = 0.5f;
	private float distortionSeparationFactor = 32f;
	private float distortionFactor = 2f;
	private float scale = 10f;
	private float rotationFactor = 1f;
	private float time = 0;
	private float rotationSpeed = 1.0f;
	private float textureRotationOffsetFactor = 0.0f;
	private float speed = 4f;

	public Vortex(float rotationFactor, String id){
		this(id);
		this.distortionFactor = rotationFactor;
	}

	public Vortex(String name) {
		TEXTURE_LOCATION = new ResourceLocation(MODID, "textures/vortex/" + name + ".png");
	}

	public void renderAsBackground() {
		int width = Minecraft.getInstance().screen.width;
		int height = Minecraft.getInstance().screen.height;
		float scale = Math.max(width, height) / 1.5f;

		GlStateManager._pushMatrix();
		GlStateManager._translatef(width / 2, height / 2, -100);
		GlStateManager._scalef(scale, scale, 0);

		renderVortex(new MatrixStack());

		GlStateManager._popMatrix();
	}

	public void renderVortex(MatrixStack matrixStack) {
		matrixStack.pushPose();
		RenderSystem.enableCull();
		RenderSystem.enableTexture();

		matrixStack.scale(scale, scale, 1);

		float f0 = (float) Math.toDegrees(this.rotationFactor * Math.sin(time * this.rotationSpeed));
		float f2 = f0 / 360.0f - (int) (f0 / 360.0);
		float f3 = this.textureRotationOffsetFactor * f2 - (int) (this.textureRotationOffsetFactor * f2);
		GL11.glRotated(f2 * 360.0, 0.0, 0.0, 1.0);

		Minecraft.getInstance().textureManager.bind(TEXTURE_LOCATION);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuilder();
		buffer.begin(7, DefaultVertexFormats.POSITION_TEX);

		for (int i = 0 ; i < 24; ++i) {
			this.renderSection(buffer, i, time * -this.speed, f3, (float) Math.sin(i * Math.PI / 36), (float) Math.sin((i + 1) * Math.PI / 36));
		}

		tessellator.end();
		RenderSystem.disableCull();
		matrixStack.popPose();
		time += Minecraft.getInstance().getDeltaFrameTime() / 100;
	}

	private static float oneSixth = 1/6f;
	private static float sqrt3Over2 = (float) Math.sqrt(3) / 2.0f;

	public void renderSection(BufferBuilder builder, int locationOffset, float textureDistanceOffset, float textureRotationOffset, float startScale, float endScale) {
		int verticalOffset = (locationOffset * oneSixth + textureDistanceOffset > 1.0) ? locationOffset - 6 : locationOffset;
		int horizontalOffset = (textureRotationOffset > 1.0) ? -6 : 0;
		float computedDistortionFactor = this.computeDistortionFactor(time, locationOffset);
		float computedDistortionFactorPlusOne = this.computeDistortionFactor(time, locationOffset + 1);

		builder.vertex(0, -startScale + computedDistortionFactor, -locationOffset)
				.uv(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).endVertex();
		builder.vertex(0, -endScale + computedDistortionFactorPlusOne, -1 - locationOffset)
				.uv(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).endVertex();
		builder.vertex(endScale * -sqrt3Over2, endScale * -0.5 + computedDistortionFactorPlusOne, -1 - locationOffset)
				.uv(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).endVertex();
		builder.vertex(startScale * -sqrt3Over2, startScale * -0.5 + computedDistortionFactor, -locationOffset)
				.uv(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).endVertex();
		horizontalOffset = ((oneSixth + textureRotationOffset > 1.0) ? -5 : 1);
		builder.vertex(startScale * -sqrt3Over2, startScale * -0.5 + computedDistortionFactor, -locationOffset)
				.uv(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).endVertex();
		builder.vertex(endScale * -sqrt3Over2, endScale * -0.5 + computedDistortionFactorPlusOne, -1 - locationOffset)
				.uv(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).endVertex();
		builder.vertex(endScale * -sqrt3Over2, endScale * 0.5 + computedDistortionFactorPlusOne, -1 - locationOffset)
				.uv(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).endVertex();
		builder.vertex(startScale * -sqrt3Over2, startScale * 0.5 + computedDistortionFactor, -locationOffset)
				.uv(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).endVertex();
		horizontalOffset = ((1.0f / 3.0f + textureRotationOffset > 1.0) ? -4 : 2);
		builder.vertex(startScale * -sqrt3Over2, startScale * 0.5 + computedDistortionFactor, -locationOffset)
				.uv(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).endVertex();
		builder.vertex(endScale * -sqrt3Over2, endScale * 0.5 + computedDistortionFactorPlusOne, -1 - locationOffset)
				.uv(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).endVertex();
		builder.vertex(endScale * -0.0f, endScale + computedDistortionFactorPlusOne, -1 - locationOffset)
				.uv(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).endVertex();
		builder.vertex(startScale * -0.0f, startScale + computedDistortionFactor, -locationOffset)
				.uv(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).endVertex();
		horizontalOffset = ((0.5f + textureRotationOffset > 1.0) ? -3 : 3);
		builder.vertex(startScale * -0.0f, startScale + computedDistortionFactor, -locationOffset)
				.uv(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).endVertex();
		builder.vertex(endScale * -0.0f, endScale + computedDistortionFactorPlusOne, -1 - locationOffset)
				.uv(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).endVertex();
		builder.vertex(endScale * sqrt3Over2, endScale * 0.5 + computedDistortionFactorPlusOne, -1 - locationOffset)
				.uv(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).endVertex();
		builder.vertex(startScale * sqrt3Over2, startScale * 0.5 + computedDistortionFactor, -locationOffset)
				.uv(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).endVertex();
		horizontalOffset = ((2.0f / 3.0f + textureRotationOffset > 1.0) ? -2 : 4);
		builder.vertex(startScale * sqrt3Over2, startScale * 0.5 + computedDistortionFactor, -locationOffset)
				.uv(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).endVertex();
		builder.vertex(endScale * sqrt3Over2, endScale * 0.5 + computedDistortionFactorPlusOne, -1 - locationOffset)
				.uv(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).endVertex();
		builder.vertex(endScale * sqrt3Over2, endScale * -0.5 + computedDistortionFactorPlusOne, -1 - locationOffset)
				.uv(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).endVertex();
		builder.vertex(startScale * sqrt3Over2, startScale * -0.5 + computedDistortionFactor, -locationOffset)
				.uv(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).endVertex();
		horizontalOffset = ((5.0f / 6.0f + textureRotationOffset > 1.0) ? -1 : 5);
		builder.vertex(startScale * sqrt3Over2, startScale * -0.5 + computedDistortionFactor, -locationOffset)
				.uv(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).endVertex();
		builder.vertex(endScale * sqrt3Over2, endScale * -0.5 + computedDistortionFactorPlusOne, -1 - locationOffset)
				.uv(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).endVertex();
		builder.vertex(endScale * -0.0f, endScale * -1.0f + computedDistortionFactorPlusOne, -1 - locationOffset)
				.uv(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).endVertex();
		builder.vertex(startScale * -0.0f, startScale * -1.0f + computedDistortionFactor, -locationOffset)
				.uv(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).endVertex();
	}

	private float computeDistortionFactor(float time, int t) {
		return (float) (Math.sin(time * this.distortionSpeed * 2.0 * Math.PI + (13 - t) * this.distortionSeparationFactor) * this.distortionFactor) / 8;
	}
}