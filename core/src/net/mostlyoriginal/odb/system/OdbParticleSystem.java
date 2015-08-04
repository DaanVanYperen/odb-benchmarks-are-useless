package net.mostlyoriginal.odb.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.mostlyoriginal.Shared;
import net.mostlyoriginal.api.system.camera.CameraSystem;
import net.mostlyoriginal.odb.component.*;

/**
 * @author Daan van Yperen
 */
@Wire
public class OdbParticleSystem extends EntityProcessingSystem {

	private SpriteBatch batch;
	protected CameraSystem cameraSystem;
	private TextureRegion particleTexture;

	protected ComponentMapper<OdbPos> mOdbPos;
	protected ComponentMapper<OdbVelocity> mOdbVelocity;
	protected ComponentMapper<OdbScale> mScale;
	protected ComponentMapper<OdbTint> mTint;

	public OdbParticleSystem() {
		super(Aspect.all(OdbParticle.class, OdbPos.class, OdbVelocity.class));
	}

	@Override
	protected void initialize() {
		super.initialize();
		particleTexture = Shared.instanceParticleTexture();
		batch = new SpriteBatch(Shared.PARTICLE_BATCH_SIZE);
	}

	@Override
	protected void end() {
		batch.end();
	}

	@Override
	protected void begin() {
		batch.setProjectionMatrix(cameraSystem.camera.combined);
		batch.begin();
	}

	@Override
	protected void process(Entity e) {

		final OdbPos p = mOdbPos.get(e);
		//final OdbVelocity v = mOdbVelocity.get(e);

		float scale = mScale.get(e).scale;

		if ( mTint.has(e)) {
			final OdbTint tint = mTint.get(e);
			batch.setColor(tint.r,tint.g,tint.b,tint.a);
		}

		batch.draw(particleTexture,
				p.x - scale*0.5f,
				p.y - scale*0.5f,
				scale,
				scale);
	}

}
