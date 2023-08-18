package org.theplaceholder.dmsm.client.vortex;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.opengl.GL11;

import static org.theplaceholder.dmsm.DMTV.MODID;

public class Vortex{
	private ResourceLocation TEXTURE_LOCATION;
	private float distortionSpeed = 1.0f;
	private float distortionSeparationFactor = 0.6666666666666666f;
	private float distortionFactor = 1.0f;
	private float scale = 10.0f;
	private float rotationFactor = 1f;
	private float time = 0;
	private float rotationSpeed = 1.0f;
	private float textureRotationOffsetFactor = 0.0f;
	private float speed = 10f;

	public Vortex(String name) {
		TEXTURE_LOCATION = new ResourceLocation(MODID, "textures/vortex/" + name + ".png");
	}

	public void renderBackground() {
		int width = Minecraft.getInstance().screen.width;
		int height = Minecraft.getInstance().screen.height;
		float scale = Math.max(width, height) / 14;

		GlStateManager._pushMatrix();
		GlStateManager._translatef(width / 2, height / 2, -scale);
		GlStateManager._scalef(scale, scale, 0);

		render();

		GlStateManager._popMatrix();
	}

	public void render() {
		GlStateManager._pushMatrix();
		GlStateManager._enableCull();
		GlStateManager._enableTexture();
		GlStateManager._disableLighting();

		GL11.glScaled(scale, scale, scale);

		float f0 = (float) (Math.toDegrees(this.rotationFactor * Math.sin(time * this.rotationSpeed)));
		float f2 = f0 / 360.0f - (int) (f0 / 360.0);
		float f3 = this.textureRotationOffsetFactor * f2 - (int) (this.textureRotationOffsetFactor * f2);
		GL11.glRotated(f2 * 360.0, 0.0, 0.0, 1.0);

		Minecraft.getInstance().textureManager.bind(TEXTURE_LOCATION);
		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder buffer = tessellator.getBuilder();
		buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		for (int i = 0 ; i < 12; ++i) {
			this.renderSection(buffer, i, time * this.speed - (int) (time * this.speed), f3, (float) Math.sin(i * Math.PI / 12.0), (float) Math.sin((i + 1) * Math.PI / 12.0));
		}
		tessellator.end();

		GlStateManager._enableLighting();
		GlStateManager._popMatrix();
		time += Minecraft.getInstance().getDeltaFrameTime() * speed / 100;
	}

