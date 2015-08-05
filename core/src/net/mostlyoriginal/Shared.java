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
	public static int PARTICLE_COUNT = 50000;

	public static int VP_WIDTH = 800;
	public static int VP_HEIGHT = 800;

	public static TextureRegion instanceParticleTexture() {
		return new TextureRegion(new Texture("sphere.png"), 0, 0, 64, 64);
	}
}
