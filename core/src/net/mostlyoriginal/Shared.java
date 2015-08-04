package net.mostlyoriginal;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author Daan van Yperen
 */
public abstract class Shared {
	public static final float ZOOM = 1;
	public static final String BACKGROUND_COLOR_HEX = "02080C";
	public static final int PARTICLE_BATCH_SIZE = 2500;
	public static int PARTICLE_COUNT = 100000;

	public static TextureRegion instanceParticleTexture() {
		return new TextureRegion(new Texture("dancingman.png"), 0, 0, 24, 36);
	}
}