	public void renderSection(BufferBuilder builder, int locationOffset, float textureDistanceOffset, float textureRotationOffset, float startScale, float endScale){
		int verticalOffset = (locationOffset * 0.16666666666666666f + textureDistanceOffset > 1.0) ? locationOffset - 6 : locationOffset;
		int horizontalOffset = (textureRotationOffset > 1.0) ? -6 : 0;

		builder.vertex(0, -startScale + this.computeDistortionFactor(time, locationOffset), -locationOffset).uv(horizontalOffset * 0.16666666666666666f + textureRotationOffset, verticalOffset * 0.16666666666666666f + textureDistanceOffset).endVertex();
		builder.vertex(0, -endScale + this.computeDistortionFactor(time, locationOffset + 1), -1 - locationOffset).uv(horizontalOffset * 0.16666666666666666f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.16666666666666666f + textureDistanceOffset).endVertex();
		builder.vertex(endScale * -0.866025, endScale * -0.5 + this.computeDistortionFactor(time, locationOffset + 1), -1 - locationOffset).uv(horizontalOffset * 0.16666666666666666f + 0.16666666666666666f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.16666666666666666f + textureDistanceOffset).endVertex();
		builder.vertex(startScale * -0.866025, startScale * -0.5 + this.computeDistortionFactor(time, locationOffset), (-locationOffset)).uv(horizontalOffset * 0.16666666666666666f + 0.16666666666666666f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.0f + textureDistanceOffset).endVertex();
		horizontalOffset = ((0.16666666666666666f + textureRotationOffset > 1.0) ? -5 : 1);
		builder.vertex(startScale * -0.866025, startScale * -0.5 + this.computeDistortionFactor(time, locationOffset), (-locationOffset)).uv(horizontalOffset * 0.16666666666666666f + 0.0f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.0f + textureDistanceOffset).endVertex();
		builder.vertex(endScale * -0.866025, endScale * -0.5 + this.computeDistortionFactor(time, locationOffset + 1), (-1 - locationOffset)).uv(horizontalOffset * 0.16666666666666666f + 0.0f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.16666666666666666f + textureDistanceOffset).endVertex();
		builder.vertex(endScale * -0.866025, endScale * 0.5 + this.computeDistortionFactor(time, locationOffset + 1), (-1 - locationOffset)).uv(horizontalOffset * 0.16666666666666666f + 0.16666666666666666f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.16666666666666666f + textureDistanceOffset).endVertex();
		builder.vertex(startScale * -0.866025, startScale * 0.5 + this.computeDistortionFactor(time, locationOffset), (-locationOffset)).uv(horizontalOffset * 0.16666666666666666f + 0.16666666666666666f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.0f + textureDistanceOffset).endVertex();
		horizontalOffset = ((0.3333333333333333 + textureRotationOffset > 1.0) ? -4 : 2);
		builder.vertex(startScale * -0.866025, startScale * 0.5 + this.computeDistortionFactor(time, locationOffset), (-locationOffset)).uv(horizontalOffset * 0.16666666666666666f + 0.0f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.0f + textureDistanceOffset).endVertex();
		builder.vertex(endScale * -0.866025, endScale * 0.5 + this.computeDistortionFactor(time, locationOffset + 1), (-1 - locationOffset)).uv(horizontalOffset * 0.16666666666666666f + 0.0f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.16666666666666666f + textureDistanceOffset).endVertex();
		builder.vertex(endScale * -0.0, endScale * 1.0 + this.computeDistortionFactor(time, locationOffset + 1), (-1 - locationOffset)).uv(horizontalOffset * 0.16666666666666666f + 0.16666666666666666f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.16666666666666666f + textureDistanceOffset).endVertex();
		builder.vertex(startScale * -0.0, startScale * 1.0 + this.computeDistortionFactor(time, locationOffset), (-locationOffset)).uv(horizontalOffset * 0.16666666666666666f + 0.16666666666666666f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.0f + textureDistanceOffset).endVertex();
		horizontalOffset = ((0.5 + textureRotationOffset > 1.0) ? -3 : 3);
		builder.vertex(startScale * -0.0, startScale * 1.0 + this.computeDistortionFactor(time, locationOffset), (-locationOffset)).uv(horizontalOffset * 0.16666666666666666f + 0.0f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.0f + textureDistanceOffset).endVertex();
		builder.vertex(endScale * -0.0, endScale * 1.0 + this.computeDistortionFactor(time, locationOffset + 1), (-1 - locationOffset)).uv(horizontalOffset * 0.16666666666666666f + 0.0f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.16666666666666666f + textureDistanceOffset).endVertex();
		builder.vertex(endScale * 0.866025, endScale * 0.5 + this.computeDistortionFactor(time, locationOffset + 1), (-1 - locationOffset)).uv(horizontalOffset * 0.16666666666666666f + 0.16666666666666666f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.16666666666666666f + textureDistanceOffset).endVertex();
		builder.vertex(startScale * 0.866025, startScale * 0.5 + this.computeDistortionFactor(time, locationOffset), (-locationOffset)).uv(horizontalOffset * 0.16666666666666666f + 0.16666666666666666f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.0f + textureDistanceOffset).endVertex();
		horizontalOffset = ((0.6666666666666666 + textureRotationOffset > 1.0) ? -2 : 4);
		builder.vertex(startScale * 0.866025, startScale * 0.5 + this.computeDistortionFactor(time, locationOffset), (-locationOffset)).uv(horizontalOffset * 0.16666666666666666f + 0.0f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.0f + textureDistanceOffset).endVertex();
		builder.vertex(endScale * 0.866025, endScale * 0.5 + this.computeDistortionFactor(time, locationOffset + 1), (-1 - locationOffset)).uv(horizontalOffset * 0.16666666666666666f + 0.0f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.16666666666666666f + textureDistanceOffset).endVertex();
		builder.vertex(endScale * 0.866025, endScale * -0.5 + this.computeDistortionFactor(time, locationOffset + 1), (-1 - locationOffset)).uv(horizontalOffset * 0.16666666666666666f + 0.16666666666666666f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.16666666666666666f + textureDistanceOffset).endVertex();
		builder.vertex(startScale * 0.866025, startScale * -0.5 + this.computeDistortionFactor(time, locationOffset), (-locationOffset)).uv(horizontalOffset * 0.16666666666666666f + 0.16666666666666666f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.0f + textureDistanceOffset).endVertex();
		horizontalOffset = ((0.8333333333333333 + textureRotationOffset > 1.0) ? -1 : 5);
		builder.vertex(startScale * 0.866025, startScale * -0.5 + this.computeDistortionFactor(time, locationOffset), (-locationOffset)).uv(horizontalOffset * 0.16666666666666666f + 0.0f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.0f + textureDistanceOffset).endVertex();
		builder.vertex(endScale * 0.866025, endScale * -0.5 + this.computeDistortionFactor(time, locationOffset + 1), -1 - locationOffset).uv(horizontalOffset * 0.16666666666666666f + 0.0f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.16666666666666666f + textureDistanceOffset).endVertex();
		builder.vertex(endScale * -0.0, endScale * -1.0 + this.computeDistortionFactor(time, locationOffset + 1), -1 - locationOffset).uv(horizontalOffset * 0.16666666666666666f + 0.16666666666666666f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.16666666666666666f + textureDistanceOffset).endVertex();
		builder.vertex(startScale * -0.0, startScale * -1.0 + this.computeDistortionFactor(time, locationOffset), (-locationOffset)).uv(horizontalOffset * 0.16666666666666666f + 0.16666666666666666f + textureRotationOffset, verticalOffset * 0.16666666666666666f + 0.0f + textureDistanceOffset).endVertex();
	}

	private float computeDistortionFactor(float time, int t) {
		return (float) (Math.sin(time * this.distortionSpeed * 2.0 * Math.PI + (13 - t) * this.distortionSeparationFactor) * this.distortionFactor) * 0.5f;
	}
}